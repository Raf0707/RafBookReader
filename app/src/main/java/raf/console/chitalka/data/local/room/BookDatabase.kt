/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.local.room

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import raf.console.chitalka.data.local.dto.BookEntity
import raf.console.chitalka.data.local.dto.ColorPresetEntity
import raf.console.chitalka.data.local.dto.HistoryEntity
import raf.console.chitalka.data.local.dto.CategoryEntity
import raf.console.chitalka.data.local.dto.BookCategoryCrossRef
import raf.console.chitalka.data.local.dto.BookmarkEntity
import raf.console.chitalka.data.local.dto.NoteEntity
import java.io.File

@Database(
    entities = [
        BookEntity::class,
        HistoryEntity::class,
        ColorPresetEntity::class,
        CategoryEntity::class,
        BookCategoryCrossRef::class,
        BookmarkEntity::class, // 🆕
        NoteEntity::class      // 🆕
    ],
    version = 12, // ⬆️ обновлено
    autoMigrations = [
        AutoMigration(1, 2),
        AutoMigration(2, 3),
        AutoMigration(3, 4, spec = DatabaseHelper.MIGRATION_3_4::class),
        AutoMigration(4, 5),
        AutoMigration(5, 6),
        AutoMigration(6, 7),
        AutoMigration(7, 8, spec = DatabaseHelper.MIGRATION_7_8::class),
        AutoMigration(8, 9, spec = DatabaseHelper.MIGRATION_8_9::class),
        // Миграция 9→10 — была ручная
        // Миграция 10→11 — тоже ручная, добавим ниже
    ],
    exportSchema = true
)

abstract class BookDatabase : RoomDatabase() {
    abstract val dao: BookDao
    abstract val categoryDao: CategoryDao
    abstract val bookCategoryDao: BookCategoryDao
    abstract val bookmarkDao: BookmarkDao
    abstract val noteDao: NoteDao
}

@Suppress("ClassName")
object DatabaseHelper {

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `LanguageHistoryEntity` (" +
                        "`languageCode` TEXT NOT NULL," +
                        " `order` INTEGER NOT NULL," +
                        " PRIMARY KEY(`languageCode`)" +
                        ")"
            )
        }
    }

    @DeleteColumn("BookEntity", "enableTranslator")
    @DeleteColumn("BookEntity", "translateFrom")
    @DeleteColumn("BookEntity", "translateTo")
    @DeleteColumn("BookEntity", "doubleClickTranslation")
    @DeleteColumn("BookEntity", "translateWhenOpen")
    @DeleteTable("LanguageHistoryEntity")
    class MIGRATION_3_4 : AutoMigrationSpec

    val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `ColorPresetEntity` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "`name` TEXT, " +
                        "`backgroundColor` INTEGER NOT NULL, " +
                        "`fontColor` INTEGER NOT NULL, " +
                        "`isSelected` INTEGER NOT NULL, " +
                        "`order` INTEGER NOT NULL" +
                        ")"
            )
        }
    }

    val MIGRATION_5_6 = object : Migration(5, 6) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `FavoriteDirectoryEntity` (" +
                        "`path` TEXT NOT NULL, " +
                        "PRIMARY KEY(`path`)" +
                        ")"
            )
        }
    }

    @DeleteColumn("BookEntity", "textPath")
    @DeleteColumn("BookEntity", "chapters")
    class MIGRATION_7_8 : AutoMigrationSpec {
        companion object {
            /**
             * Along with textPath deletion,
             * books directory with text does not
             * serve any purpose.
             */
            fun removeBooksDir(application: Application) {
                val booksDir = File(application.filesDir, "books")

                if (booksDir.exists()) {
                    booksDir.deleteRecursively()
                }
            }
        }
    }

    @DeleteTable("FavoriteDirectoryEntity")
    class MIGRATION_8_9 : AutoMigrationSpec

    /**
     * Миграция 9 → 10.
     * Создаём таблицу категорий и добавляем четыре стандартные категории.
     */
    val MIGRATION_9_10 = object : Migration(9, 10) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `CategoryEntity` (" +
                        "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "`name` TEXT NOT NULL, " +
                        "`kind` TEXT NOT NULL, " +
                        "`isVisible` INTEGER NOT NULL, " +
                        "`position` INTEGER NOT NULL, " +
                        "`isDefault` INTEGER NOT NULL" +
                        ")"
            )

            db.execSQL("INSERT OR IGNORE INTO `CategoryEntity` (id, name, kind, isVisible, position, isDefault) VALUES (0, 'All', 'SYSTEM_MAIN', 1, -1, 1)")
            db.execSQL("INSERT OR IGNORE INTO `CategoryEntity` (id, name, kind, isVisible, position, isDefault) VALUES (1, 'Reading', 'SYSTEM', 1, 0, 1)")
            db.execSQL("INSERT OR IGNORE INTO `CategoryEntity` (id, name, kind, isVisible, position, isDefault) VALUES (2, 'Already read', 'SYSTEM', 1, 1, 1)")
            db.execSQL("INSERT OR IGNORE INTO `CategoryEntity` (id, name, kind, isVisible, position, isDefault) VALUES (3, 'Planning', 'SYSTEM', 1, 2, 1)")
            db.execSQL("INSERT OR IGNORE INTO `CategoryEntity` (id, name, kind, isVisible, position, isDefault) VALUES (4, 'Dropped', 'SYSTEM', 1, 3, 1)")

            db.execSQL("CREATE TABLE IF NOT EXISTS `BookCategoryCrossRef` (" +
                    "`bookId` INTEGER NOT NULL, " +
                    "`categoryId` INTEGER NOT NULL, " +
                    "PRIMARY KEY(`bookId`, `categoryId`)" +
                    ")")

            db.execSQL("INSERT INTO `BookCategoryCrossRef` (bookId, categoryId) " +
                    "SELECT id, 0 FROM BookEntity")

            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `BookEntity_new` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`title` TEXT NOT NULL, " +
                        "`author` TEXT, " +
                        "`description` TEXT, " +
                        "`filePath` TEXT NOT NULL, " +
                        "`scrollIndex` INTEGER NOT NULL, " +
                        "`scrollOffset` INTEGER NOT NULL, " +
                        "`progress` REAL NOT NULL, " +
                        "`image` TEXT, " +
                        "`categoryId` INTEGER NOT NULL" +
                        ")"
            )

            val cursor = db.query("PRAGMA table_info(BookEntity)")
            var hasOldCategory = false
            while (cursor.moveToNext()) {
                val name = cursor.getString(1) // second column is name
                if (name == "category") {
                    hasOldCategory = true; break
                }
            }
            cursor.close()

            if (hasOldCategory) {
                db.execSQL(
                    "INSERT INTO `BookEntity_new` (id, title, author, description, filePath, scrollIndex, scrollOffset, progress, image, categoryId) " +
                            "SELECT id, title, author, description, filePath, scrollIndex, scrollOffset, progress, image, " +
                            "CASE category " +
                            "WHEN 'READING' THEN 1 " +
                            "WHEN 'ALREADY_READ' THEN 2 " +
                            "WHEN 'PLANNING' THEN 3 " +
                            "WHEN 'DROPPED' THEN 4 " +
                            "ELSE 1 END " +
                            "FROM BookEntity"
                )
            } else {
                db.execSQL(
                    "INSERT INTO `BookEntity_new` (id, title, author, description, filePath, scrollIndex, scrollOffset, progress, image, categoryId) " +
                            "SELECT id, title, author, description, filePath, scrollIndex, scrollOffset, progress, image, categoryId FROM BookEntity"
                )
            }

            db.execSQL("DROP TABLE BookEntity")
            db.execSQL("ALTER TABLE BookEntity_new RENAME TO BookEntity")

            db.execSQL(
                "INSERT INTO `BookCategoryCrossRef` (bookId, categoryId) " +
                        "SELECT id, categoryId FROM BookEntity WHERE categoryId != 0"
            )
        }
    }

    val MIGRATION_10_11 = object : Migration(10, 11) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
            CREATE TABLE IF NOT EXISTS `BookmarkEntity` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `bookId` INTEGER NOT NULL,
                `chapterIndex` INTEGER NOT NULL,
                `offset` INTEGER NOT NULL,
                `label` TEXT,
                `createdAt` INTEGER NOT NULL
            )
        """.trimIndent())

            db.execSQL("""
            CREATE TABLE IF NOT EXISTS `NoteEntity` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `bookId` INTEGER NOT NULL,
                `chapterIndex` INTEGER NOT NULL,
                `offsetStart` INTEGER NOT NULL,
                `offsetEnd` INTEGER NOT NULL,
                `content` TEXT NOT NULL,
                `createdAt` INTEGER NOT NULL,
                `bookmarkId` INTEGER,
                FOREIGN KEY(`bookmarkId`) REFERENCES `BookmarkEntity`(`id`) ON DELETE SET NULL
            )
        """.trimIndent())

            db.execSQL("""
            CREATE INDEX IF NOT EXISTS `index_NoteEntity_bookmarkId` ON `NoteEntity`(`bookmarkId`)
        """.trimIndent())
        }
    }

    val MIGRATION_11_12 = object : Migration(11, 12) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                ALTER TABLE `BookmarkEntity` ADD COLUMN `progress` REAL NOT NULL DEFAULT 0.0
            """.trimIndent())
        }
    }



    /**
     * Callback, который вызывается при создании базы данных (fresh install).
     * Заполняет таблицу `CategoryEntity` четырьмя стандартными категориями, если она пуста.
     */
    val PREPOPULATE_CATEGORIES = object : RoomDatabase.Callback() {
        private fun insertDefaults(db: SupportSQLiteDatabase) {
            db.execSQL("INSERT OR IGNORE INTO `CategoryEntity` (id, name, kind, isVisible, position, isDefault) VALUES (0, 'All', 'SYSTEM_MAIN', 1, -1, 1)")
            db.execSQL("INSERT OR IGNORE INTO `CategoryEntity` (id, name, kind, isVisible, position, isDefault) VALUES (1, 'Reading', 'SYSTEM', 1, 0, 1)")
            db.execSQL("INSERT OR IGNORE INTO `CategoryEntity` (id, name, kind, isVisible, position, isDefault) VALUES (2, 'Already read', 'SYSTEM', 1, 1, 1)")
            db.execSQL("INSERT OR IGNORE INTO `CategoryEntity` (id, name, kind, isVisible, position, isDefault) VALUES (3, 'Planning', 'SYSTEM', 1, 2, 1)")
            db.execSQL("INSERT OR IGNORE INTO `CategoryEntity` (id, name, kind, isVisible, position, isDefault) VALUES (4, 'Dropped', 'SYSTEM', 1, 3, 1)")
        }

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            insertDefaults(db)
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            val cursor = db.query("SELECT COUNT(*) FROM CategoryEntity")
            var count = 0
            if (cursor.moveToFirst()) count = cursor.getInt(0)
            cursor.close()
            if (count == 0) {
                insertDefaults(db)
            }
        }
    }

    val PREPOPULATE_BOOKS = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            insertDefaults(db)
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            val cursor = db.query("SELECT COUNT(*) FROM BookEntity WHERE id IN (-100, -101)")
            var count = 0
            if (cursor.moveToFirst()) count = cursor.getInt(0)
            cursor.close()
            if (count < 2) {
                insertDefaults(db)
            }
        }

        private fun insertDefaults(db: SupportSQLiteDatabase) {
            // Читательский дневник (закладки + заметки)
            db.execSQL("""
            INSERT OR IGNORE INTO BookEntity (
                id, title, author, description, filePath,
                scrollIndex, scrollOffset, progress, image, categoryId
            ) VALUES (
                -100, 'Читательский дневник', '', 'Автоматически создаётся для заметок к книгам', '',
                0, 0, 0.0, null, 0
            )
        """)

            // Мои заметки (независимая редактируемая книга)
            db.execSQL("""
            INSERT OR IGNORE INTO BookEntity (
                id, title, author, description, filePath,
                scrollIndex, scrollOffset, progress, image, categoryId
            ) VALUES (
                -101, 'Мои заметки', '', 'Записывайте мысли, идеи, черновики и личные заметки', '',
                0, 0, 0.0, null, 0
            )
        """)
        }
    }

}
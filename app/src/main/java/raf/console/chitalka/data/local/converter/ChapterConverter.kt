package raf.console.chitalka.data.local.converter

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import raf.console.chitalka.domain.model.Chapter

class ChapterConverter {
    @TypeConverter
    fun fromChapterList(chapters: List<Chapter>): String {
        return Gson().toJson(chapters)
    }

    @TypeConverter
    fun toChapterList(data: String): List<Chapter> {
        val listType = object : TypeToken<List<Chapter>>() {}.type
        return try {
            Gson().fromJson<List<Chapter>?>(data, listType).sortedBy { it.index }
        } catch (j: JsonSyntaxException) {
            j.printStackTrace()
            emptyList()
        }
    }
}
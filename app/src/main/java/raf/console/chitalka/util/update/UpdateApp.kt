package raf.console.chitalka.util.update
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.rustore.sdk.appupdate.manager.RuStoreAppUpdateManager
import ru.rustore.sdk.appupdate.manager.factory.RuStoreAppUpdateManagerFactory
import ru.rustore.sdk.appupdate.model.AppUpdateInfo
import ru.rustore.sdk.appupdate.model.AppUpdateOptions
import ru.rustore.sdk.appupdate.model.AppUpdateType
import ru.rustore.sdk.appupdate.model.UpdateAvailability

private const val TAG = "RuStoreUpdate"
private const val RETRY_DELAY = 5000L // 5 секунд перед повтором запроса

fun checkForUpdates(context: Context, activity: Activity) {
    val updateManager = RuStoreAppUpdateManagerFactory.create(context)

    requestUpdateInfo(context, activity, updateManager)
}

private fun requestUpdateInfo(context: Context, activity: Activity, updateManager: RuStoreAppUpdateManager) {
    updateManager.getAppUpdateInfo()
        .addOnSuccessListener { appUpdateInfo ->
            when (appUpdateInfo.updateAvailability) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    showUpdateDialog(context, activity, updateManager, appUpdateInfo)
                }
                else -> {
                    Log.d(TAG, "Обновление не требуется или в процессе обновления")
                }
            }
        }
        .addOnFailureListener { throwable ->
            Log.e(TAG, "Ошибка проверки обновлений, пробуем снова...", throwable)
            retryCheckForUpdates(context, activity, updateManager)
        }
}

private fun retryCheckForUpdates(context: Context, activity: Activity, updateManager: RuStoreAppUpdateManager) {
    Handler(Looper.getMainLooper()).postDelayed({
        requestUpdateInfo(context, activity, updateManager)
    }, RETRY_DELAY)
}

private fun showUpdateDialog(
    context: Context,
    activity: Activity,
    updateManager: RuStoreAppUpdateManager,
    appUpdateInfo: AppUpdateInfo
) {
    MaterialAlertDialogBuilder(context)
        .setTitle("Доступно обновление")
        .setMessage("Обнаружена новая версия приложения. Обновите его сейчас!")
        .setPositiveButton("Обновить") { _, _ ->
            startUpdate(updateManager, appUpdateInfo, activity)
        }
        .setNegativeButton("Закрыть") { _, _ ->
            activity.finish() // ❌ Если отказался, закрываем приложение
        }
        .setCancelable(false)
        .show()
}

private fun startUpdate(updateManager: RuStoreAppUpdateManager, appUpdateInfo: AppUpdateInfo, activity: Activity) {
    updateManager.startUpdateFlow(
        appUpdateInfo,
        AppUpdateOptions.Builder().appUpdateType(AppUpdateType.FLEXIBLE).build()
    ).addOnSuccessListener { resultCode ->
        when (resultCode) {
            Activity.RESULT_OK -> {
                Log.d(TAG, "✅ Обновление успешно завершено")
                completeUpdate(updateManager)
            }
            Activity.RESULT_CANCELED -> {
                Log.w(TAG, "❌ Пользователь отменил обновление. Завершаем приложение.")
                activity.finish()
            }
            2 -> { // ActivityResult.ACTIVITY_NOT_FOUND
                Log.e(TAG, "⚠ RuStore не найден или версия устарела")
            }
        }
    }.addOnFailureListener { throwable ->
        Log.e(TAG, "Ошибка старта обновления", throwable)
        retryCheckForUpdates(activity, activity, updateManager) // Пробуем снова
    }
}

private fun completeUpdate(updateManager: RuStoreAppUpdateManager) {
    updateManager.completeUpdate(AppUpdateOptions.Builder().appUpdateType(AppUpdateType.FLEXIBLE).build())
        .addOnFailureListener { throwable ->
            Log.e(TAG, "Ошибка завершения обновления", throwable)
        }
}

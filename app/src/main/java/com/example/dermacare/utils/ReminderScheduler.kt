package com.example.dermacare.utils
import android.content.Context
import androidx.work.*
import com.example.dermacare.workers.SkinCareReminderWorker
import java.util.concurrent.TimeUnit
import java.util.Calendar

object ReminderScheduler {

    fun scheduleDailyReminders(context: Context) {
        scheduleMorningReminder(context)
        scheduleEveningReminder(context)
    }

    private fun scheduleMorningReminder(context: Context) {
        val delay = calculateDelay(8, 0)   // 8:00 AM

        val data = Data.Builder()
            .putString("reminder_type", "morning")
            .build()

        val morningWork = PeriodicWorkRequestBuilder<SkinCareReminderWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag("morning_reminder")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "morning_skincare",
            ExistingPeriodicWorkPolicy.KEEP,
            morningWork
        )
    }

    private fun scheduleEveningReminder(context: Context) {
        val delay = calculateDelay(20, 0)  // 8:00 PM

        val data = Data.Builder()
            .putString("reminder_type", "evening")
            .build()

        val eveningWork = PeriodicWorkRequestBuilder< SkinCareReminderWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag("evening_reminder")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "evening_skincare",
            ExistingPeriodicWorkPolicy.KEEP,
            eveningWork
        )
    }

    // ✅ Aaj ka time check karke delay calculate karta hai
    private fun calculateDelay(targetHour: Int, targetMinute: Int): Long {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, targetHour)
            set(Calendar.MINUTE, targetMinute)
            set(Calendar.SECOND, 0)
        }
        // Agar time nikal gaya toh kal ka schedule karo
        if (target.before(now)) {
            target.add(Calendar.DAY_OF_MONTH, 1)
        }
        return target.timeInMillis - now.timeInMillis
    }
}

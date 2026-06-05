package com.example.dermacare.domain.usecase

import android.content.Context
import com.example.dermacare.utils.ReminderScheduler

class ScheduleReminderUseCase(private val context: Context) {
    operator fun invoke(){
        ReminderScheduler.scheduleDailyReminders(context)
    }
}
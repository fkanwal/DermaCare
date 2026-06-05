package com.example.dermacare.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dermacare.domain.usecase.ScheduleReminderUseCase

class NotificationViewModel(application: Application): AndroidViewModel(application) {
    private val scheduleReminderUseCase = ScheduleReminderUseCase(application)
    private val _reminderScheduled = MutableLiveData<Boolean>()
    val reminderScheduled: LiveData<Boolean> = _reminderScheduled

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun scheduleReminders() {
        try {
            scheduleReminderUseCase()
            _reminderScheduled.value = true
            _message.value = "Daily reminders scheduled! ✅"
        } catch (e: Exception) {
            _reminderScheduled.value = false
            _message.value = "Failed to schedule reminders"
        }
    }
    fun cancelReminders() {
        try {
            androidx.work.WorkManager.getInstance(getApplication())
                .cancelAllWork()
            _reminderScheduled.value = false
            _message.value = "Reminders cancelled"
        } catch (e: Exception) {
            _message.value = "Failed to cancel reminders"
        }
    }
}

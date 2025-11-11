package com.example.myapplication.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.myapplication.data.Product
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar

class AlarmScheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleReminder(product: Product) {
        if (product.reminderType == com.example.myapplication.data.ReminderType.NONE) {
            cancelReminder(product.id)
            return
        }

        val reminderDateTime = calculateReminderDateTime(product)
        val calendar = Calendar.getInstance().apply {
            timeInMillis = reminderDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
        }

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = "com.example.myapplication.REMINDER_ALARM"
            putExtra("product_id", product.id)
            putExtra("product_name", product.name)
            putExtra("expiry_date", product.expiryDate.toString())
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            product.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun cancelReminder(productId: Int) {
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            productId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    private fun calculateReminderDateTime(product: Product): LocalDateTime {
        val reminderDate = product.expiryDate.minusDays(product.reminderDaysInAdvance.toLong())
        val reminderTime = LocalTime.parse(product.reminderTime)
        return LocalDateTime.of(reminderDate, reminderTime)
    }
}

package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val productionDate: LocalDate,
    val shelfLifeDays: Int,
    val expiryDate: LocalDate,
    val reminderDaysInAdvance: Int = 3,
    val reminderTime: String = "09:00",
    val reminderType: ReminderType = ReminderType.NOTIFICATION,
    val calendarEventId: Long? = null,
    val isExpired: Boolean = false,
    val daysRemaining: Int = 0,
    val notes: String = ""
)

enum class ReminderType {
    NOTIFICATION,
    ALARM,
    BOTH,
    NONE
}

fun Product.getDaysRemaining(): Int {
    val today = LocalDate.now()
    return when {
        expiryDate.isBefore(today) -> -1
        else -> (expiryDate.toEpochDay() - today.toEpochDay()).toInt()
    }
}

fun Product.isExpiredNow(): Boolean {
    return LocalDate.now().isAfter(expiryDate) || LocalDate.now().isEqual(expiryDate)
}

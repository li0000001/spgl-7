package com.example.myapplication.data

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun fromReminderType(value: ReminderType): String {
        return value.name
    }

    @TypeConverter
    fun toReminderType(value: String): ReminderType {
        return ReminderType.valueOf(value)
    }
}

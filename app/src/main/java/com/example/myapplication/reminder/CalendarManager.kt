package com.example.myapplication.reminder

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.provider.CalendarContract
import com.example.myapplication.data.Product
import java.time.LocalDateTime
import java.time.ZoneId

class CalendarManager(private val context: Context) {
    private val contentResolver = context.contentResolver

    fun createCalendarEvent(product: Product): Long? {
        return try {
            val calendarId = getCalendarId()
            if (calendarId == null) {
                return null
            }

            val startTime = product.expiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val endTime = startTime + (60 * 60 * 1000) // 1 hour duration

            val event = ContentValues().apply {
                put(CalendarContract.Events.CALENDAR_ID, calendarId)
                put(CalendarContract.Events.TITLE, "保质期提醒: ${product.name}")
                put(CalendarContract.Events.DESCRIPTION, "商品 '${product.name}' 将于 ${product.expiryDate} 过期")
                put(CalendarContract.Events.DTSTART, startTime)
                put(CalendarContract.Events.DTEND, endTime)
                put(CalendarContract.Events.EVENT_TIMEZONE, "UTC")
                put(CalendarContract.Events.HAS_ALARM, 1)
            }

            val uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, event)
            uri?.lastPathSegment?.toLongOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun updateCalendarEvent(product: Product) {
        if (product.calendarEventId != null) {
            deleteCalendarEvent(product.calendarEventId)
        }
    }

    fun deleteCalendarEvent(eventId: Long) {
        try {
            val uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId)
            contentResolver.delete(uri, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getCalendarId(): Long? {
        return try {
            val projection = arrayOf(CalendarContract.Calendars._ID)
            val cursor = contentResolver.query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                null,
                null,
                null
            )
            cursor?.use {
                if (it.moveToFirst()) {
                    it.getLong(0)
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Product
import com.example.myapplication.data.ProductDao
import com.example.myapplication.reminder.AlarmScheduler
import com.example.myapplication.reminder.CalendarManager
import kotlinx.coroutines.launch

class ProductViewModel(private val productDao: ProductDao, private val context: Context) :
    ViewModel() {
    private val alarmScheduler = AlarmScheduler(context)
    private val calendarManager = CalendarManager(context)

    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()
    val expiredProducts: LiveData<List<Product>> = productDao.getExpiredProducts()
    val validProducts: LiveData<List<Product>> = productDao.getValidProducts()

    fun addProduct(product: Product) {
        viewModelScope.launch {
            val productId = productDao.insert(product)
            val newProduct = product.copy(id = productId.toInt())

            alarmScheduler.scheduleReminder(newProduct)
            val eventId = calendarManager.createCalendarEvent(newProduct)

            if (eventId != null) {
                productDao.update(newProduct.copy(calendarEventId = eventId))
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            productDao.update(product)
            alarmScheduler.scheduleReminder(product)
            calendarManager.updateCalendarEvent(product)

            if (product.calendarEventId != null) {
                calendarManager.deleteCalendarEvent(product.calendarEventId)
            }
            val eventId = calendarManager.createCalendarEvent(product)
            if (eventId != null) {
                productDao.update(product.copy(calendarEventId = eventId))
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productDao.delete(product)
            alarmScheduler.cancelReminder(product.id)
            if (product.calendarEventId != null) {
                calendarManager.deleteCalendarEvent(product.calendarEventId)
            }
        }
    }

    fun deleteProductById(productId: Int) {
        viewModelScope.launch {
            val product = productDao.getProductById(productId)
            if (product != null) {
                productDao.delete(product)
                alarmScheduler.cancelReminder(productId)
                if (product.calendarEventId != null) {
                    calendarManager.deleteCalendarEvent(product.calendarEventId)
                }
            }
        }
    }

    companion object {
        fun create(context: Context): ProductViewModel {
            val database = AppDatabase.getInstance(context)
            val productDao = database.productDao()
            return ProductViewModel(productDao, context)
        }
    }
}

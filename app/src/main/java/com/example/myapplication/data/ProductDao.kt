package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product: Product): Long

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM products ORDER BY expiryDate ASC")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): Product?

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM products WHERE expiryDate < date('now') ORDER BY expiryDate ASC")
    fun getExpiredProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE expiryDate >= date('now') ORDER BY expiryDate ASC")
    fun getValidProducts(): LiveData<List<Product>>
}

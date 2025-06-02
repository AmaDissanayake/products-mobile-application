package com.example.productsmobileapplication.data

import com.example.productsmobileapplication.model.Product

class ProductRepository {
    private val api = RetrofitInstance.api

    suspend fun getAllProducts(): List<Product> {
        return try {
            api.getProducts().products
        } catch (e: Exception) {
            emptyList() // Return empty list on error
        }
    }
}
package com.example.productsmobileapplication.data

import com.example.productsmobileapplication.model.Product

class ProductDetailRepository {
    private val api = RetrofitInstance.api

    suspend fun getProductById(id: Int): Product? {
        return try {
            api.getProductById(id)
        } catch (e: Exception) {
            null
        }
    }
}
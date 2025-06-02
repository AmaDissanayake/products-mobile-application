package com.example.productsmobileapplication.data

import com.example.productsmobileapplication.model.Product

class ProductRepository {
    private val api = RetrofitInstance.api

    suspend fun getProducts(skip: Int, limit: Int): List<Product> {
        return try {
            api.getProducts(skip, limit).products
        } catch (e: Exception) {
            emptyList()
        }
    }
}
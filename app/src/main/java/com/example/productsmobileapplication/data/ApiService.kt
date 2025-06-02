package com.example.productsmobileapplication.data

import com.example.productsmobileapplication.model.Product
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): ProductsResponse
}
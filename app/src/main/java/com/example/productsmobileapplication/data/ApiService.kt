package com.example.productsmobileapplication.data

import com.example.productsmobileapplication.model.Product
import com.example.productsmobileapplication.model.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): ProductsResponse

    // Add endpoint for single product
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}
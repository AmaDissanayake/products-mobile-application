package com.example.productsmobileapplication.data

import com.example.productsmobileapplication.model.Product

data class ProductsResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
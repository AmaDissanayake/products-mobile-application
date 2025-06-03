package com.example.productsmobileapplication.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("brand") val brand: String?,
    @SerializedName("category") val category: String,
    @SerializedName("tags") val tags: List<String>? = emptyList(),
    @SerializedName("availabilityStatus") val availabilityStatus: String? = "In Stock" // Added field
)
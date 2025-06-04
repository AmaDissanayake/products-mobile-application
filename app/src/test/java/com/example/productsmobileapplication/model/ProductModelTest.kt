package com.example.productsmobileapplication.model

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductModelTest {

    @Test
    fun `Product should deserialize from JSON correctly`() {
        // Given
        val json = """
            {
                "id": 1,
                "title": "Test Product",
                "description": "Test description",
                "price": 9.99,
                "thumbnail": "thumbnail.jpg",
                "images": ["img1.jpg", "img2.jpg"],
                "brand": "Test Brand",
                "category": "Test Category",
                "tags": ["tag1", "tag2"],
                "availabilityStatus": "In Stock"
            }
        """.trimIndent()

        // When
        val product = Gson().fromJson(json, Product::class.java)

        // Then
        assertEquals(1, product.id)
        assertEquals("Test Product", product.title)
        assertEquals(9.99, product.price, 0.001)
        assertEquals(2, product.images.size)
        assertEquals("Test Brand", product.brand)
        assertEquals(2, product.tags?.size)
        assertEquals("In Stock", product.availabilityStatus)
    }

    @Test
    fun `ProductsResponse should deserialize from JSON correctly`() {
        // Given
        val json = """
            {
                "products": [
                    {"id": 1, "title": "Product 1"},
                    {"id": 2, "title": "Product 2"}
                ],
                "total": 100,
                "skip": 0,
                "limit": 2
            }
        """.trimIndent()

        // When
        val response = Gson().fromJson(json, ProductsResponse::class.java)

        // Then
        assertEquals(2, response.products.size)
        assertEquals(100, response.total)
        assertEquals(0, response.skip)
        assertEquals(2, response.limit)
    }
}
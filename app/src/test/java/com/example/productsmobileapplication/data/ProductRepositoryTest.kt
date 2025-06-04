package com.example.productsmobileapplication.data

import com.example.productsmobileapplication.model.Product
import com.example.productsmobileapplication.model.ProductsResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductRepositoryTest {

    // Helper function to create test product
    private fun createTestProduct(id: Int): Product {
        return Product(
            id = id,
            title = "Product $id",
            description = "Description $id",
            price = 10.0 * id,
            thumbnail = "",
            images = emptyList(),
            brand = null,
            category = "Category $id",
            tags = null,
            availabilityStatus = "In Stock"
        )
    }

    @Test
    fun `getProducts should return products on success`() {
        runTest {
            // Given
            val mockApiService = mockk<ApiService>()
            val mockProducts = listOf(createTestProduct(1), createTestProduct(2))
            coEvery { mockApiService.getProducts(any(), any()) } returns ProductsResponse(
                products = mockProducts,
                total = 2,
                skip = 0,
                limit = 10
            )

            // Create repository and set API through reflection
            val repository = ProductRepository()
            repository::class.java.getDeclaredField("api").apply {
                isAccessible = true
                set(repository, mockApiService)
            }

            // When
            val result = repository.getProducts(0, 10)

            // Then
            assertEquals(2, result.size)
        }
    }

    @Test
    fun `getProducts should return empty list on exception`() {
        runTest {
            // Given
            val mockApiService = mockk<ApiService>()
            coEvery { mockApiService.getProducts(any(), any()) } throws Exception("Error")

            // Create repository and set API through reflection
            val repository = ProductRepository()
            repository::class.java.getDeclaredField("api").apply {
                isAccessible = true
                set(repository, mockApiService)
            }

            // When
            val result = repository.getProducts(0, 10)

            // Then
            assertTrue(result.isEmpty())
        }
    }
}
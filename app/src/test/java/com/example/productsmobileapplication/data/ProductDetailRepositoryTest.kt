package com.example.productsmobileapplication.data

import com.example.productsmobileapplication.model.Product
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ProductDetailRepositoryTest {

    // Helper function to create test product
    private fun createTestProduct(id: Int, title: String): Product {
        return Product(
            id = id,
            title = title,
            description = "Test description",
            price = 9.99,
            thumbnail = "",
            images = emptyList(),
            brand = null,
            category = "Test Category",
            tags = null,
            availabilityStatus = "In Stock"
        )
    }

    @Test
    fun `getProductById should return product on success`() {
        runTest {
            // Given
            val mockApiService = mockk<ApiService>()
            val mockProduct = createTestProduct(1, "Test")
            coEvery { mockApiService.getProductById(1) } returns mockProduct

            // Create repository and set API through reflection
            val repository = ProductDetailRepository()
            repository::class.java.getDeclaredField("api").apply {
                isAccessible = true
                set(repository, mockApiService)
            }

            // When
            val result = repository.getProductById(1)

            // Then
            assertEquals(mockProduct, result)
        }
    }

    @Test
    fun `getProductById should return null on exception`() {
        runTest {
            // Given
            val mockApiService = mockk<ApiService>()
            coEvery { mockApiService.getProductById(1) } throws Exception("Error")

            // Create repository and set API through reflection
            val repository = ProductDetailRepository()
            repository::class.java.getDeclaredField("api").apply {
                isAccessible = true
                set(repository, mockApiService)
            }

            // When
            val result = repository.getProductById(1)

            // Then
            assertNull(result)
        }
    }
}
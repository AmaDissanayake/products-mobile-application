package com.example.productsmobileapplication.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.productsmobileapplication.data.ProductDetailRepository
import com.example.productsmobileapplication.model.Product
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ProductDetailViewModel
    private val mockRepository: ProductDetailRepository = mockk()

    // Helper function to create test products
    private fun createTestProduct(
        id: Int = 1,
        title: String = "Test Product",
        price: Double = 9.99
    ): Product {
        return Product(
            id = id,
            title = title,
            description = "Test description",
            price = price,
            thumbnail = "",
            images = emptyList(),
            brand = null,
            category = "Test Category",
            tags = null,
            availabilityStatus = "In Stock"
        )
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductDetailViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProduct should update product on success`() {
        runTest {
            // Given
            val mockProduct = createTestProduct()
            coEvery { mockRepository.getProductById(1) } returns mockProduct

            // When
            viewModel.loadProduct(1)
            advanceUntilIdle()

            // Then
            assertEquals(mockProduct, viewModel.product.value)
            assertFalse(viewModel.isLoading.value)
            assertNull(viewModel.error.value)
        }
    }

    @Test
    fun `loadProduct should handle product not found`() {
        runTest {
            // Given
            coEvery { mockRepository.getProductById(1) } returns null

            // When
            viewModel.loadProduct(1)
            advanceUntilIdle()

            // Then
            assertNull(viewModel.product.value)
            assertFalse(viewModel.isLoading.value)
            assertEquals("Product not found", viewModel.error.value)
        }
    }

    @Test
    fun `loadProduct should handle errors`() {
        runTest {
            // Given
            val errorMsg = "Network error"
            coEvery { mockRepository.getProductById(1) } throws Exception(errorMsg)

            // When
            viewModel.loadProduct(1)
            advanceUntilIdle()

            // Then
            assertNull(viewModel.product.value)
            assertFalse(viewModel.isLoading.value)
            assertEquals("Failed to load product: $errorMsg", viewModel.error.value)
        }
    }
}
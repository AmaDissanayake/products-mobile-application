package com.example.productsmobileapplication.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.productsmobileapplication.data.ProductRepository
import com.example.productsmobileapplication.model.Product
import io.mockk.coEvery
import io.mockk.coVerify
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ProductListViewModel
    private val mockRepository: ProductRepository = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductListViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProducts should update products on success`() {
        runTest {
            // Given
            val mockProducts = listOf(createTestProduct(1), createTestProduct(2))
            coEvery { mockRepository.getProducts(any(), any()) } returns mockProducts

            // When
            viewModel.loadProducts()
            advanceUntilIdle()

            // Then
            assertEquals(2, viewModel.products.value.size)
            assertFalse(viewModel.isLoading.value)
            assertTrue(viewModel.error.value == null)
            assertEquals(1, viewModel.currentPage)
            assertFalse(viewModel.allItemsLoaded)
        }
    }

    @Test
    fun `loadProducts should handle errors`() {
        runTest {
            // Given
            val errorMsg = "Network error"
            coEvery { mockRepository.getProducts(any(), any()) } throws Exception(errorMsg)

            // When
            viewModel.loadProducts()
            advanceUntilIdle()

            // Then
            assertTrue(viewModel.products.value.isEmpty())
            assertEquals(errorMsg, viewModel.error.value)
            assertFalse(viewModel.isLoading.value)
            assertEquals(0, viewModel.currentPage)
        }
    }

    @Test
    fun `should not load when already loading`() {
        runTest {
            // Given
            viewModel.loadProducts() // set loading to true

            // When
            viewModel.loadProducts() // try to load again
            advanceUntilIdle()

            // Then
            coVerify(exactly = 1) { mockRepository.getProducts(any(), any()) }
        }
    }

    @Test
    fun `should detect end of list when response is smaller than page size`() {
        runTest {
            // Given
            val mockProducts = List(5) { createTestProduct(it) }
            coEvery { mockRepository.getProducts(any(), any()) } returns mockProducts

            // When
            viewModel.loadProducts()
            advanceUntilIdle()

            // Then
            assertTrue(viewModel.allItemsLoaded)
            assertEquals(5, viewModel.products.value.size)
        }
    }

    @Test
    fun `should paginate correctly through multiple pages`() {
        runTest {
            // Given
            val firstPageProducts = List(10) { createTestProduct(it) }
            val secondPageProducts = List(5) { createTestProduct(it + 10) }

            coEvery { mockRepository.getProducts(skip = 0, limit = 10) } returns firstPageProducts
            coEvery { mockRepository.getProducts(skip = 10, limit = 10) } returns secondPageProducts

            // First page load
            viewModel.loadProducts()
            advanceUntilIdle()

            // Verify first page
            assertEquals(10, viewModel.products.value.size)
            assertEquals(1, viewModel.currentPage)
            assertFalse(viewModel.allItemsLoaded)

            // Second page load
            viewModel.loadProducts()
            advanceUntilIdle()

            // Verify second page
            assertEquals(15, viewModel.products.value.size)
            assertEquals(2, viewModel.currentPage)
            assertTrue(viewModel.allItemsLoaded)
        }
    }

    // Helper function
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
}
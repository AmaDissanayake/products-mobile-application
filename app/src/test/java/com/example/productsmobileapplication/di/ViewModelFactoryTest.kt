package com.example.productsmobileapplication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.productsmobileapplication.viewmodels.ProductDetailViewModel
import com.example.productsmobileapplication.viewmodels.ProductListViewModel
import org.junit.Assert.assertTrue
import org.junit.Test

class ViewModelFactoryTest {

    @Test
    fun `create should return ProductListViewModel for ProductListViewModel class`() {
        // Given
        val factory = ViewModelFactory()

        // When
        val viewModel = factory.create(ProductListViewModel::class.java)

        // Then
        assertTrue(viewModel is ProductListViewModel)
    }

    @Test
    fun `create should return ProductDetailViewModel for ProductDetailViewModel class`() {
        // Given
        val factory = ViewModelFactory()

        // When
        val viewModel = factory.create(ProductDetailViewModel::class.java)

        // Then
        assertTrue(viewModel is ProductDetailViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `create should throw for unknown ViewModel class`() {
        // Given
        val factory = ViewModelFactory()

        // When
        factory.create(TestViewModel::class.java)
    }

    class TestViewModel : ViewModel()
}
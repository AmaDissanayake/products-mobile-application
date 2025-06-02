package com.example.productsmobileapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsmobileapplication.data.ProductRepository
import com.example.productsmobileapplication.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var currentPage = 0
    private val pageSize = 10
    var allItemsLoaded = false
        private set

    init {
        loadProducts()
    }

    fun loadProducts() {
        if (_isLoading.value || allItemsLoaded) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val newProducts = repository.getProducts(
                    skip = currentPage * pageSize,
                    limit = pageSize
                )

                _products.value = _products.value + newProducts
                currentPage++

                if (newProducts.size < pageSize) {
                    allItemsLoaded = true
                }
            } catch (e: Exception) {
                _error.value = "Failed to load products: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
package com.example.productsmobileapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsmobileapplication.data.ProductDetailRepository
import com.example.productsmobileapplication.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val repository: ProductDetailRepository = ProductDetailRepository()
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadProduct(productId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _product.value = repository.getProductById(productId)
            } catch (e: Exception) {
                _error.value = "Failed to load product: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
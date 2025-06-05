package com.example.productsmobileapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.productsmobileapplication.ui.screens.ProductDetailScreen
import com.example.productsmobileapplication.ui.screens.ProductListScreen
import com.example.productsmobileapplication.ui.theme.ProductsMobileApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductsMobileApplicationTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "productList"
                ) {
                    composable("productList") {
                        ProductListScreen(navController)
                    }
                    composable("productDetail/{productId}") { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId")
                        ProductDetailScreen(
                            navController = navController,
                            productId = productId?.toIntOrNull()
                        )
                    }
                }
            }
        }
    }
}
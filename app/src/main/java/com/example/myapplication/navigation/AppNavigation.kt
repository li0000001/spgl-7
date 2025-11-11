package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.ProductListScreen
import com.example.myapplication.ui.screens.ProductDetailScreen
import com.example.myapplication.ui.screens.AddProductScreen
import com.example.myapplication.viewmodel.ProductViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: ProductViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "product_list"
    ) {
        composable("product_list") {
            ProductListScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate("add_product") },
                onProductClick = { productId -> navController.navigate("product_detail/$productId") }
            )
        }

        composable("add_product") {
            AddProductScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("product_detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            if (productId != null) {
                ProductDetailScreen(
                    productId = productId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onEdit = { navController.navigate("edit_product/$productId") }
                )
            }
        }

        composable("edit_product/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            if (productId != null) {
                AddProductScreen(
                    productId = productId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

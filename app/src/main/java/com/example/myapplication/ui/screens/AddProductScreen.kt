package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.components.ProductForm
import com.example.myapplication.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    productId: Int? = null,
    viewModel: ProductViewModel,
    onBack: () -> Unit
) {
    var isLoading by remember { mutableStateOf(productId != null) }
    var product by remember { mutableStateOf<com.example.myapplication.data.Product?>(null) }

    LaunchedEffect(productId) {
        if (productId != null) {
            // In a real implementation, fetch the product from the database
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (productId == null) "添加商品" else "编辑商品") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "返回")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ProductForm(
                product = product,
                onSave = { newProduct ->
                    if (productId == null) {
                        viewModel.addProduct(newProduct)
                    } else {
                        viewModel.updateProduct(newProduct.copy(id = productId))
                    }
                    onBack()
                },
                onCancel = onBack
            )
        }
    }
}

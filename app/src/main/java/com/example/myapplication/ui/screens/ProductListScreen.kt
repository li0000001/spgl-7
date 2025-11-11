package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.components.ProductList
import com.example.myapplication.viewmodel.ProductViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductViewModel,
    onAddClick: () -> Unit,
    onProductClick: (Int) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val allProducts by viewModel.allProducts.observeAsState(emptyList())
    val validProducts by viewModel.validProducts.observeAsState(emptyList())
    val expiredProducts by viewModel.expiredProducts.observeAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddClick,
                icon = { Icon(Icons.Default.Add, "添加商品") },
                text = { Text("添加商品") }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("全部 (${allProducts.size})") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("有效 (${validProducts.size})") }
                )
                Tab(
                    selected = selectedTabIndex == 2,
                    onClick = { selectedTabIndex = 2 },
                    text = { Text("已过期 (${expiredProducts.size})") }
                )
            }

            when (selectedTabIndex) {
                0 -> {
                    if (allProducts.isEmpty()) {
                        EmptyStateView()
                    } else {
                        ProductList(
                            products = allProducts,
                            onProductClick = { onProductClick(it.id) },
                            onDeleteClick = { viewModel.deleteProduct(it) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                1 -> {
                    if (validProducts.isEmpty()) {
                        EmptyStateView()
                    } else {
                        ProductList(
                            products = validProducts,
                            onProductClick = { onProductClick(it.id) },
                            onDeleteClick = { viewModel.deleteProduct(it) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                2 -> {
                    if (expiredProducts.isEmpty()) {
                        EmptyStateView()
                    } else {
                        ProductList(
                            products = expiredProducts,
                            onProductClick = { onProductClick(it.id) },
                            onDeleteClick = { viewModel.deleteProduct(it) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "暂无商品",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "点击右下角浮动按钮添加商品",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

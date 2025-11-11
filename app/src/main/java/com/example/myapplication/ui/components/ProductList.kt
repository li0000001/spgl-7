package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.Product
import com.example.myapplication.ui.theme.GreenLight
import com.example.myapplication.ui.theme.OrangeLight
import com.example.myapplication.ui.theme.RedLight
import java.time.LocalDate

@Composable
fun ProductList(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onDeleteClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(products) { product ->
            ProductCard(
                product = product,
                onProductClick = { onProductClick(product) },
                onDeleteClick = { onDeleteClick(product) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onProductClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val daysRemaining = calculateDaysRemaining(product.expiryDate)
    val isExpired = isExpired(product.expiryDate)
    val cardColor = when {
        isExpired -> RedLight.copy(alpha = 0.1f)
        daysRemaining <= 3 -> OrangeLight.copy(alpha = 0.1f)
        else -> GreenLight.copy(alpha = 0.1f)
    }
    val textColor = when {
        isExpired -> RedLight
        daysRemaining <= 3 -> OrangeLight
        else -> GreenLight
    }

    Box(
        modifier = modifier
            .background(cardColor, RoundedCornerShape(12.dp))
            .clickable(onClick = onProductClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "过期日期: ${product.expiryDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when {
                        isExpired -> "已过期"
                        daysRemaining == 0 -> "今天过期"
                        daysRemaining == 1 -> "明天过期"
                        else -> "还剩 $daysRemaining 天"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "删除",
                    tint = Color.Red
                )
            }
        }
    }
}

private fun calculateDaysRemaining(expiryDate: LocalDate): Int {
    val today = LocalDate.now()
    return when {
        expiryDate.isBefore(today) -> -1
        else -> (expiryDate.toEpochDay() - today.toEpochDay()).toInt()
    }
}

private fun isExpired(expiryDate: LocalDate): Boolean {
    return LocalDate.now().isAfter(expiryDate)
}

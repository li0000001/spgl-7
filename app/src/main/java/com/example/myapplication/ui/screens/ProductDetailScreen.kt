package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.GreenLight
import com.example.myapplication.ui.theme.OrangeLight
import com.example.myapplication.ui.theme.RedLight
import com.example.myapplication.viewmodel.ProductViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductViewModel,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {
    var product by remember { mutableStateOf<com.example.myapplication.data.Product?>(null) }

    LaunchedEffect(productId) {
        // In a real implementation, fetch the product from the database
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("商品详情") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "返回")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (product != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                val daysRemaining = calculateDaysRemaining(product!!.expiryDate)
                val isExpired = isExpired(product!!.expiryDate)
                val textColor = when {
                    isExpired -> RedLight
                    daysRemaining <= 3 -> OrangeLight
                    else -> GreenLight
                }

                Text(
                    text = product!!.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                DetailItem(label = "生产日期", value = product!!.productionDate.toString())
                DetailItem(label = "保质期", value = "${product!!.shelfLifeDays} 天")
                DetailItem(label = "过期日期", value = product!!.expiryDate.toString())

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = when {
                        isExpired -> "已过期"
                        daysRemaining == 0 -> "今天过期"
                        daysRemaining == 1 -> "明天过期"
                        else -> "还剩 $daysRemaining 天"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(textColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                DetailItem(label = "提醒方式", value = product!!.reminderType.name)
                DetailItem(label = "提前提醒", value = "${product!!.reminderDaysInAdvance} 天")
                DetailItem(label = "提醒时间", value = product!!.reminderTime)

                if (product!!.notes.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailItem(label = "备注", value = product!!.notes)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.deleteProductById(productId); onBack() },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.Delete, "删除")
                    }
                    Button(
                        onClick = onEdit,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.Edit, "编辑")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("编辑")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
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

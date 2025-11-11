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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.Product
import com.example.myapplication.data.ReminderType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ProductForm(
    product: Product? = null,
    onSave: (Product) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    var productionDate by remember { mutableStateOf(product?.productionDate ?: LocalDate.now()) }
    var shelfLifeDays by remember { mutableStateOf(product?.shelfLifeDays?.toString() ?: "7") }
    var expiryDate by remember { mutableStateOf(product?.expiryDate ?: LocalDate.now().plusDays(7)) }
    var reminderDaysInAdvance by remember { mutableStateOf(product?.reminderDaysInAdvance?.toString() ?: "3") }
    var reminderTime by remember { mutableStateOf(product?.reminderTime ?: "09:00") }
    var reminderType by remember { mutableStateOf(product?.reminderType ?: ReminderType.NOTIFICATION) }
    var notes by remember { mutableStateOf(product?.notes ?: "") }

    var expandedReminderType by remember { mutableStateOf(false) }
    var useProductionDateMode by remember { mutableStateOf(product == null) }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = if (product == null) "添加商品" else "编辑商品",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("商品名称") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text("输入方式选择", style = MaterialTheme.typography.labelMedium)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            OutlinedButton(
                onClick = { useProductionDateMode = true },
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp),
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
            ) {
                Text("生产日期 + 保质期")
            }
            OutlinedButton(
                onClick = { useProductionDateMode = false },
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp),
                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
            ) {
                Text("直接输入过期日期")
            }
        }

        if (useProductionDateMode) {
            DatePickerField(
                label = "生产日期",
                date = productionDate,
                onDateSelected = { productionDate = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = shelfLifeDays,
                onValueChange = {
                    shelfLifeDays = it
                    if (it.isNotEmpty()) {
                        expiryDate = productionDate.plusDays(it.toIntOrNull() ?: 7L)
                    }
                },
                label = { Text("保质期(天)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "过期日期: $expiryDate",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                    .padding(12.dp)
            )
        } else {
            DatePickerField(
                label = "过期日期",
                date = expiryDate,
                onDateSelected = { expiryDate = it }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = reminderDaysInAdvance,
            onValueChange = { reminderDaysInAdvance = it },
            label = { Text("提前提醒天数") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = reminderTime,
            onValueChange = { reminderTime = it },
            label = { Text("提醒时间 (HH:mm)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = reminderType.name,
                onValueChange = {},
                label = { Text("提醒类型") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedReminderType = true },
                enabled = false
            )
            DropdownMenu(
                expanded = expandedReminderType,
                onDismissRequest = { expandedReminderType = false }
            ) {
                ReminderType.values().forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type.name) },
                        onClick = {
                            reminderType = type
                            expandedReminderType = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("备注") },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text("取消")
            }
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val newProduct = Product(
                            id = product?.id ?: 0,
                            name = name,
                            productionDate = productionDate,
                            shelfLifeDays = shelfLifeDays.toIntOrNull() ?: 7,
                            expiryDate = expiryDate,
                            reminderDaysInAdvance = reminderDaysInAdvance.toIntOrNull() ?: 3,
                            reminderTime = reminderTime,
                            reminderType = reminderType,
                            notes = notes,
                            calendarEventId = product?.calendarEventId
                        )
                        onSave(newProduct)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text("保存")
            }
        }
    }
}

@Composable
fun DatePickerField(
    label: String,
    date: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = dateFormatter.format(date),
        onValueChange = {},
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .clickable { showDatePicker = true },
        enabled = false,
        singleLine = true
    )

    if (showDatePicker) {
        DatePickerDialog(
            initialDate = date,
            onDateSelected = {
                onDateSelected(it)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@Composable
fun DatePickerDialog(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    var year by remember { mutableStateOf(initialDate.year.toString()) }
    var month by remember { mutableStateOf(initialDate.monthValue.toString().padStart(2, '0')) }
    var day by remember { mutableStateOf(initialDate.dayOfMonth.toString().padStart(2, '0')) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("选择日期") },
        text = {
            Column {
                OutlinedTextField(
                    value = year,
                    onValueChange = { year = it },
                    label = { Text("年") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = month,
                        onValueChange = { month = it.take(2) },
                        label = { Text("月") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = day,
                        onValueChange = { day = it.take(2) },
                        label = { Text("日") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    try {
                        val selectedDate = LocalDate.of(
                            year.toInt(),
                            month.toInt(),
                            day.toInt()
                        )
                        onDateSelected(selectedDate)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            ) {
                Text("确定")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}

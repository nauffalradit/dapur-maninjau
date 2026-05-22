package com.gondrongz.depotmaninjau.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gondrongz.depotmaninjau.data.menuList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashierScreen(onBack: () -> Unit) {
    var cartItems by remember { mutableStateOf(mutableMapOf<String, Int>()) }
    val totalAmount = cartItems.entries.sumOf { (id, qty) -> 
        (menuList.find { it.id == id }?.price ?: 0.0) * qty 
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buat Transaksi Baru") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Total Pembayaran", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Rp ${totalAmount.toInt()}", 
                            style = MaterialTheme.typography.headlineSmall, 
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* TODO: Process Transaction */ },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = totalAmount > 0
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Selesaikan Transaksi")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(menuList) { menu ->
                val quantity = cartItems[menu.id] ?: 0
                CashierItem(
                    name = menu.name,
                    price = "Rp ${menu.price.toInt()}",
                    quantity = quantity,
                    onIncrease = {
                        val newMap = cartItems.toMutableMap()
                        newMap[menu.id] = quantity + 1
                        cartItems = newMap
                    },
                    onDecrease = {
                        if (quantity > 0) {
                            val newMap = cartItems.toMutableMap()
                            if (quantity == 1) newMap.remove(menu.id) else newMap[menu.id] = quantity - 1
                            cartItems = newMap
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CashierItem(
    name: String,
    price: String,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontWeight = FontWeight.Bold)
                Text(text = price, style = MaterialTheme.typography.bodySmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease, enabled = quantity > 0) {
                    Text("-", style = MaterialTheme.typography.headlineMedium)
                }
                Text(
                    text = quantity.toString(),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onIncrease) {
                    Text("+", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }
    }
}

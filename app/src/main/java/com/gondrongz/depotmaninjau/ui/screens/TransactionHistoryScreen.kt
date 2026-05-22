package com.gondrongz.depotmaninjau.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Master Transaksi", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* TODO: Open Date Picker */ }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Filter Tanggal")
                    }
                    IconButton(onClick = { /* TODO: Export to Excel */ }) {
                        Icon(Icons.Default.FileDownload, contentDescription = "Export Excel")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Summary Header
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Mei 2024", fontWeight = FontWeight.Bold)
                    Text(text = "Total: Rp 15.400.000", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Dummy Data
                items(List(10) { it }) { index ->
                    TransactionHistoryItem(
                        id = "TRX-${1000 + index}",
                        date = "24 Mei 2024, 14:30",
                        amount = "Rp ${50000 + (index * 15000)}",
                        status = if (index % 3 == 0) "Lunas" else "Pending"
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionHistoryItem(
    id: String,
    date: String,
    amount: String,
    status: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = id, fontWeight = FontWeight.Bold)
                Text(text = date, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = amount, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                Surface(
                    color = if (status == "Lunas") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
                    shape = androidx.compose.foundation.shape.CircleShape
                ) {
                    Text(
                        text = status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (status == "Lunas") MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
}

package com.gondrongz.depotmaninjau.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gondrongz.depotmaninjau.data.menuList

import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMenuScreen(
    menuId: String,
    onBack: () -> Unit
) {
    val menu = menuList.find { it.id == menuId }
    var rating by remember { mutableIntStateOf(4) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Menu") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (menu != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = menu.imageResId),
                    contentDescription = menu.name,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = menu.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = "Rp ${menu.price.toInt()}", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))
                
                // Rating Vibe
                Row {
                    repeat(5) { index ->
                        IconButton(onClick = { rating = index + 1 }) {
                            Icon(
                                imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.StarOutline,
                                contentDescription = null,
                                tint = if (index < rating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = menu.description, fontSize = 16.sp)
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Kembali ke Menu")
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Menu tidak ditemukan")
            }
        }
    }
}

package com.gondrongz.depotmaninjau.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gondrongz.depotmaninjau.data.PreferenceManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    preferenceManager: PreferenceManager,
    onNavigateToEdit: () -> Unit,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    var profileData by remember { mutableStateOf(preferenceManager.getData()) }

    LaunchedEffect(Unit) {
        profileData = preferenceManager.getData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Master Profil Toko", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Store,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Column {
                        Text(
                            text = profileData["name"] ?: "Depot Maninjau",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Status Toko: Aktif",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ProfileItem(label = "Nama Toko", value = profileData["name"] ?: "Depot Maninjau")
            ProfileItem(label = "Alamat Lengkap", value = profileData["address"] ?: "Jl. Raya Maninjau No. 123")
            ProfileItem(label = "Deskripsi Bisnis", value = profileData["description"] ?: "Restoran tradisional Minang modern.")
            ProfileItem(label = "Jam Operasional", value = profileData["hours"] ?: "08:00 - 21:00")
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Pengaturan Tampilan", fontWeight = FontWeight.Bold)
                    Text("Mode Gelap", style = MaterialTheme.typography.bodySmall)
                }
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = onThemeChange
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = onNavigateToEdit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Edit Profil Toko")
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp), color = MaterialTheme.colorScheme.outlineVariant)
    }
}

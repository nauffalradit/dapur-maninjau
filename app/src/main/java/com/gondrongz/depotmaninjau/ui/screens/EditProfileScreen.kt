package com.gondrongz.depotmaninjau.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gondrongz.depotmaninjau.data.PreferenceManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    preferenceManager: PreferenceManager,
    onNavigateBack: () -> Unit
) {
    val initialData = remember { preferenceManager.getData() }
    
    var name by remember { mutableStateOf(initialData["name"] ?: "") }
    var address by remember { mutableStateOf(initialData["address"] ?: "") }
    var description by remember { mutableStateOf(initialData["description"] ?: "") }
    var hours by remember { mutableStateOf(initialData["hours"] ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Profil") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama Restoran") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Alamat") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Deskripsi") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = hours,
                onValueChange = { hours = it },
                label = { Text("Jam Buka") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Batal")
                }
                Button(
                    onClick = {
                        preferenceManager.saveData(name, address, description, hours)
                        onNavigateBack()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Simpan")
                }
            }
        }
    }
}

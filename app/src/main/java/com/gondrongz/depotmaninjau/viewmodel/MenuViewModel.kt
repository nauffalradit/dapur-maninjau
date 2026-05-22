package com.gondrongz.depotmaninjau.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gondrongz.depotmaninjau.data.local.AppDatabase
import com.gondrongz.depotmaninjau.data.local.entity.MenuEntity
import com.gondrongz.depotmaninjau.model.MenuItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val menuDao = db.menuDao()

    val allMenus: StateFlow<List<MenuItem>> = menuDao.getAllMenus()
        .map { entities ->
            entities.map { entity ->
                MenuItem(
                    id = entity.id.toString(),
                    name = entity.name,
                    price = entity.price,
                    description = entity.description,
                    imageResId = entity.imageResId
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addMenu(name: String, price: Double, description: String, imageResId: Int) {
        viewModelScope.launch {
            val entity = MenuEntity(
                name = name,
                price = price,
                description = description,
                imageResId = imageResId
            )
            menuDao.insertMenu(entity)
        }
    }

    fun deleteMenu(id: String) {
        viewModelScope.launch {
            // Finding the entity to delete. For simplicity in this POS prototype, 
            // we delete by name or you can fetch the entity first.
            // Ideally, we'd add a deleteById in DAO.
            val currentList = menuDao.getAllMenus().stateIn(viewModelScope).value
            val entityToDelete = currentList.find { it.id.toString() == id }
            if (entityToDelete != null) {
                menuDao.deleteMenu(entityToDelete)
            }
        }
    }
}

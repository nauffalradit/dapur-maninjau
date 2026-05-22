package com.gondrongz.depotmaninjau.data

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("restaurant_prefs", Context.MODE_PRIVATE)

    fun saveData(name: String, address: String, description: String, hours: String) {
        sharedPreferences.edit().apply {
            putString("name", name)
            putString("address", address)
            putString("description", description)
            putString("hours", hours)
            apply()
        }
    }

    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean("dark_mode", false)
    }

    fun setDarkMode(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("dark_mode", enabled).apply()
    }

    fun getData(): Map<String, String> {
        return mapOf(
            "name" to (sharedPreferences.getString("name", "Depot Maninjau") ?: "Depot Maninjau"),
            "address" to (sharedPreferences.getString("address", "Jl. Maninjau No. 1, Malang") ?: "Jl. Maninjau No. 1, Malang"),
            "description" to (sharedPreferences.getString("description", "Masakan Padang Otentik Sejak 1990") ?: "Masakan Padang Otentik Sejak 1990"),
            "hours" to (sharedPreferences.getString("hours", "08:00 - 21:00") ?: "08:00 - 21:00")
        )
    }
}

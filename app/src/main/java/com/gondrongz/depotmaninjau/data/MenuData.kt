package com.gondrongz.depotmaninjau.data

import com.gondrongz.depotmaninjau.R
import com.gondrongz.depotmaninjau.model.MenuItem

val menuList = listOf(
    MenuItem(
        id = "1",
        name = "Nasi Padang Rendang",
        price = 25000.0,
        description = "Nasi hangat dengan rendang daging sapi empuk, sayur nangka, dan sambal ijo.",
        imageResId = R.drawable.ic_launcher_foreground
    ),
    MenuItem(
        id = "2",
        name = "Ayam Pop",
        price = 20000.0,
        description = "Ayam goreng gurih dengan bumbu rahasia khas Minang.",
        imageResId = R.drawable.ic_launcher_foreground
    ),
    MenuItem(
        id = "3",
        name = "Sate Padang",
        price = 18000.0,
        description = "Sate lidah sapi dengan kuah kental kuning yang kaya rempah.",
        imageResId = R.drawable.ic_launcher_foreground
    ),
    MenuItem(
        id = "4",
        name = "Es Teh Manis",
        price = 5000.0,
        description = "Es teh segar untuk menemani makan siang Anda.",
        imageResId = R.drawable.ic_launcher_foreground
    ),
    MenuItem(
        id = "5",
        name = "Es Jeruk",
        price = 7000.0,
        description = "Perasan jeruk asli yang menyegarkan.",
        imageResId = R.drawable.ic_launcher_foreground
    )
)

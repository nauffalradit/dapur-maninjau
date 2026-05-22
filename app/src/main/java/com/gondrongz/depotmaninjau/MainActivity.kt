package com.gondrongz.depotmaninjau

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gondrongz.depotmaninjau.data.PreferenceManager
import com.gondrongz.depotmaninjau.ui.screens.*
import com.gondrongz.depotmaninjau.ui.theme.DepotManinjauTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val preferenceManager = remember { PreferenceManager(context) }
            var isDarkMode by remember { mutableStateOf(preferenceManager.isDarkMode()) }

            DepotManinjauTheme(darkTheme = isDarkMode) {
                DepotManinjauApp(
                    preferenceManager = preferenceManager,
                    isDarkMode = isDarkMode,
                    onThemeChange = { 
                        isDarkMode = it
                        preferenceManager.setDarkMode(it)
                    }
                )
            }
        }
    }
}

@Composable
fun DepotManinjauApp(
    preferenceManager: PreferenceManager,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val showBottomBar = currentDestination in AppDestinations.entries.map { it.route }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController, currentDestination)
            }
        }
//        bottomBar = {
//            if (showBottomBar) {
//                BottomNavigationBar(navController, currentDestination)
//            }
//        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            preferenceManager = preferenceManager,
            isDarkMode = isDarkMode,
            onThemeChange = onThemeChange,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, currentDestination: String?) {
    NavigationBar(
        modifier = Modifier
            .height(140.dp)
            .clip(RoundedCornerShape(20.dp)),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        tonalElevation = 8.dp
    ) {
        AppDestinations.entries.forEach { destination ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = destination.label,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 9.sp
                    )
                },
                selected = currentDestination == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(AppDestinations.HOME.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                )
            )
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    preferenceManager: PreferenceManager,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier,
        enterTransition = { fadeIn(tween(400)) + slideInHorizontally(tween(400)) { 1000 } },
        exitTransition = { fadeOut(tween(400)) + slideOutHorizontally(tween(400)) { -1000 } },
        popEnterTransition = { fadeIn(tween(400)) + slideInHorizontally(tween(400)) { -1000 } },
        popExitTransition = { fadeOut(tween(400)) + slideOutHorizontally(tween(400)) { 1000 } }
    ) {
        composable("splash") {
            SplashScreen(onNavigateToHome = {
                navController.navigate(AppDestinations.HOME.route) {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable(AppDestinations.HOME.route) {
            HomeScreen(
                onNavigateToTransaction = { navController.navigate("cashier") },
                onNavigateToMenu = { navController.navigate(AppDestinations.PRODUCTS.route) }
            )
        }
        composable("cashier") {
            CashierScreen(onBack = { navController.popBackStack() })
        }
        composable(AppDestinations.PRODUCTS.route) {
            ProductManagementScreen()
        }
        composable(AppDestinations.TRANSACTIONS.route) {
            TransactionHistoryScreen()
        }
        composable(AppDestinations.PROFILE.route) {
            ProfileScreen(
                preferenceManager = preferenceManager,
                onNavigateToEdit = { /* TODO */ },
                isDarkMode = isDarkMode,
                onThemeChange = onThemeChange
            )
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
) {
    HOME("Beranda", Icons.Default.Dashboard, "home"),
    PRODUCTS("Produk", Icons.Default.Inventory, "products"),
    TRANSACTIONS("Transaksi", Icons.Default.ReceiptLong, "transactions"),
    PROFILE("Profil", Icons.Default.Storefront, "profile"),
}

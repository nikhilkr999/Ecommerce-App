package com.compose.ecommerceapp.screens.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.compose.ecommerceapp.screens.navigation.Screens

@Composable
fun BottomNavigationBar(
    navController: NavController
){
    val currentRoute = ""
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, Screens.Home.route),
        BottomNavItem("Categories", Icons.Default.Search, Screens.CategoryList.route),
        BottomNavItem("Wishlist", Icons.Default.Favorite, Screens.Cart.route, 5),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, Screens.Cart.route, 3),
        BottomNavItem("Profile", Icons.Default.Person, Screens.Profile.route)
    )

    NavigationBar(
        modifier = Modifier.height(82.dp),
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {

        //converting current backStack entry into state
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        //getting the route of current destination
        val currentRoute = currentBackStackEntry?.destination?.route
        items.forEach {item ->

            NavigationBarItem(
                icon = {
                    if (item.badgeCount > 0) {

                        BadgedBox(
                            badge = {Badge { Text(item.badgeCount.toString()) }},
                        ){
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }else{
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = {Text(item.title)},
                selected = currentRoute == item.route,
                onClick = {
                    //navigate between screens   //VVI use in all apps
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val badgeCount:Int = 0
)

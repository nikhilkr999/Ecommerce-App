package com.compose.ecommerceapp.screens.navigation


sealed class Screens(val route: String) {

    object Cart: Screens("Cart")
    object Wishlist: Screens("Wishlist")

    object ProductDetails: Screens("product_details/{productId}"){
        fun createRoute(productId: String) = "product_details/$productId"
    }


    object ProductList : Screens("product_list/{categoryId}/{categoryName}") {
        fun createRoute(categoryId: String, categoryName: String): String {
            return "product_list/$categoryId/$categoryName"
        }
    }

    object CategoryList: Screens("category_list")
    object Profile: Screens("Profile")
    object Login: Screens("Login")
    object SignUp: Screens("SignUp")
    object Home: Screens("Home")
}
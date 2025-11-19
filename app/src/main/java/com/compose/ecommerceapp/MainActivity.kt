package com.compose.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compose.ecommerceapp.screens.cart.CartScreen
import com.compose.ecommerceapp.screens.categories.CategoryScreen
import com.compose.ecommerceapp.screens.home.HomeScreen
import com.compose.ecommerceapp.screens.navigation.Screens
import com.compose.ecommerceapp.screens.products.ProductDetailsScreen
import com.compose.ecommerceapp.screens.products.ProductScreen
import com.compose.ecommerceapp.screens.profile.LoginScreen
import com.compose.ecommerceapp.screens.profile.ProfileScreen
import com.compose.ecommerceapp.screens.profile.SignUpScreen
import com.compose.ecommerceapp.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            //authViewModel
            val authViewModel : AuthViewModel = hiltViewModel()

            //properly observe the state
            val isLoggedIn by remember {
                derivedStateOf {
                    authViewModel.isLoggedIn
                }
            }

            NavHost(
                navController = navController,
                startDestination = "Home"
            ){
                composable(Screens.Home.route){
                    HomeScreen(
                        navController = navController,
                        onProfileCLick = {navController.navigate(Screens.Profile.route)},
                        onCartClick = {navController.navigate(Screens.Cart.route)}
                    )
                }

                composable(Screens.Cart.route){
                    CartScreen(navController = navController)
                }

                composable(Screens.Profile.route) {
                    ProfileScreen(
                        navController = navController,
                        onSignOut ={
                            authViewModel.signOut()
                            navController.navigate(Screens.Login.route)
                        })
                }

                composable(Screens.CategoryList.route){
                    CategoryScreen(navController = navController,
                        onCartClick = {navController.navigate(Screens.Cart.route)},

                        onProfileClick = {
                            if(isLoggedIn ){
                                navController.navigate(Screens.Profile.route)
                            }
                            else {
                                navController.navigate(Screens.Login.route)
                            }
                        }
                        )
                }

                composable (Screens.ProductDetails.route){
                    val productId = it.arguments?.getString("productId")
                    if(productId!=null){
                        ProductDetailsScreen(productId)
                    }
                }

                composable (Screens.ProductList.route){
                    val categoryId = it.arguments?.getString("categoryId")
                    val categoryName = it.arguments?.getString("categoryName")
                    if(categoryId!=null){
                        ProductScreen(categoryId,categoryName,navController)
                    }
                }

                composable (Screens.SignUp.route){
                    SignUpScreen(
                        onNavigateToLogin = {
                            navController.navigate(Screens.Login.route)
                        },
                        onSignUpSuccess = {
                            navController.navigate(Screens.Home.route)
                        }
                    )
                }
                composable(Screens.SignUp.route){
                    SignUpScreen(
                        onNavigateToLogin = {
                            navController.navigate(Screens.Login.route)
                        },
                        onSignUpSuccess = {
                            navController.navigate(Screens.Home.route)
                        }
                    )
                }

                composable(Screens.Login.route){
                    LoginScreen(
                        onNavigateToSignUp = {
                            navController.navigate(Screens.SignUp.route)
                        },
                        onLoginSuccess = {
                            navController.navigate(Screens.CategoryList.route)
                        }
                    )
                }
            }
        }
    }
}

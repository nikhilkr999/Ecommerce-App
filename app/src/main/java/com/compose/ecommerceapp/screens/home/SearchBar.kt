package com.compose.ecommerceapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.compose.ecommerceapp.model.Product
import com.compose.ecommerceapp.screens.navigation.Screens
import com.compose.ecommerceapp.screens.products.ProductItem
import com.compose.ecommerceapp.viewmodels.CartViewModel
import com.compose.ecommerceapp.viewmodels.SearchViewModel

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: ()-> Unit,
    modifier: Modifier = Modifier
){

    Box(
        modifier = Modifier.height(52.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color.LightGray.copy(0.2f)),
        contentAlignment = Alignment.Center
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            Icon(Icons.Default.Search, "Search")
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Search products...", color = Color.Gray, fontSize = 16.sp)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {onSearch()}
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor  = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )

            )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SearchResultSection(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val searchResult = searchViewModel.searchResults.value
    val isSearching = searchViewModel.isSearching.value

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Search Results",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 16.dp,
                vertical = 16.dp)
        )

        if(isSearching){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }else{
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(searchResult.size){
                    index -> val product = searchResult[index]

                    ProductItem(
                        product = product,
                        onClick = {navController.navigate(Screens.ProductDetails.createRoute(product.id))},
                        onAddToCart = {cartViewModel.addToCart(product)}
                    )
                }
            }
        }
    }
}
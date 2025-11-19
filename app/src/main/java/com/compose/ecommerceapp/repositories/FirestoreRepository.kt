package com.compose.ecommerceapp.repositories

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.compose.ecommerceapp.model.Category
import com.compose.ecommerceapp.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.emptyList

@Singleton
class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
){
    private val TAG = "FirestoreRepository"

    fun getCategoriesFlow(): Flow<List<Category>> =
        callbackFlow {
            Log.d(TAG, "Starting category listener...") // LOG 1

            val listenerRegistration = firestore
                .collection("categories")
                .addSnapshotListener { snapshot, error ->
                    if (error != null){
                        Log.e(TAG, "Error fetching categories: ${error.message}") // LOG 2
                        return@addSnapshotListener
                    }

                    if(snapshot != null){
                        val categories = snapshot.toObjects(Category::class.java)
                        Log.i(TAG, "Fetched categories count: ${categories.size}") // LOG 3
                        trySend(categories)
                    } else {
                        Log.w(TAG, "Snapshot is null while fetching categories.") // LOG 4
                    }
                }

            awaitClose {
                Log.d(TAG, "Category listener removed.") // LOG 5
                listenerRegistration.remove()
            }
        }

    suspend fun getProductByCategory(categoryId: String): List<Product>{
        Log.d(TAG, "Fetching products for categoryId: $categoryId") // LOG
        return try {
            val result = firestore.collection("products")
                .whereEqualTo("categoryId", categoryId)
                .get()
                .await()

            Log.d(TAG, "Firestore returned ${result.size()} products") // LOG

            result.toObjects(Product::class.java).also {
                Log.v(TAG,"Mapped products: $it")
            }
        } catch (e: Exception){
            Log.e(TAG, "Error fetching products: ${e.message}") // LOG
            emptyList()
        }
    }

    suspend fun getProductById(productId: String): Product?{
        Log.d(TAG, "Fetching product with productId: $productId") // LOG
        return try {
            val result = firestore.collection("products")
                .document(productId)
                .get()
                .await()
            val product = result.toObject(Product::class.java)
            Log.i(TAG, "Product fetch result: $product") // LOG
            product
        } catch (e: Exception){
            Log.e(TAG, "Error fetching product: ${e.message}") // LOG
            null
        }
    }

    suspend fun getAllProductsInFirestore(): List<Product> {
        Log.d(TAG, "Fetching all products") // LOG
        return try {
            val allProducts = firestore.collection("products")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Product::class.java) }
            Log.i(TAG, "Total products fetched: ${allProducts.size}") // LOG
            allProducts
        } catch (e: Exception){
            Log.e(TAG, "Failed to fetch all products: ${e.message}") // LOG
            emptyList()
        }
    }

    suspend fun searchProducts(query : String):List<Product>{

        return try {
            val searchQuery = query.toLowerCase()

            val allProducts = firestore.collection("products")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Product::class.java) }

            allProducts.filter { product ->
                product.name.lowercase().contains(searchQuery)
            }
        }catch (e : Exception){
            Log.e("taggy", "error searching products")
            emptyList()
        }

    }
}

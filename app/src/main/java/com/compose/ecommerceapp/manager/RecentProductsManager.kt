package com.compose.ecommerceapp.manager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "recent_products")
class RecentProductsManager(context: Context) {
    private val dataStore = context.dataStore
    private val RECENT_PRODUCTS_KEY = stringSetPreferencesKey("recent_products")

    companion object {
        const val MAX_RECENT = 20 // Limit history
    }

    val recentProductIds: Flow<Set<String>> = dataStore.data
        .map { preferences ->
            preferences[RECENT_PRODUCTS_KEY] ?: emptySet()
        }

    suspend fun addProduct(productId: String) {
        dataStore.edit { preferences ->
            val currentSet = preferences[RECENT_PRODUCTS_KEY]?.toMutableSet() ?: mutableSetOf()
            currentSet.remove(productId) // Remove if exists (to bring to front)
            currentSet.add(productId)
            if (currentSet.size > MAX_RECENT) {
                currentSet.remove(currentSet.first()) // Remove oldest
            }
            preferences[RECENT_PRODUCTS_KEY] = currentSet
        }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
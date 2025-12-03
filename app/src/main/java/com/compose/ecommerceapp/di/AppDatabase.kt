package com.compose.ecommerceapp.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.compose.ecommerceapp.model.Product
import com.compose.ecommerceapp.model.WishlistProduct
import com.compose.ecommerceapp.room.CartDao
import com.compose.ecommerceapp.room.WishlistDao

@Database(entities = [Product::class, WishlistProduct::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    //Defines Dao here to interact with db

    abstract fun cartDao() : CartDao
    abstract fun wishlistDao() : WishlistDao

    //Singleton instance
    companion object{

        @Volatile    //enures visibilty of changes across threads
        private var INSTANCE: AppDatabase ?= null

        fun getDatabase(context : Context): AppDatabase{

            // if INSTANCE is not null then---> return it
            //if INSTANCE is null then ---> execute it
            //the synchronized block to create the DB instance
            //synchronized : only one thread can execute at a time


            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ecommerce_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
package com.example.inhuis.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IngredientDao {

    @Query("SELECT * from ingredient ORDER BY name ASC")
    fun getIngredients(): LiveData<List<Ingredient>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: Ingredient)

    @Query("DELETE FROM ingredient")
    suspend fun deleteAll()
}
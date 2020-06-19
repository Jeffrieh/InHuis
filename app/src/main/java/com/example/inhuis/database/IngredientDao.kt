package com.example.inhuis.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IngredientDao {

    @Query("SELECT *, SUM(amount) as amount from ingredient GROUP BY name ORDER BY name ASC")
    fun getIngredients(): LiveData<List<Ingredient>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: Ingredient)

    @Query("DELETE FROM ingredient")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(ingredient: Ingredient)

}
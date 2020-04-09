package com.example.recipe.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY name")
    fun getRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE type LIKE :type ORDER BY name")
    fun getRecipesByType(type: String): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    fun getRecipe(recipeId: String): LiveData<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

}
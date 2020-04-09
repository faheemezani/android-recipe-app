package com.example.recipe.data

class RecipeRepository(private val recipeDao: RecipeDao) {

    fun getRecipes() = recipeDao.getRecipes()

    fun getRecipe(recipeId: String) = recipeDao.getRecipe(recipeId)

    fun getRecipesByType(type: String) = recipeDao.getRecipesByType(type)

    suspend fun insert(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

}
package com.example.recipe.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val recipeId: Int,
    val name: String?,
    val type: String?,
    val ingredients: String?,
    val steps: String?,
    val notes: String?,
    val picture: String?
) {
    constructor(
        name: String?,
        type: String?,
        ingredients: String?,
        steps: String?,
        notes: String?,
        picture: String?
    ) : this(0, name, type, ingredients, steps, notes, picture)

}
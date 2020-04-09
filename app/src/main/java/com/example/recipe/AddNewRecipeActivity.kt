package com.example.recipe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import com.example.recipe.adapters.RecipeAdapter

import kotlinx.android.synthetic.main.activity_add_new_recipe.*
import kotlinx.android.synthetic.main.content_add_new_recipe.*

class AddNewRecipeActivity : AppCompatActivity() {

    var hasType = false
    private var recipeId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_recipe)
        setSupportActionBar(toolbar)

        if (intent != null) {
            if (intent.getIntExtra(RecipeAdapter.RECIPE_ID, -1) != -1)
                recipeId = intent.getIntExtra(RecipeAdapter.RECIPE_ID, -1)
            if (intent.getStringExtra(RecipeAdapter.RECIPE_TYPE) != null)
                hasType = true
            if (intent.getStringExtra(RecipeAdapter.RECIPE_NAME) != null)
                edRecipeName.setText(intent.getStringExtra(RecipeAdapter.RECIPE_NAME))
            if (intent.getStringExtra(RecipeAdapter.RECIPE_INGREDIENTS) != null)
                edIngredients.setText(intent.getStringExtra(RecipeAdapter.RECIPE_INGREDIENTS))
            if (intent.getStringExtra(RecipeAdapter.RECIPE_STEPS) != null)
                edSteps.setText(intent.getStringExtra(RecipeAdapter.RECIPE_STEPS))
        }

        setupUI()

        fab.setOnClickListener { _ ->
            val replyIntent = Intent()
            if (TextUtils.isEmpty(edRecipeName.text)) {
                Toast.makeText(this, "Please enter at least the recipe name", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                if (recipeId != -1) replyIntent.putExtra(RECIPE_ID, recipeId)
                replyIntent.putExtra(RECIPE_TYPE, spRecipeTypes.selectedItem.toString())
                replyIntent.putExtra(RECIPE_NAME, edRecipeName.text.toString())
                replyIntent.putExtra(RECIPE_INGREDIENTS, edIngredients.text.toString())
                replyIntent.putExtra(RECIPE_STEPS, edSteps.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    private fun setupUI() {
        // Setup spinner for filtering recipes based on types
        val spinner: AppCompatSpinner = findViewById(R.id.spRecipeTypes)
        ArrayAdapter.createFromResource(
            this,
            R.array.recipetypes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            if (hasType) {
                hasType = false
                val recipeType = intent.getStringExtra(RecipeAdapter.RECIPE_TYPE)
                val position = adapter.getPosition(recipeType)
                spRecipeTypes.setSelection(position)
            }
        }

    }

    companion object {
        const val RECIPE_ID = "com.example.recipe.RECIPE_ID"
        const val RECIPE_NAME = "com.example.recipe.RECIPE_NAME"
        const val RECIPE_TYPE = "com.example.recipe.RECIPE_TYPE"
        const val RECIPE_INGREDIENTS = "com.example.recipe.RECIPE_INGREDIENTS"
        const val RECIPE_STEPS = "com.example.recipe.RECIPE_STEPS"
    }

}

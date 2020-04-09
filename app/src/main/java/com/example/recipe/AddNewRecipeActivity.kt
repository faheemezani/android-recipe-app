package com.example.recipe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe.adapters.RecipeAdapter

import kotlinx.android.synthetic.main.activity_add_new_recipe.*
import kotlinx.android.synthetic.main.content_add_new_recipe.*
import kotlinx.android.synthetic.main.recipe_item.*

class AddNewRecipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_recipe)
        setSupportActionBar(toolbar)

        if (intent != null) {
            if (intent.getStringExtra(RecipeAdapter.RECIPE_NAME) != null)
                edRecipeName.setText(intent.getStringExtra(RecipeAdapter.RECIPE_NAME))
            if (intent.getStringExtra(RecipeAdapter.RECIPE_INGREDIENTS) != null)
                edIngredients.setText(intent.getStringExtra(RecipeAdapter.RECIPE_INGREDIENTS))
            if (intent.getStringExtra(RecipeAdapter.RECIPE_STEPS) != null)
                edSteps.setText(intent.getStringExtra(RecipeAdapter.RECIPE_STEPS))
        }

        fab.setOnClickListener { _ ->
            val replyIntent = Intent()
            if (TextUtils.isEmpty(edRecipeName.text)) {
                Toast.makeText(this, "Please enter at least the recipe name", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(RECIPE_NAME, edRecipeName.text.toString())
                replyIntent.putExtra(RECIPE_INGREDIENTS, edIngredients.text.toString())
                replyIntent.putExtra(RECIPE_STEPS, edSteps.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val RECIPE_NAME = "com.example.recipe.RECIPE_NAME"
        const val RECIPE_TYPE = "com.example.recipe.RECIPE_TYPE"
        const val RECIPE_INGREDIENTS = "com.example.recipe.RECIPE_INGREDIENTS"
        const val RECIPE_STEPS = "com.example.recipe.RECIPE_STEPS"
    }

}

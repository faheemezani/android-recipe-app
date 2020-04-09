package com.example.recipe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.adapters.RecipeAdapter
import com.example.recipe.data.Recipe
import com.example.recipe.viewmodels.RecipeListViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    val newRecipeActivityRequestCode = 1
    val existingRecipeActivityRequestCode = 2
    private lateinit var mViewModel: RecipeListViewModel
    private lateinit var mRecipeAdapter: RecipeAdapter
    private var allRecipeList: MutableList<Recipe> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupUI()

        fab.setOnClickListener { _ ->
            val intent = Intent(this, AddNewRecipeActivity::class.java)
            startActivityForResult(intent, newRecipeActivityRequestCode)
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
        }
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                /** Do nothing */
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (spRecipeTypes.selectedItemPosition != 0) {
                    true -> {
                        val filteredList = allRecipeList.filter { recipes ->
                            recipes.type ==  spRecipeTypes.selectedItem.toString() }
                        mRecipeAdapter.reloadRecipeList(filteredList)
                    }
                    else -> mRecipeAdapter.reloadRecipeList(allRecipeList)
                }
            }
        }

        // Setup recyclerview to list down the recipes
        mRecipeAdapter = RecipeAdapter(this, mutableListOf())
        rvRecipes.adapter = mRecipeAdapter
        rvRecipes.layoutManager = LinearLayoutManager(this)

        mViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        mViewModel.allRecipes.observe(this, Observer { recipes ->
            allRecipeList.clear(); allRecipeList.addAll(recipes)
            recipes?.let { mRecipeAdapter.reloadRecipeList(it) }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == newRecipeActivityRequestCode ||
                    requestCode == existingRecipeActivityRequestCode) && resultCode == Activity.RESULT_OK) {
            val recipeName = data?.getStringExtra(AddNewRecipeActivity.RECIPE_NAME)
            if (recipeName != null) {
                val recipe = when (requestCode) {
                    newRecipeActivityRequestCode -> {
                        Recipe(
                            recipeName,
                            data.getStringExtra(AddNewRecipeActivity.RECIPE_TYPE),
                            data.getStringExtra(AddNewRecipeActivity.RECIPE_INGREDIENTS),
                            data.getStringExtra(AddNewRecipeActivity.RECIPE_STEPS), "", ""
                        )
                    }
                    else -> {
                        Recipe(
                            data.getIntExtra(AddNewRecipeActivity.RECIPE_ID, -1),
                            recipeName,
                            data.getStringExtra(AddNewRecipeActivity.RECIPE_TYPE),
                            data.getStringExtra(AddNewRecipeActivity.RECIPE_INGREDIENTS),
                            data.getStringExtra(AddNewRecipeActivity.RECIPE_STEPS), "", ""
                        )
                    }
                }
                mViewModel.insert(recipe)
            }
        }
    }

}

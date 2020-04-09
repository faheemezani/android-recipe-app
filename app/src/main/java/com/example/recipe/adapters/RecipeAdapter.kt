package com.example.recipe.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe.AddNewRecipeActivity
import com.example.recipe.R
import com.example.recipe.data.Recipe
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipeAdapter(val mContext: Context, var recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvRecipeName?.text = recipes[position].name
        holder.llItem?.setOnClickListener {
            val intent = Intent(mContext, AddNewRecipeActivity::class.java)
            intent.putExtra(RECIPE_NAME, holder.tvRecipeName?.text)
            intent.putExtra(RECIPE_INGREDIENTS, recipes[position].ingredients)
            intent.putExtra(RECIPE_STEPS, recipes[position].steps)
            mContext.startActivity(intent)
        }
    }

    fun reloadRecipeList(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var llItem: LinearLayout? = itemView.llItem
        internal var tvRecipeName: TextView? = itemView.tvRecipeName
        internal var ivRecipeImage: ImageView? = itemView.ivRecipeImage
    }

    companion object {
        const val RECIPE_NAME = "com.example.recipe.RECIPE_NAME"
        const val RECIPE_TYPE = "com.example.recipe.RECIPE_TYPE"
        const val RECIPE_INGREDIENTS = "com.example.recipe.RECIPE_INGREDIENTS"
        const val RECIPE_STEPS = "com.example.recipe.RECIPE_STEPS"
    }

}
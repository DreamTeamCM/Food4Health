package com.food4health.domain.UseCases

import com.food4health.data.DataBase.Food4Health_DataBase
import com.food4health.data.Model.Recipe

class RecipeUseCases {

    val f4hDB = Food4Health_DataBase()

    suspend fun getAllRecipes(): ArrayList<Recipe>{
        return f4hDB.getAllRecipes()
    }

    suspend fun getRecipe(id: String): Recipe{
        return f4hDB.getRecipe(id)
    }

    suspend fun addRecipe(newRecipe: Recipe){
        return f4hDB.addRecipe(newRecipe)
    }

    suspend fun setRecipe(settedRecipe: Recipe){
        return f4hDB.setRecipe(settedRecipe)
    }

    suspend fun getFavouriteRecipes(email:String): ArrayList<Recipe>{
        return f4hDB.getFavouriteRecipes(email)
    }
}
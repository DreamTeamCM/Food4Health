package com.food4health.presentation.Favourite.Model

import com.food4health.data.Model.Recipe

interface FavouriteViewModel {

    suspend fun getFavouriteRecipes(email: String): List<Recipe>

}
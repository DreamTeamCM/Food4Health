package com.food4health.presentation.AddRecipe

import com.food4health.data.Model.Recipe


interface AddRecipeContract {

    interface AddRecipeView{

        fun showError(error: Int)
        fun showMessage(message: Int)
        fun showAddRecipeProgressBar()
        fun hideAddRecipeProgressBar()
        fun enableAddRecipeButton()
        fun disableAddRecipeButton()
        fun addRecipe()
        fun navigateToRecipe(id: String)

    }

    interface AddRecipePresenter{

        fun attachView(view: AddRecipeContract.AddRecipeView)
        fun dettachView()
        fun isViewAttached(): Boolean

        fun checkEmptyAddRecipeName(name: String): Boolean
        fun checkEmptyAddRecipeDescription(description: String): Boolean
        fun checkEmptyAddRecipeIngredients(ingredients: ArrayList<String>): Boolean
        fun checkEmptyAddRecipePreparation(preparation: Map<String, String>): Boolean
        fun checkEmptyAddRecipeSuggestions(suggestions: String): Boolean
        fun checkEmptyFields(name: String, description: String, ingredients: ArrayList<String>, preparation: Map<String, String>, suggestions: String): Boolean

        fun addRecipe(newRecipe: Recipe)

    }

}
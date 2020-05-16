package com.food4health.presentation.SetRecipe

import com.food4health.data.Model.Recipe

interface SetRecipeContract {

    interface SetRecipeView{
        fun showError(error: Int)
        fun showMessage(message: Int)
        fun showSetRecipeProgressBar()
        fun hideSetRecipeProgressBar()
        fun enableSetRecipeButton()
        fun disableSetRecipeButton()

        fun initSetRecipeContent()

        fun addIngredient()
        fun addIngredient(ingregient: String)
        fun addStep()
        fun addStep(step: String)

        fun setRecipe()
        fun navigateToRecipe(id: String)

    }

    interface SetRecipePresenter{

        fun attachView(view: SetRecipeContract.SetRecipeView)
        fun dettachView()
        fun isViewAttached(): Boolean

        fun checkEmptySetRecipeName(name: String): Boolean
        fun checkEmptySetRecipeDescription(description: String): Boolean
        fun checkEmptySetRecipeIngredients(ingredients: ArrayList<String>): Boolean
        fun checkEmptySetRecipePreparation(preparation: Map<String, String>): Boolean
        fun checkEmptySetRecipeSuggestions(suggestions: String): Boolean
        fun checkEmptyFields(name: String, description: String, ingredients: ArrayList<String>, preparation: Map<String, String>, suggestions: String): Boolean

        fun setRecipe(newRecipe: Recipe)
    }

}
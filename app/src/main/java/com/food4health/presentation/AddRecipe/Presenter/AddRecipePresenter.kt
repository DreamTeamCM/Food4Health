package com.food4health.presentation.AddRecipe.Presenter

import android.util.Log
import com.food4health.Food4Health
import com.food4health.base.Exceptions.FirebaseAddRecipeException
import com.food4health.data.Model.Recipe
import com.food4health.presentation.AddRecipe.AddRecipeContract
import com.food4health.presentation.AddRecipe.Model.AddRecipeViewModel
import com.sinergia.food4health.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddRecipePresenter(addRecipeViewModel: AddRecipeViewModel): AddRecipeContract.AddRecipePresenter, CoroutineScope {

    private val TAG = "[ADDRECIPE_ACTIVITY]"
    private val addRecipeJob = Job()

    var view: AddRecipeContract.AddRecipeView? = null
    var addRecipeViewModel: AddRecipeViewModel ?= null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + addRecipeJob

    init{
        this.addRecipeViewModel = addRecipeViewModel
    }

    override fun attachView(view: AddRecipeContract.AddRecipeView) {
        this.view = view
    }

    override fun dettachView() {
        this.view = null
    }

    override fun isViewAttached(): Boolean {
        return this.view != null
    }

    override fun checkEmptyAddRecipeName(name: String): Boolean {
        return name.isNullOrEmpty()
    }

    override fun checkEmptyAddRecipeDescription(description: String): Boolean {
        return description.isNullOrEmpty()
    }

    override fun checkEmptyAddRecipeIngredients(ingredients: ArrayList<String>): Boolean {
        return ingredients.isEmpty()
    }

    override fun checkEmptyAddRecipePreparation(preparation: Map<String, String>): Boolean {
        return preparation.isEmpty()
    }

    override fun checkEmptyAddRecipeSuggestions(suggestions: String): Boolean {
        return suggestions.isNullOrEmpty()
    }

    override fun checkEmptyFields(
        name: String,
        description: String,
        ingredients: ArrayList<String>,
        preparation: Map<String, String>,
        suggestions: String
    ): Boolean {
        return (
            checkEmptyAddRecipeName(name) ||
            checkEmptyAddRecipeDescription(description) ||
            checkEmptyAddRecipeIngredients(ingredients) ||
            checkEmptyAddRecipePreparation(preparation) ||
            checkEmptyAddRecipeSuggestions(suggestions)
        )
    }

    override fun addRecipe(newRecipe: Recipe) {

        Log.d(TAG, "Trying to add new recipe with name ${newRecipe.name}.")

        launch{

            if(isViewAttached()){
                view?.disableAddRecipeButton()
                view?.showAddRecipeProgressBar()
            }

            try{

                addRecipeViewModel?.addRecipe(newRecipe)
                Food4Health.currentRecipe = newRecipe

                if(isViewAttached()){
                    view?.hideAddRecipeProgressBar()
                    view?.enableAddRecipeButton()
                    view?.showMessage(R.string.MSG_ADDRECIPE_SUCCESS)
                    view?.navigateToCatalog()
                }

                Log.d(TAG, "Succesfully add recipe with name ${newRecipe.name}.")

            } catch (error: FirebaseAddRecipeException){

                if(isViewAttached()){
                    view?.hideAddRecipeProgressBar()
                    view?.enableAddRecipeButton()
                    view?.showError(R.string.ERR_REGISTER_FAILURE)
                }

                Log.d(TAG, "ERROR!: Cannot add recipe in Firebase. Error Message --> ${error.message}.")
            }
        }

    }


}
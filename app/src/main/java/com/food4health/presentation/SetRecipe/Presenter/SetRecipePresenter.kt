package com.food4health.presentation.SetRecipe.Presenter

import android.net.Uri
import android.util.Log
import com.food4health.Food4Health
import com.food4health.base.Exceptions.FirebaseSetRecipeException
import com.food4health.base.Exceptions.FirebaseStorageUploadImageException
import com.food4health.data.Model.Recipe
import com.food4health.presentation.SetRecipe.Model.SetRecipeViewModel
import com.food4health.presentation.SetRecipe.SetRecipeContract
import com.sinergia.food4health.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SetRecipePresenter(setRecipeViewModel: SetRecipeViewModel): SetRecipeContract.SetRecipePresenter, CoroutineScope {

    private val TAG = "[SETRECIPE_ACTIVITY]"
    private val setRecipeJob = Job()

    var view: SetRecipeContract.SetRecipeView? = null
    var setRecipeViewModel: SetRecipeViewModel?= null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + setRecipeJob

    init{
        this.setRecipeViewModel = setRecipeViewModel
    }


    override fun attachView(view: SetRecipeContract.SetRecipeView) {
        this.view = view
    }

    override fun dettachView() {
        this.view = null
    }

    override fun isViewAttached(): Boolean {
        return this.view != null
    }

    override fun checkEmptySetRecipeName(name: String): Boolean {
        return name.isNullOrEmpty()
    }

    override fun checkEmptySetRecipeDescription(description: String): Boolean {
        return description.isNullOrEmpty()
    }

    override fun checkEmptySetRecipeIngredients(ingredients: ArrayList<String>): Boolean {
        return ingredients.isEmpty()
    }

    override fun checkEmptySetRecipePreparation(preparation: Map<String, String>): Boolean {
        return preparation.isEmpty()
    }

    override fun checkEmptySetRecipeSuggestions(suggestions: String): Boolean {
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
            checkEmptySetRecipeName(name) ||
            checkEmptySetRecipeDescription(description) ||
            checkEmptySetRecipeIngredients(ingredients) ||
            checkEmptySetRecipePreparation(preparation) ||
            checkEmptySetRecipeSuggestions(suggestions)
        )
    }

    override fun setRecipe(settedRecipe: Recipe) {
        Log.d(TAG, "Trying to set new recipe with name ${settedRecipe.name}.")

        launch{

            if(isViewAttached()){
                view?.disableSetRecipeButton()
                view?.showSetRecipeProgressBar()
            }

            try{

                setRecipeViewModel?.setRecipe(settedRecipe)

                if(isViewAttached()){
                    view?.hideSetRecipeProgressBar()
                    view?.enableSetRecipeButton()
                    view?.showMessage(R.string.MSG_ADDRECIPE_SUCCESS)
                    view?.navigateToRecipe(settedRecipe.id)
                }

                Log.d(TAG, "Succesfully set recipe with name ${settedRecipe.name}.")

            } catch (error: FirebaseSetRecipeException){

                if(isViewAttached()){
                    view?.hideSetRecipeProgressBar()
                    view?.enableSetRecipeButton()
                    view?.showError(R.string.ERR_REGISTER_FAILURE)
                }

                Log.d(TAG, "ERROR!: Cannot set recipe in Firebase. Error Message --> ${error.message}.")
            }
        }
    }

    override fun uploadRecipeImage(imageURI: Uri) {

        Log.d(TAG, "Trying to update recipe image with name ${Food4Health.currentRecipe.name}.")

        launch{

            if(isViewAttached()){
                view?.disableSetRecipeButton()
                view?.showSetRecipeProgressBar()
            }

            try{

                val newRecipeImage = setRecipeViewModel?.uploadRecipeImage(Food4Health.currentRecipe.id, imageURI)!!
                Food4Health.currentRecipe.image = newRecipeImage.toString()
                setRecipeViewModel?.setRecipe(Food4Health.currentRecipe)

                if(isViewAttached()){
                    view?.showMessage(R.string.MSG_UPDATERECIPEIMAGE_AVATAR)
                    view?.hideSetRecipeProgressBar()
                    view?.enableSetRecipeButton()
                    view?.navigateToRecipe(Food4Health.currentRecipe.id)
                }

            } catch (error: FirebaseStorageUploadImageException){

                val errorMsg = error.message
                if(isViewAttached()){
                    view?.showError(R.string.ERR_UPDATERECIPEIMAGE_FAILURE)
                    view?.hideSetRecipeProgressBar()
                    view?.enableSetRecipeButton()
                }

                Log.d(TAG, "Cannot update recipe image to account with email ${Food4Health.currentUser.email} --> $errorMsg.")

            } catch (error: FirebaseSetRecipeException){

                val errorMsg = error.message
                if(isViewAttached()){
                    view?.showError(R.string.ERR_UPDATERECIPEIMAGE_FAILURE)
                    view?.hideSetRecipeProgressBar()
                    view?.enableSetRecipeButton()
                }

                Log.d(TAG, "Cannot update recipe image to account with email ${Food4Health.currentUser.email} --> $errorMsg.")

            }



        }

    }
}
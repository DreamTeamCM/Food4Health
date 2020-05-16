package com.food4health.presentation.Favourite.Presenter

import android.util.Log
import com.food4health.Food4Health
import com.food4health.base.Exceptions.FirebaseGetFavouriteRecipesException
import com.food4health.data.Model.Recipe
import com.food4health.presentation.Favourite.FavouriteContract
import com.food4health.presentation.Favourite.Model.FavouriteViewModel
import com.sinergia.food4health.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavouritePresenter(favouriteViewModel: FavouriteViewModel): FavouriteContract.FavouritePresenter , CoroutineScope {

    private val TAG = "[FAVOURITE_ACTIVITY]"
    private var recipesList: List<Recipe> ?= null
    private val favouriteJob = Job()

    var view: FavouriteContract.FavouriteView ?= null
    var favouriteViewModel: FavouriteViewModel ?= null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + favouriteJob

    init{
        this.favouriteViewModel = favouriteViewModel
    }


    override fun attachView(view: FavouriteContract.FavouriteView) {
        this.view = view
    }

    override fun dettachView() {
        this.view = null
    }

    override fun isViewAttached(): Boolean {
        return this.view != null
    }

    override fun getFavouriteRecipes() {
        Log.d(TAG, "Trying to get all favourite recipes from Firebase.")

        launch{

            try{

                if(isViewAttached()){
                    view?.showFavouriteProgressBar()
                }

                recipesList = favouriteViewModel?.getFavouriteRecipes(Food4Health.currentUser.email)!!

                if(isViewAttached()){
                    view?.initFavouriteContent(recipesList!!)
                    view?.showFavouriteContent()
                    view?.hideFavouriteProgressBar()
                }

                Log.d(TAG, "Successfully get all favourite recipes from Firebase.")


            } catch (error: FirebaseGetFavouriteRecipesException){

                if(isViewAttached()){
                    view?.hideFavouriteProgressBar()
                    view?.showError(R.string.ERR_GETRECIPE_FAILURE)
                }

                Log.d(TAG, "ERROR!: Cannot get all favourite recipes from Firebase. Error Message --> ${error.message}.")
            }

        }
    }


}
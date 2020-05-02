package com.food4health.presentation.Catalog.Presenter

import android.util.Log
import com.food4health.base.Exceptions.FirebaseGetAllRecipesException
import com.food4health.data.Model.Recipe
import com.food4health.presentation.Catalog.CatalogContract
import com.food4health.presentation.Catalog.Model.CatalogViewModel
import com.sinergia.food4health.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CatalogPresenter(catalogViewModel: CatalogViewModel): CatalogContract.CatalogPresenter, CoroutineScope {

    private val TAG = "[CATALOG_ACTIVITY]"
    private var recipesList: List<Recipe> ?= null
    private val catalogJob = Job()

    var view: CatalogContract.CatalogView ?= null
    var catalogViewModel: CatalogViewModel ?= null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + catalogJob

    init{
        this.catalogViewModel = catalogViewModel
    }

    override fun attachView(view: CatalogContract.CatalogView) {
        this.view = view
    }

    override fun dettachView() {
        this.view = null
    }

    override fun isViewAttached(): Boolean {
        return this.view != null
    }

    override fun getAllRecipes() {

        Log.d(TAG, "Trying to get all recipes from Firebase.")

        launch{

            try{

                if(isViewAttached()){
                    view?.showCatalogProgressBar()
                }

                recipesList = catalogViewModel?.getAllRecipes()!!
                Log.d(TAG, recipesList.toString())

                if(isViewAttached()){
                    view?.initCatalogContent(recipesList!!)
                    view?.showCatalogContent()
                    view?.hideCatalogProgressBar()
                }

                Log.d(TAG, "Successfully get all recipes from Firebase.")


            } catch (error: FirebaseGetAllRecipesException){

                if(isViewAttached()){
                    view?.hideCatalogProgressBar()
                    view?.showError(R.string.ERR_GETRECIPE_FAILURE)
                }

                Log.d(TAG, "ERROR!: Cannot get all recipes from Firebase. Error Message --> ${error.message}.")
            }

        }


    }


}
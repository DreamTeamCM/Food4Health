package com.food4health.presentation.Catalog.Presenter

import android.util.Log
import com.food4health.Food4Health
import com.food4health.base.Exceptions.FirebaseGetRecipeException
import com.food4health.data.Model.Recipe
import com.food4health.presentation.Catalog.CatalogContract
import com.food4health.presentation.Catalog.Model.ItemCatalogViewModel
import com.sinergia.food4health.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ItemCatalogPresenter(itemCatalogViewModel: ItemCatalogViewModel): CatalogContract.ItemCatalogPresenter, CoroutineScope {

    private val TAG = "[ITEMCATALOG_ACTIVITY]"
    private var recipesList: List<Recipe> ?= null
    private val itemCatalogJob = Job()

    var view: CatalogContract.ItemCatalogView ?= null
    var itemCatalogViewModel: ItemCatalogViewModel?= null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + itemCatalogJob

    init{
        this.itemCatalogViewModel = itemCatalogViewModel
    }

    override fun attachView(view: CatalogContract.ItemCatalogView) {
        this.view = view
    }

    override fun dettachView() {
        this.view = null
    }

    override fun isViewAttached(): Boolean {
        return this.view != null
    }

    override fun getItemCatalog(id: String) {

        Log.d(TAG, "Trying to get recipe from Firebase.")

        launch{

            try{

                if(isViewAttached()){
                    view?.showItemCatalogProgressBar()
                }

                var recipe = itemCatalogViewModel?.getItemCatalog(id)!!

                if(isViewAttached()){
                    view?.initInitCatalogContent(recipe)
                    if(recipe.ownerMail == Food4Health.currentUser.email) view?.showItemCatalogButtons()
                    view?.hideItemCatalogProgressBar()
                }

                Log.d(TAG, "Successfully get recipe from Firebase.")

            } catch (error: FirebaseGetRecipeException){

                if(isViewAttached()){
                    view?.hideItemCatalogProgressBar()
                    view?.showError(R.string.ERR_GETRECIPE_FAILURE)
                }

                Log.d(TAG, "ERROR!: Cannot get recipe from Firebase. Error Message --> ${error.message}.")

            }

        }

    }

    override fun deleteRecipe(recipe: Recipe) {

        Log.d(TAG, "Trying to delete recipe from Firebase.")

        launch{

            try{

                if(isViewAttached()){
                    view?.showItemCatalogProgressBar()
                    view?.disableItemCatalogButtons()
                    view?.showMessage(R.string.MSG_DELETERECIPE_SUCCESS)
                }

                itemCatalogViewModel?.deleteRecipe(recipe)

                if(isViewAttached()){
                    view?.hideItemCatalogProgressBar()
                    view?.enableItemCatalogButtons()
                    view?.showMessage(R.string.MSG_DELETERECIPE_SUCCESS)
                    view?.navigateToCatalog()
                }

                Log.d(TAG, "Successfully delete recipe from Firebase.")

            } catch (error: FirebaseGetRecipeException){

                if(isViewAttached()){
                    view?.hideItemCatalogProgressBar()
                    view?.enableItemCatalogButtons()
                    view?.showError(R.string.ERR_DELETERECIPE_FAILURE)
                }

                Log.d(TAG, "ERROR!: Cannot delete recipe from Firebase. Error Message --> ${error.message}.")

            }

        }

    }


}
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

    override fun setLikes() {

        Log.d(TAG, "Trying to set recipe likes in Firebase.")
        var newLike = false;

        launch {

            try {

                if (isViewAttached()) {
                    view?.showItemCatalogProgressBar()
                }

                var settedRecipeLikes = Food4Health.currentRecipe.likes
                if (settedRecipeLikes.contains(Food4Health.currentUser.email)) {
                    settedRecipeLikes.remove(Food4Health.currentUser.email)
                } else {
                    settedRecipeLikes.add(Food4Health.currentUser.email)
                    newLike = true
                }

                var settedRecipe = Recipe(
                    Food4Health.currentRecipe.name,
                    Food4Health.currentRecipe.description,
                    Food4Health.currentRecipe.ingredients,
                    Food4Health.currentRecipe.preparation,
                    Food4Health.currentRecipe.suggestions,
                    Food4Health.currentRecipe.ownerMail,
                    Food4Health.currentRecipe.ownerName,
                    Food4Health.currentRecipe.uploadDate,
                    Food4Health.currentRecipe.image,
                    settedRecipeLikes,
                    Food4Health.currentRecipe.id
                )

                itemCatalogViewModel?.setLikes(settedRecipe)

                if (isViewAttached()) {
                    view?.hideItemCatalogProgressBar()
                    view?.initInitCatalogContent(settedRecipe)
                    if (settedRecipe.ownerMail == Food4Health.currentUser.email) view?.showItemCatalogButtons()
                    if (newLike) {
                        view?.showMessage(R.string.MSG_SET_LIKES_LIKE)
                        view?.setLikeImage(1)
                    } else {
                        view?.showMessage(R.string.MSG_SET_LIKES_DISLIKE)
                        view?.setLikeImage(0)
                    }

                }

                Log.d(TAG, "Successfully set recipe likes in Firebase.")

            } catch (error: FirebaseGetRecipeException) {

                if (isViewAttached()) {
                    view?.hideItemCatalogProgressBar()
                    view?.showError(R.string.ERR_SET_LIKES)
                }

                Log.d(
                    TAG,
                    "ERROR!: Cannot set recipe likes in Firebase. Error Message --> ${error.message}."
                )

            }

        }

    }

    override fun getItemCatalog(id: String) {

        Log.d(TAG, "Trying to get recipe from Firebase.")

        launch {

            try {

                if (isViewAttached()) {
                    view?.showItemCatalogProgressBar()
                }

                var recipe = itemCatalogViewModel?.getItemCatalog(id)!!

                if (isViewAttached()) {
                    view?.initInitCatalogContent(recipe)
                    if (recipe.ownerMail == Food4Health.currentUser.email) view?.showItemCatalogButtons()
                    view?.hideItemCatalogProgressBar()
                }

                Log.d(TAG, "Successfully get recipe from Firebase.")

            } catch (error: FirebaseGetRecipeException) {

                if (isViewAttached()) {
                    view?.hideItemCatalogProgressBar()
                    view?.showError(R.string.ERR_GETRECIPE_FAILURE)
                }

                Log.d(
                    TAG,
                    "ERROR!: Cannot get recipe from Firebase. Error Message --> ${error.message}."
                )

            }

        }

    }

    override fun deleteRecipe(recipe: Recipe) {

        Log.d(TAG, "Trying to delete recipe from Firebase.")

        launch {

            try {

                if (isViewAttached()) {
                    view?.showItemCatalogProgressBar()
                    view?.disableItemCatalogButtons()
                    view?.showMessage(R.string.MSG_DELETERECIPE_SUCCESS)
                }

                itemCatalogViewModel?.deleteRecipe(recipe)

                if (isViewAttached()) {
                    view?.hideItemCatalogProgressBar()
                    view?.enableItemCatalogButtons()
                    view?.showMessage(R.string.MSG_DELETERECIPE_SUCCESS)
                    view?.navigateToCatalog()
                }

                Log.d(TAG, "Successfully delete recipe from Firebase.")

            } catch (error: FirebaseGetRecipeException) {

                if (isViewAttached()) {
                    view?.hideItemCatalogProgressBar()
                    view?.enableItemCatalogButtons()
                    view?.showError(R.string.ERR_DELETERECIPE_FAILURE)
                }

                Log.d(
                    TAG,
                    "ERROR!: Cannot delete recipe from Firebase. Error Message --> ${error.message}."
                )

            }

        }

    }

}
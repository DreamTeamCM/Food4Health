package com.food4health.presentation.Catalog

import com.food4health.data.Model.Recipe

interface CatalogContract {

    interface CatalogView{

        fun showError(error: Int)
        fun showMessage(message: Int)
        fun showCatalogProgressBar()
        fun hideCatalogProgressBar()
        fun showCatalogContent()
        fun hideCatalogContent()
        fun enableSearchButton()
        fun disableSearchButton()
        fun attachAllRecipes(allRecipes: ArrayList<Recipe>)

        fun search()

        fun getAllRecipes()
        fun initCatalogContent(recipes: List<Recipe>)

    }

    interface ItemCatalogView{

        fun showError(error: Int)
        fun showMessage(message: Int)
        fun showItemCatalogProgressBar()
        fun hideItemCatalogProgressBar()
        fun showItemCatalogContent()
        fun hideItemCatalogContent()
        fun showItemCatalogButtons()
        fun hideItemCatalogContentButtons()
        fun enableItemCatalogButtons()
        fun disableItemCatalogButtons()

        fun setLikes()
        fun setLikeImage(type: Int) // 0 -> Dislike; 1-> Like

        fun getRecipe()
        fun deleteRecipe()
        fun initInitCatalogContent(recipe: Recipe)

        fun navigateToCatalog()

    }

    interface CatalogPresenter{

        fun attachView(view: CatalogContract.CatalogView)
        fun dettachView()
        fun isViewAttached(): Boolean

        fun search(recipes: ArrayList<Recipe>, searcher: String)

        fun getAllRecipes()

    }

    interface ItemCatalogPresenter {

        fun attachView(view: CatalogContract.ItemCatalogView)
        fun dettachView()
        fun isViewAttached(): Boolean

        fun setLikes()

        fun getItemCatalog(id: String)
        fun deleteRecipe(recipe: Recipe)

    }

}
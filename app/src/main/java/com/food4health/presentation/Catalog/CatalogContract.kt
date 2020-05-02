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

        fun getRecipe()
        fun initInitCatalogContent(recipe: Recipe)

    }

    interface CatalogPresenter{

        fun attachView(view: CatalogContract.CatalogView)
        fun dettachView()
        fun isViewAttached(): Boolean

        fun getAllRecipes()

    }

    interface ItemCatalogPresenter {

        fun attachView(view: CatalogContract.ItemCatalogView)
        fun dettachView()
        fun isViewAttached(): Boolean

        fun getItemCatalog(id: String)

    }

}
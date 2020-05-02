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

    interface CatalogPresenter{

        fun attachView(view: CatalogContract.CatalogView)
        fun dettachView()
        fun isViewAttached(): Boolean

        fun getAllRecipes()

    }

}
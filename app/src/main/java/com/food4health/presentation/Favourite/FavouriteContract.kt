package com.food4health.presentation.Favourite

import com.food4health.data.Model.Recipe

interface FavouriteContract {
    interface FavouriteView{

        fun showError(error: Int)
        fun showMessage(message: Int)
        fun showFavouriteProgressBar()
        fun hideFavouriteProgressBar()
        fun showFavouriteContent()
        fun hideFavouriteContent()

        fun getFavouriteRecipes()
        fun initFavouriteContent(recipes: List<Recipe>)

    }

    interface FavouritePresenter{

        fun attachView(view: FavouriteContract.FavouriteView)
        fun dettachView()
        fun isViewAttached(): Boolean

        fun getFavouriteRecipes()

    }
}
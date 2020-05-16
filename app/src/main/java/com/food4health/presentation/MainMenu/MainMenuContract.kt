package com.food4health.presentation.MainMenu

interface MainMenuContract {

    interface MainMenuView{

        fun navigateToCatalog()

        fun navigateToAddRecipe()

        fun navigateToAccount()

        fun navigateToFavourites()

    }

}
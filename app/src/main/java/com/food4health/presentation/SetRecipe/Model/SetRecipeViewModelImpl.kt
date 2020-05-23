package com.food4health.presentation.SetRecipe.Model

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.food4health.data.Model.Recipe
import com.food4health.domain.UseCases.FileUseCases
import com.food4health.domain.UseCases.RecipeUseCases


class SetRecipeViewModelImpl: ViewModel(), SetRecipeViewModel {

    val recipeUseCases = RecipeUseCases()
    val fileUseCases = FileUseCases()

    override suspend fun setRecipe(settedRecipe: Recipe) {
        return recipeUseCases.setRecipe(settedRecipe)
    }

    override suspend fun uploadRecipeImage(recipeId: String, imageURI: Uri): Uri {
        return fileUseCases.uploadRecipeImage(recipeId, imageURI)
    }

}
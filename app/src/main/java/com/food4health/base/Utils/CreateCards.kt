package com.food4health.base.Utils

import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.food4health.data.Model.Recipe
import com.sinergia.food4health.R

class CreateCards {

    fun createRecipeCard(context: Context, recipe: Recipe): CardView {

        // RESOURCE CARD
        var recipeCard = CardView(context)
        recipeCard.radius = 50f
        var cardParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            width = LinearLayout.LayoutParams.MATCH_PARENT
            height = LinearLayout.LayoutParams.WRAP_CONTENT
            bottomMargin = 30
        }
        recipeCard.layoutParams = cardParams
        recipeCard.setBackgroundColor(getColor(context, R.color.colorWhite))

        // CARD CONTENT
        var cardContent = LinearLayout(context)
        var contentParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            width = LinearLayout.LayoutParams.MATCH_PARENT
            height = LinearLayout.LayoutParams.WRAP_CONTENT
        }
        cardContent.layoutParams = contentParams
        cardContent.orientation = LinearLayout.VERTICAL

        // CARD IMAGE
        var resourceImage = ImageView(context)
        var imageParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ).apply {
            width = LinearLayout.LayoutParams.MATCH_PARENT
            height = 200
            bottomMargin = 10
            topMargin = 10
            rightMargin = 10
            leftMargin = 10
        }
        resourceImage.layoutParams = imageParams
        resourceImage.setImageResource(R.drawable.f4h_logo)

        // CARD SEPARATOR
        var separator = LinearLayout(context)
        var separatorParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            width = LinearLayout.LayoutParams.MATCH_PARENT
            height = 5
            topMargin = 10
            bottomMargin = 10
        }
        separator.layoutParams = separatorParams
        separator.setBackgroundColor(getColor(context, R.color.colorLightBlue))

        // CARD DESCRIPTION
        var recipeDescription = LinearLayout(context)
        var descriptionParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ).apply {
            width = LinearLayout.LayoutParams.MATCH_PARENT
            height = LinearLayout.LayoutParams.WRAP_CONTENT

        }
        descriptionParams.setMargins(15,0,15,0)
        recipeDescription.layoutParams = descriptionParams
        recipeDescription.orientation = LinearLayout.VERTICAL

        val recipeName = TextView(context)
        val recipeNameTxt = recipe.name
        recipeName.setText("Nombre: $recipeNameTxt.")
        recipeName.setTextColor(ContextCompat.getColor(context, R.color.colorBlue))
        recipeDescription.addView(recipeName)

        val recipeOwner = TextView(context)
        val recipeOwnerTxt = recipe.owner
        recipeOwner.setText("Nombre: $recipeOwnerTxt.")
        recipeOwner.setTextColor(ContextCompat.getColor(context, R.color.colorBlue))
        recipeDescription.addView(recipeOwner)

        val recipeDescript = TextView(context)
        val recipeDescriptionTxt = recipe.description
        recipeDescript.setText("Nombre: $recipeDescriptionTxt.")
        recipeDescript.setTextColor(ContextCompat.getColor(context, R.color.colorBlue))
        recipeDescription.addView(recipeDescript)

        return recipeCard

    }

}
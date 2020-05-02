package com.food4health.data.Model

data class Recipe (

    var name: String = "Desconocido",
    var description: String = "Desconocido",
    var ingredients: ArrayList<String> = arrayListOf(),
    var preparation: Map<String, String> = emptyMap(),
    var suggestions: String = "Desconocido",
    var owner: String = "Desconocido",
    var uploadDate: String = "Desconocido",
    var likes: ArrayList<String> = arrayListOf(),
    var id : String = ""

)
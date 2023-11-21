package com.example.continueintegrationproject.model

import com.google.firebase.database.Exclude

data class Boards(
    @get:Exclude
    var id: String? = null,
    var name: String = "",
    var total: Float = 0.0F,
    var current: Float = 0.0F,
    @get:Exclude
    var isDeleted: Boolean = false
)
package com.example.dermacare.data.model

import kotlinx.serialization.descriptors.PrimitiveKind

class Post (
    val id : Int,
    val userId:Int,
    val title : String,
    val body: String

)


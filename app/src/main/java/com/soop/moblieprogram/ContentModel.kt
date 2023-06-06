package com.soop.moblieprogram

data class ContentModel(
    val title: String = "",
    val content: String = "",
    val time: String = "",
    val category: MutableList<String> = mutableListOf<String>(),
    val chatUrl: String = "",
    val location: String = ""
)

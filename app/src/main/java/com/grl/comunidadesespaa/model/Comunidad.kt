package com.grl.comunidadesespaa.model

data class Comunidad(
    var id: Int,
    var name: String,
    var image: Int,
    var habitants: Long,
    var capital: String,
    var x: Double,
    var y: Double,
    var capImage: Int,
    var uri: String
)
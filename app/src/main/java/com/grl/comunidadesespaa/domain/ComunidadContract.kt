package com.grl.comunidadesespaa.domain

import android.provider.BaseColumns

class ComunidadContract {
    companion object{
        const val NOMBRE_BD = "comunidades"
        const val VERSION = 1
        class Entrada: BaseColumns {
            companion object{
                const val NOMBRE_TABLA = "comunidades"
                const val COLUMNA_ID = "id"
                const val COLUMNA_NOMBRE = "nombre"
                const val COLUMNA_IMAGEN = "imagen"
                const val COLUMNA_HABITANTES = "habitantes"
                const val COLUMNA_CAPITAL = "capital"
                const val COLUMNA_COORDENADA_X = "coordenada_x"
                const val COLUMNA_COORDENADA_Y = "coordenada_y"
                const val COLUMNA_IMAGEN_CAPITAL = "imagen_capital"
                const val COLUMNA_URI = "uri"
            }
        }
    }
}
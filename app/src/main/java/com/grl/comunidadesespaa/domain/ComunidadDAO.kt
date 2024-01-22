package com.grl.comunidadesespaa.domain

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.grl.comunidadesespaa.model.Comunidad

class ComunidadDAO {
    fun cargarLista(context: Context?): MutableList<Comunidad> {

        lateinit var resultado: MutableList<Comunidad>
        lateinit var cursor: Cursor
        try {

            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            val columnas = arrayOf(
                ComunidadContract.Companion.Entrada.COLUMNA_ID,
                ComunidadContract.Companion.Entrada.COLUMNA_NOMBRE,
                ComunidadContract.Companion.Entrada.COLUMNA_IMAGEN,
                ComunidadContract.Companion.Entrada.COLUMNA_HABITANTES,
                ComunidadContract.Companion.Entrada.COLUMNA_CAPITAL,
                ComunidadContract.Companion.Entrada.COLUMNA_COORDENADA_X,
                ComunidadContract.Companion.Entrada.COLUMNA_COORDENADA_Y,
                ComunidadContract.Companion.Entrada.COLUMNA_IMAGEN_CAPITAL
            )
            cursor = db.query(
                ComunidadContract.Companion.Entrada.NOMBRE_TABLA,
                columnas, null, null, null, null, null
            )
            resultado = mutableListOf()
            while (cursor.moveToNext()) {
                val nueva = Comunidad(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getLong(3),
                    cursor.getString(4),
                    cursor.getDouble(5),
                    cursor.getDouble(6),
                    cursor.getInt(7)
                    )
                resultado.add(nueva)
            }
        } finally {
            cursor.close()
        }
        return resultado
    }

    fun actualizarBBDD(context: Context?, comunidad: Comunidad) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        val values = ContentValues()
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_ID,comunidad.id)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_NOMBRE,comunidad.name)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_IMAGEN,comunidad.image)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_HABITANTES,comunidad.habitants)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_CAPITAL,comunidad.capital)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_COORDENADA_X,comunidad.x)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_COORDENADA_Y,comunidad.y)
        values.put(ComunidadContract.Companion.Entrada.COLUMNA_IMAGEN_CAPITAL,comunidad.capImage)
        db.update(ComunidadContract.Companion.Entrada.NOMBRE_TABLA,values,"id=?",arrayOf(comunidad.id.toString()))
        db.close()
    }

    fun eliminarComunidad(context: Context?, comunidad: Comunidad) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.delete(ComunidadContract.Companion.Entrada.NOMBRE_TABLA,"id=?", arrayOf(comunidad.id.toString()))
        db.close()
    }
}
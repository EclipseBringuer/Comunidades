package com.grl.comunidadesespaa.domain

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.grl.comunidadesespaa.Comunidad
import com.grl.comunidadesespaa.R
import java.lang.Exception

class DBOpenHelper private constructor(context: Context?) :
    SQLiteOpenHelper(context, ComunidadContract.NOMBRE_BD, null, ComunidadContract.VERSION) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(
                "CREATE TABLE ${ComunidadContract.Companion.Entrada.NOMBRE_TABLA}"
                        + "(${ComunidadContract.Companion.Entrada.COLUMNA_ID} int NOT NULL"
                        + ",${ComunidadContract.Companion.Entrada.COLUMNA_NOMBRE} NVARCHAR(20) NOT NULL"
                        + ",${ComunidadContract.Companion.Entrada.COLUMNA_IMAGEN} int NOT NULL);"
            )
            inicializarBBDD(sqLiteDatabase)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ${ComunidadContract.Companion.Entrada.NOMBRE_TABLA};")
        onCreate(sqLiteDatabase)
    }

    private fun inicializarBBDD(db: SQLiteDatabase) {
        val lista = cargarComunidades()
        for (comunidad in lista) {
            db.execSQL(
                ("INSERT INTO ${ComunidadContract.Companion.Entrada.NOMBRE_TABLA}(" +
                        "${ComunidadContract.Companion.Entrada.COLUMNA_ID}," +
                        "${ComunidadContract.Companion.Entrada.COLUMNA_NOMBRE}," +
                        "${ComunidadContract.Companion.Entrada.COLUMNA_IMAGEN})" +
                        " VALUES (${comunidad.id},'${comunidad.name}',${comunidad.image});")
            )
        }
    }

    private fun cargarComunidades(): MutableList<Comunidad> {
        return mutableListOf(
            Comunidad(1, "Andalucía", R.drawable.andalucia),
            Comunidad(2, "Aragón", R.drawable.aragon),
            Comunidad(3, "Asturias", R.drawable.asturias),
            Comunidad(4, "Baleares", R.drawable.baleares),
            Comunidad(5, "Canarias", R.drawable.canarias),
            Comunidad(6, "Cantabria", R.drawable.cantabria),
            Comunidad(7, "Castilla y León", R.drawable.castillaleon),
            Comunidad(8, "Castilla-La Mancha", R.drawable.castillamancha),
            Comunidad(9, "Cataluña", R.drawable.catalunya),
            Comunidad(10, "Ceuta", R.drawable.ceuta),
            Comunidad(11, "Extremadura", R.drawable.extremadura),
            Comunidad(12, "Galicia", R.drawable.galicia),
            Comunidad(13, "La Rioja", R.drawable.larioja),
            Comunidad(14, "Madrid", R.drawable.madrid),
            Comunidad(15, "Melilla", R.drawable.melilla),
            Comunidad(16, "Murcia", R.drawable.murcia),
            Comunidad(17, "Navarra", R.drawable.navarra),
            Comunidad(18, "País Vasco", R.drawable.paisvasco),
            Comunidad(19, "Valencia", R.drawable.valencia)
        )
    }

    companion object {
        private var dbOpen: DBOpenHelper? = null
        fun getInstance(context: Context?): DBOpenHelper? {
            if (dbOpen == null) dbOpen = DBOpenHelper(context)
            return dbOpen
        }
    }
}
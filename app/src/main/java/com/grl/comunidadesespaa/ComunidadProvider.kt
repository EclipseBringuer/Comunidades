package com.grl.comunidadesespaa

class ComunidadProvider {
    companion object {
        var comunidadList: MutableList<Comunidad> = mutableListOf()

        fun llenar(): MutableList<Comunidad> {
            comunidadList = mutableListOf(
                Comunidad("Andalucía", R.drawable.andalucia),
                Comunidad("Aragón", R.drawable.aragon),
                Comunidad("Asturias", R.drawable.asturias),
                Comunidad("Baleares", R.drawable.baleares),
                Comunidad("Canarias", R.drawable.canarias),
                Comunidad("Cantabria", R.drawable.cantabria),
                Comunidad("Castilla y León", R.drawable.castillaleon),
                Comunidad("Castilla-La Mancha", R.drawable.castillamancha),
                Comunidad("Cataluña", R.drawable.catalunya),
                Comunidad("Ceuta", R.drawable.ceuta),
                Comunidad("Extremadura", R.drawable.extremadura),
                Comunidad("Galicia", R.drawable.galicia),
                Comunidad("La Rioja", R.drawable.larioja),
                Comunidad("Madrid", R.drawable.madrid),
                Comunidad("Melilla", R.drawable.melilla),
                Comunidad("Murcia", R.drawable.murcia),
                Comunidad("Navarra", R.drawable.navarra),
                Comunidad("País Vasco", R.drawable.paisvasco),
                Comunidad("Valencia", R.drawable.valencia)
            )
            return comunidadList
        }
    }
}

package com.grl.comunidadesespaa.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grl.comunidadesespaa.model.Comunidad
import com.grl.comunidadesespaa.databinding.ActivityOpenStreetBinding
import com.grl.comunidadesespaa.domain.ComunidadDAO
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem

class OpenStreetActivity : AppCompatActivity() {
    private lateinit var map: MapView
    private lateinit var binding: ActivityOpenStreetBinding
    private lateinit var lista: List<Comunidad>
    private var position: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance()
            .load(applicationContext, this.getPreferences(Context.MODE_PRIVATE))
        binding = ActivityOpenStreetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configuración básica del mapa
        map = binding.streetMap
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        val mapController = map.controller
        mapController.setZoom(9.5)

        //Creamos los snippets de las comunidades
        position = this.intent.getIntExtra("posicion", 0)
        lista = ComunidadDAO().cargarLista(this)
        val items: ArrayList<OverlayItem> = ArrayList<OverlayItem>()
        for (comunidad: Comunidad in lista) {
            items.add(
                OverlayItem(
                    "${comunidad.name} - Capital: ${comunidad.capital}",
                    "Habitantes: ${comunidad.habitants}",
                    GeoPoint(comunidad.x, comunidad.y)
                )
            )
        }

        //Ubicacion y metodos de los snippets
        val mOverlay: ItemizedOverlayWithFocus<OverlayItem> =
            ItemizedOverlayWithFocus<OverlayItem>(
                items,
                object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem?> {
                    override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                        return true
                    }

                    override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                        return true
                    }
                }, applicationContext
            )
        mOverlay.setFocusItemsOnTap(true)
        map.getOverlays().add(mOverlay)

        if (position >= 0 && position < items.size) {
            val comunidadSelected = lista[position]
            val mapController = map.controller
            mapController.setCenter(GeoPoint(comunidadSelected.x, comunidadSelected.y))
            mapController.setZoom(11.0)
        } else {
            mapController.setCenter(GeoPoint(40.429642598652, -3.76167856716930))
        }
    }

    public override fun onResume() {
        super.onResume()
        map.onResume()
    }

    public override fun onPause() {
        super.onPause()
        map.onPause()
    }
}
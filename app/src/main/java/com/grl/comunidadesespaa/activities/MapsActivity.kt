package com.grl.comunidadesespaa.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.grl.comunidadesespaa.model.Comunidad
import com.grl.comunidadesespaa.R
import com.grl.comunidadesespaa.databinding.ActivityMapsBinding
import com.grl.comunidadesespaa.domain.ComunidadDAO

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var posicion: Int = 0
    private lateinit var lista: List<Comunidad>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        posicion = this.intent.getIntExtra("posicion", 0)
        lista = ComunidadDAO().cargarLista(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        mMap.moveCamera(CameraUpdateFactory.zoomTo(1f))
        mMap.uiSettings.isZoomControlsEnabled = false

        val camUpdate = CameraUpdateFactory.newLatLng(
            LatLng(38.55439899253784, -14.340675914640318)
        )
        mMap.animateCamera(camUpdate)

        for (comunidad: Comunidad in lista) {
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(comunidad.x, comunidad.y))
                    .icon(BitmapDescriptorFactory.fromResource(comunidad.capImage))
                    .title("${comunidad.name} - Capital: ${comunidad.capital}")
                    .snippet("Habitantes: ${comunidad.habitants}")
            )
        }
        posicionar(posicion)
    }

    private fun posicionar(posicion: Int) {
        val newX: Double = lista[posicion].x
        val newY: Double = lista[posicion].y
        val target = LatLng(newX, newY)
        val pos = CameraPosition.builder()
            .target(target)
            .zoom(8f)
            .bearing(0f)
            .tilt(0f)
            .build()
        val camUpdate = CameraUpdateFactory.newCameraPosition(pos)
        mMap.animateCamera(camUpdate)
    }
}
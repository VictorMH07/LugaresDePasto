
package com.example.lugaresdepasto.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.maplibre.android.annotations.MarkerOptions
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import android.view.MotionEvent

private const val MAP_STYLE_URL =
    "https://tiles.openfreemap.org/styles/liberty"

@Composable
fun MapaPasto(
    modifier: Modifier = Modifier,
    onLugarSeleccionado: (String, String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val mapView = remember {
        MapLibre.getInstance(context)

        object : MapView(context) {

            override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

                when (ev.action) {
                    MotionEvent.ACTION_DOWN -> {
                        parent?.requestDisallowInterceptTouchEvent(true)
                    }

                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> {
                        parent?.requestDisallowInterceptTouchEvent(false)
                    }
                }

                return super.dispatchTouchEvent(ev)
            }

        }.apply {
            onCreate(null)
        }
    }

    DisposableEffect(mapView, lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        if (lifecycleOwner.lifecycle.currentState
                .isAtLeast(Lifecycle.State.STARTED)
        ) {
            mapView.onStart()
        }

        if (lifecycleOwner.lifecycle.currentState
                .isAtLeast(Lifecycle.State.RESUMED)
        ) {
            mapView.onResume()
        }

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onPause()
            mapView.onStop()
            mapView.onDestroy()
        }
    }

    AndroidView(
        factory = {
            mapView.apply {
                getMapAsync { map ->
                    map.setStyle(MAP_STYLE_URL)

                    map.cameraPosition = CameraPosition.Builder()
                        .target(LatLng(1.1550, -77.2160))
                        .zoom(9.0)
                        .build()

                    // Plaza de Nariño
                    map.addMarker(
                        MarkerOptions()
                            .position(LatLng(1.2136, -77.2811))
                            .title("Plaza de Nariño")
                            .snippet("Centro histórico de Pasto")
                    )

                    // Laguna de La Cocha
                    map.addMarker(
                        MarkerOptions()
                            .position(LatLng(1.0966, -77.1517))
                            .title("Laguna de La Cocha")
                            .snippet("Atractivo turístico de El Encano")
                    )

                    // Templo de San Juan Bautista
                    map.addMarker(
                        MarkerOptions()
                            .position(LatLng(1.2150331, -77.2788971))
                            .title("Templo de San Juan Bautista")
                            .snippet("Patrimonio histórico y religioso de Pasto")
                    )

                    // Museo del Carnaval
                    map.addMarker(
                        MarkerOptions()
                            .position(LatLng(1.23017, -77.28681))
                            .title("Museo del Carnaval")
                            .snippet("Centro Cultural Pandiaco - Carnaval de Negros y Blancos")
                    )

                    // Detectar cuando se selecciona un sitio turístico
                    map.setOnMarkerClickListener { marker ->
                        onLugarSeleccionado(
                            marker.title ?: "",
                            marker.snippet ?: ""
                        )
                        false
                    }
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
    )
}
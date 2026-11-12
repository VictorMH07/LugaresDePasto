
package com.example.lugaresdepasto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lugaresdepasto.ui.theme.LugaresDePastoTheme
import com.example.lugaresdepasto.ui.components.MapaPasto
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.lugaresdepasto.data.Lugar
import com.example.lugaresdepasto.data.LugarDatabase
import com.example.lugaresdepasto.data.LugarRepository
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.LaunchedEffect
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import org.maplibre.android.geometry.LatLng
import androidx.compose.runtime.saveable.rememberSaveable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            LugaresDePastoTheme {
                PantallaPrincipal()
            }
        }
    }
}

@Composable
fun PantallaPrincipal() {

    val lugarSeleccionado = remember {
        mutableStateOf<Pair<String, String>?>(null)
    }

    val context = LocalContext.current

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val latitudUsuario = rememberSaveable {
        mutableStateOf<Double?>(null)
    }

    val longitudUsuario = rememberSaveable {
        mutableStateOf<Double?>(null)
    }

    val permisoUbicacion = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val solicitarPermisoUbicacion =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permisos ->
            permisoUbicacion.value =
                permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                        permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        }

    val repository = remember {
        LugarRepository(
            LugarDatabase.obtenerBaseDatos(context).lugarDao()
        )
    }

    val favoritos = repository.favoritos.collectAsState(
        initial = emptyList()
    ).value

    val coroutineScope = rememberCoroutineScope()

    val bringIntoViewRequester = remember {
        BringIntoViewRequester()
    }

    LaunchedEffect(lugarSeleccionado.value) {
        if (lugarSeleccionado.value != null) {
            bringIntoViewRequester.bringIntoView()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

            // Encabezado
            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 28.dp
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Lugares de Pasto",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            Text(
                                text = "Descubre nuestra ciudad",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "📍",
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "¡Bienvenido!",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Text(
                        text = "Explora, conoce y guarda tus lugares favoritos de Pasto.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                // Sección del mapa
                Text(
                    text = "Explora la ciudad",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Mapa de Pasto",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Button(
                            onClick = {
                                val tienePermiso =
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) == PackageManager.PERMISSION_GRANTED ||
                                            ContextCompat.checkSelfPermission(
                                                context,
                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                            ) == PackageManager.PERMISSION_GRANTED

                                if (!tienePermiso) {
                                    solicitarPermisoUbicacion.launch(
                                        arrayOf(
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                        )
                                    )
                                } else {
                                    val cancellationToken = CancellationTokenSource()

                                    fusedLocationClient.getCurrentLocation(
                                        Priority.PRIORITY_HIGH_ACCURACY,
                                        cancellationToken.token
                                    ).addOnSuccessListener { location ->
                                        if (location != null) {
                                            latitudUsuario.value = location.latitude
                                            longitudUsuario.value = location.longitude
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("📍 Activar mi ubicación")
                        }

                        if (
                            latitudUsuario.value != null &&
                            longitudUsuario.value != null
                        ) {
                            Text(
                                text = "Mi ubicación:\n" +
                                        "Latitud: ${latitudUsuario.value}\n" +
                                        "Longitud: ${longitudUsuario.value}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        MapaPasto(
                            ubicacionUsuario = latitudUsuario.value?.let { latitud ->
                                longitudUsuario.value?.let { longitud ->
                                    LatLng(latitud, longitud)
                                }
                            },
                            onLugarSeleccionado = { nombre, descripcion ->
                                lugarSeleccionado.value = nombre to descripcion
                            }
                        )

                        // Ficha informativa del sitio selleccionado
                        lugarSeleccionado.value?.let { lugar ->

                            val descripcionLugar = when (lugar.first) {
                                "Plaza de Nariño" ->
                                    "Es el corazón del centro histórico de Pasto. " +
                                            "Un espacio emblemático rodeado de edificios históricos, " +
                                            "comercios y lugares representativos de la ciudad."

                                "Laguna de La Cocha" ->
                                    "También conocida como lago Guamuez, es uno de los " +
                                            "principales atractivos naturales de Nariño. " +
                                            "Se destaca por sus paisajes, aguas tranquilas y gastronomía."

                                "Templo de San Juan Bautista" ->
                                    "Es uno de los templos históricos más representativos " +
                                            "de Pasto. Destaca por su arquitectura religiosa y su " +
                                            "importancia en el patrimonio cultural de la ciudad."

                                "Museo del Carnaval" ->
                                    "Es un espacio cultural dedicado al Carnaval de Negros " +
                                            "y Blancos. Allí se pueden conocer las tradiciones, " +
                                            "carrozas, figuras y expresiones artísticas de esta celebración."

                                else -> lugar.second
                            }

                            val ubicacionLugar = when (lugar.first) {
                                "Plaza de Nariño" -> "Centro histórico de Pasto."
                                "Laguna de La Cocha" -> "Corregimiento de El Encano, Pasto."
                                "Templo de San Juan Bautista" -> "Centro histórico de Pasto."
                                "Museo del Carnaval" -> "Centro Cultural Pandiaco, Pasto."
                                else -> "Pasto, Nariño."
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .bringIntoViewRequester(bringIntoViewRequester),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Image(
                                        painter = painterResource(
                                            id = when (lugar.first) {
                                                "Plaza de Nariño" -> R.drawable.plaza_narino
                                                "Laguna de La Cocha" -> R.drawable.laguna_cocha
                                                "Templo de San Juan Bautista" -> R.drawable.templo_san_juan
                                                "Museo del Carnaval" -> R.drawable.museo_carnaval
                                                else -> R.drawable.plaza_narino
                                            }
                                        ),
                                        contentDescription = "Fotografía de ${lugar.first}",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),
                                        contentScale = ContentScale.Crop
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "📍 ${lugar.first}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = descripcionLugar,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Text(
                                        text = "📍 Ubicación: $ubicacionLugar",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                val favoritoExistente = favoritos.find {
                                                    it.nombre == lugar.first
                                                }

                                                if (favoritoExistente != null) {
                                                    repository.eliminarLugar(favoritoExistente)
                                                } else {
                                                    repository.guardarLugar(
                                                        Lugar(
                                                            nombre = lugar.first,
                                                            descripcion = descripcionLugar,
                                                            ubicacion = ubicacionLugar
                                                        )
                                                    )
                                                }
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = if (favoritos.any { it.nombre == lugar.first }) {
                                                "★ Quitar de favoritos"
                                            } else {
                                                "☆ Guardar en favoritos"
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))



                    }
                }

                Spacer(modifier = Modifier.height(24.dp))


                // Sección de lugares guardados
                Text(
                    text = "Mis lugares guardados",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        if (favoritos.isEmpty()) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "⭐",
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Todavía no tienes lugares guardados",
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Agrega tus sitios favoritos y aparecerán aquí.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        } else {
                            favoritos.forEach { favorito ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            lugarSeleccionado.value =
                                                favorito.nombre to favorito.descripcion
                                        }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "⭐",
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = favorito.nombre,
                                            style = MaterialTheme.typography.bodyLarge
                                        )

                                        Text(
                                            text = favorito.ubicacion,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }

                                HorizontalDivider()
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))

                // Información de la aplicación
                Text(
                    text = "Descubre Pasto a tu manera",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Explora los sitios de interés de nuestra ciudad y crea tu propia colección de lugares especiales.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {
    LugaresDePastoTheme {
        PantallaPrincipal()
    }
}

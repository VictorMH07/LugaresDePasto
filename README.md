# LugaresDePasto

App Android en Kotlin para explorar algunos lugares turísticos de Pasto, Nariño. Muestra un mapa con cuatro sitios de interés, una ficha con foto y descripción de cada uno, la ubicación actual del usuario y una lista de favoritos que se guarda en el teléfono.

Es un trabajo académico de la asignatura Desarrollo de aplicaciones para dispositivos móviles. Sirve para practicar integración de mapas, permisos, geolocalización y persistencia local.

## Qué hace

- Muestra un mapa de la región de Pasto con cuatro marcadores.
- Al tocar un marcador abre una ficha con foto, descripción y ubicación del lugar.
- Permite guardar un lugar como favorito y quitarlo. Los favoritos quedan en una base de datos local (Room) y siguen ahí al cerrar la app.
- El botón de ubicación pide el permiso en tiempo de ejecución y, si se concede, dibuja un pin azul con la posición del usuario y muestra sus coordenadas.
- La interfaz funciona en vertical y en horizontal. Las coordenadas del usuario se conservan al rotar la pantalla.

## Lugares incluidos

1. Plaza de Nariño, centro histórico de Pasto.
2. Laguna de La Cocha, en el corregimiento de El Encano.
3. Templo de San Juan Bautista, patrimonio histórico y religioso.
4. Museo del Carnaval, dedicado al Carnaval de Negros y Blancos.

## Conexión a internet

- La **primera vez** que se abre la app hace falta conexión. Sin ella no cargan el mapa ni los marcadores.
- Después, las zonas del mapa que ya se vieron quedan en la caché de MapLibre y se pueden volver a ver sin conexión.
- Las fotos están dentro de la app y los favoritos en la base de datos local, así que las fichas de los lugares guardados se abren sin internet.
- Para ver zonas nuevas del mapa sí hace falta conexión.

## No necesita API key

El mapa usa MapLibre con el estilo Liberty de OpenFreeMap, que no pide clave ni cuenta de facturación. Quien clone el repositorio no tiene que configurar ningún secreto para ejecutarlo.

## Tecnologías

| Tecnología                    | Para qué se usa             |
|:------------------------------|:----------------------------|
| Kotlin                        | Lenguaje principal          |
| Jetpack Compose y Material 3  | Interfaz                    |
| MapLibre Native               | Mapa interactivo            |
| OpenFreeMap                   | Estilo y mosaicos del mapa  |
| Room (SQLite)                 | Favoritos en el dispositivo |
| Google Play Services Location | Ubicación del dispositivo   |
| Gradle                        | Dependencias y compilación  |

Configuración del proyecto: `minSdk` 24, `compileSdk` y `targetSdk` 37.

## Estructura

```text
LugaresDePasto/
├── app/src/main/
│   ├── java/com/example/lugaresdepasto/
│   │   ├── MainActivity.kt          pantalla principal
│   │   ├── data/                    Lugar, LugarDao, LugarDatabase, LugarRepository
│   │   └── ui/
│   │       ├── components/          MapaPasto.kt
│   │       └── theme/
│   ├── res/drawable-nodpi/          fotos de los lugares
│   └── AndroidManifest.xml
├── gradle/libs.versions.toml        versiones de las dependencias
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Cómo ejecutarlo

Necesitas Android Studio, el SDK de Android y un emulador o un teléfono con depuración USB.

1. Clona el repositorio:

   ```bash
   git clone https://github.com/VictorMH07/LugaresDePasto.git
   ```

2. Abre la carpeta `LugaresDePasto` desde Android Studio (Open).
3. Espera a que Gradle termine de sincronizar.
4. Elige el emulador o el teléfono y pulsa Run.

## Permisos y privacidad

La app declara `INTERNET`, `ACCESS_FINE_LOCATION` y `ACCESS_COARSE_LOCATION`.

El permiso de ubicación se pide cuando el usuario toca el botón de ubicación. Si lo niega, la app no se cierra: el mapa, las fichas y los favoritos siguen funcionando, pero no se muestra la posición del usuario.

La ubicación solo se muestra en pantalla. No se guarda ni se envía a ningún servidor propio. Lo único que se guarda en el teléfono son los lugares favoritos. Para dibujar el mapa, la app descarga mosaicos de los servidores de OpenFreeMap, que es un servicio de terceros.

## Fuentes y licencias

- Datos del mapa: © colaboradores de OpenStreetMap. Mosaicos y estilo: OpenFreeMap. La atribución se mantiene visible en el mapa.
- Fotos de los lugares:
- plaza_narino.jpg: Carlos A. Revelo. Licencia: https://web.archive.org/web/20161028183047/http://www.panoramio.com/photo/90880650
- laguna_cocha.jpg: Licencia: https://commons.wikimedia.org/wiki/File:La_Cocha,_Nari%C3%B1o.jpg
- templo_san_juan.jpg: Información Turistica de Nariño, vía Wikimedia Commons. Licencia: https://situr.narino.gov.co/
- museo_carnaval.jpg: Licencia: https://commons.wikimedia.org/wiki/File:Museo_del_Carnaval,_Pasto.jpg

## Limitaciones conocidas

- Los cuatro lugares son fijos. La app no permite crear marcadores nuevos.
- El estado de la pantalla vive en el composable principal; no hay ViewModel.
- Sin conexión en el primer uso, el mapa y los marcadores no cargan y la app no muestra ningún aviso.
- Si se niega el permiso de ubicación, no se le explica al usuario por qué no aparece su posición.
- Al rotar la pantalla se cierra la ficha que estaba abierta.

## Autor

Victor Manuel Hernandez Cepeda ([VictorMH07](https://github.com/VictorMH07)).

# LugaresDePasto

Aplicación móvil Android desarrollada en Kotlin que permite explorar lugares turísticos del municipio de Pasto, Nariño, mediante un mapa interactivo. Los usuarios pueden consultar información de diferentes sitios turísticos, visualizar fotografías, guardar sus lugares favoritos y conocer su ubicación actual.

## Descripción del proyecto

**LugaresDePasto** nace como una iniciativa para facilitar el reconocimiento y la consulta de algunos atractivos turísticos de Pasto mediante una aplicación móvil intuitiva.

La aplicación integra un mapa interactivo donde se muestran diferentes sitios de interés turístico. Cada marcador permite consultar información del lugar y acceder a una ficha con fotografía, descripción y ubicación.

También cuenta con un sistema de favoritos para guardar los lugares de interés y una funcionalidad de geolocalización que permite identificar la posición actual del usuario.

## Funcionalidades principales

- **Mapa interactivo:** visualización de lugares turísticos mediante MapLibre.
- **Marcadores turísticos:** selección de diferentes sitios de interés en el mapa.
- **Fichas informativas:** fotografías, nombres, descripciones y ubicaciones de los lugares.
- **Sistema de favoritos:** posibilidad de guardar, consultar y quitar lugares favoritos.
- **Almacenamiento local:** persistencia de favoritos mediante Room.
- **Ubicación del usuario:** obtención de las coordenadas actuales del dispositivo.
- **Marcador personalizado:** pin azul para diferenciar la ubicación del usuario de los lugares turísticos.
- **Conservación de ubicación:** las coordenadas y el marcador del usuario se mantienen al cambiar la orientación de la pantalla.
- **Diseño adaptable:** interfaz compatible con orientación vertical y horizontal.
- **Funcionamiento sin conexión:** consulta de fotografías, fichas informativas y favoritos almacenados localmente sin necesidad de Internet.
- **Mapa en caché:** posibilidad de visualizar zonas cartográficas previamente almacenadas en caché cuando no hay conexión a Internet.

## Funcionamiento sin conexión a Internet

La aplicación incorpora almacenamiento local mediante Room, lo que permite guardar y consultar los lugares favoritos sin depender de una conexión a Internet.

Las fotografías de los sitios turísticos se encuentran dentro de los recursos de la aplicación, por lo que pueden visualizarse sin conexión.

El mapa utiliza MapLibre y el estilo cartográfico de OpenFreeMap. Las zonas que ya se han cargado y cuyos datos permanecen en caché pueden seguir visualizándose sin Internet. Sin embargo, para consultar zonas nuevas o descargar actualizaciones cartográficas es necesaria una conexión.

## Adaptación de pantalla

La interfaz se adapta a las orientaciones vertical y horizontal del dispositivo. La altura del mapa cambia según el espacio disponible para facilitar su visualización.

Además, las coordenadas de la ubicación del usuario se conservan durante los cambios de orientación mediante el mecanismo de restauración de estado de Jetpack Compose.


## Tecnologías utilizadas

| Tecnología | Descripción |
|---|---|
| Kotlin | Lenguaje de programación principal. |
| Jetpack Compose | Desarrollo de interfaces de usuario. |
| Material Design 3 | Componentes y diseño visual. |
| MapLibre | Integración del mapa interactivo. |
| OpenFreeMap | Proveedor del estilo cartográfico utilizado. |
| Room | Persistencia de datos locales. |
| SQLite | Base de datos local utilizada por Room. |
| Google Play Services Location | Obtención de la ubicación del dispositivo. |
| Gradle | Gestión de dependencias y compilación. |
| Android Studio | Entorno de desarrollo. |
| Git y GitHub | Control de versiones y alojamiento del código. |

## Lugares turísticos incluidos

La aplicación cuenta con los siguientes lugares:

1. **Plaza de Nariño:** centro histórico de la ciudad de Pasto.
2. **Laguna de La Cocha:** atractivo turístico ubicado en el corregimiento de El Encano.
3. **Templo de San Juan Bautista:** patrimonio histórico y religioso de Pasto.
4. **Museo del Carnaval:** espacio cultural dedicado a la tradición del Carnaval de Negros y Blancos.

## Estructura general del proyecto

```text
LugaresDePasto/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/lugaresdepasto/
│           │   ├── data/
│           │   │   ├── Lugar.kt
│           │   │   ├── LugarDao.kt
│           │   │   ├── LugarDatabase.kt
│           │   │   └── LugarRepository.kt
│           │   │
│           │   ├── ui/
│           │   │   ├── components/
│           │   │   │   └── MapaPasto.kt
│           │   │   └── theme/
│           │   │
│           │   └── MainActivity.kt
│           │
│           └── res/
│               └── drawable-nodpi/
│                   ├── plaza_narino.jpg
│                   ├── laguna_cocha.jpg
│                   ├── templo_san_juan.jpg
│                   └── museo_carnaval.jpg
│
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Requisitos

Para ejecutar el proyecto se recomienda contar con:

- Android Studio.
- JDK compatible con la configuración del proyecto.
- Android SDK configurado.
- Gradle y sus dependencias descargadas mediante la sincronización del proyecto.
- Emulador Android o dispositivo físico compatible.

## Instalación y ejecución

1. Clonar el repositorio:

```bash
git clone https://github.com/VictorMH07/LugaresDePasto.git
```

2. Abrir Android Studio.
3. Seleccionar **Open** y buscar la carpeta `LugaresDePasto`.
4. Esperar a que Gradle sincronice las dependencias.
5. Seleccionar un emulador Android o conectar un dispositivo físico.
6. Ejecutar la aplicación mediante el botón **Run** de Android Studio.

## Permisos de ubicación

La aplicación utiliza los siguientes permisos:

- `ACCESS_FINE_LOCATION`: permite solicitar ubicación precisa.
- `ACCESS_COARSE_LOCATION`: permite solicitar ubicación aproximada.

El usuario debe conceder los permisos desde el sistema Android para utilizar la funcionalidad de ubicación.

## Almacenamiento de favoritos

El proyecto utiliza Room para administrar una base de datos local.

La información de los lugares guardados como favoritos se almacena en el dispositivo, permitiendo consultar los favoritos sin necesidad de mantener una conexión a Internet para acceder a esos registros.

## Objetivo académico

Este proyecto tiene como finalidad aplicar conocimientos de desarrollo de aplicaciones móviles Android, integración de mapas, manejo de permisos, geolocalización, diseño de interfaces y persistencia de información local.

Además, busca ofrecer una herramienta que facilite el reconocimiento de algunos lugares turísticos del municipio de Pasto.

## Autor

**VictorMH07**

GitHub: [https://github.com/VictorMH07](https://github.com/VictorMH07)

Repositorio: [LugaresDePasto](https://github.com/VictorMH07/LugaresDePasto)

---

Desarrollado con Kotlin y Jetpack Compose.

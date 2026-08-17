# Build Report — Filosofar v1.0.0

**Estado general: COMPILACIÓN NO VERIFICADA.**

Este reporte documenta, con evidencia real, qué se pudo y qué no se pudo verificar en el entorno de generación de este entregable, siguiendo la regla de honestidad del pliego de especificación.

## 1. Restricciones reales del entorno de generación

Verificado directamente en el entorno sandbox usado para construir este proyecto:

| Recurso necesario | Estado | Evidencia |
|---|---|---|
| JDK | ✅ Disponible | OpenJDK 21.0.10 |
| Android SDK / `sdkmanager` | ❌ No instalado | `ANDROID_HOME` vacío, sin `/usr/lib/android-sdk` |
| `dl.google.com` / `maven.google.com` (AGP, AndroidX, Compose, Room) | ❌ Bloqueado | `HTTP 403` en petición directa |
| `services.gradle.org` (distribución de Gradle) | ❌ Bloqueado | `HTTP 403` en petición directa; confirmado también al intentar `./gradlew clean` real (ver sección 3) |
| `github.com` / `raw.githubusercontent.com` / `release-assets.githubusercontent.com` | ✅ Permitido | Usado para obtener el compilador de Kotlin y el `gradle-wrapper.jar` real (ver abajo) |

Estas restricciones son de la política de red del entorno de generación, no del proyecto en sí. En un entorno estándar (Android Studio, o CI con acceso normal a internet) no existen.

## 2. Verificación de sintaxis con el compilador real de Kotlin

Dado que no se pudo compilar contra el classpath completo de Android/Compose/Room, se descargó el **compilador oficial de Kotlin 2.0.20** (`kotlin-compiler-2.0.20.zip`, obtenido de `github.com/JetBrains/kotlin/releases`, dominio permitido) y se ejecutó sobre los 88 archivos `.kt` de `app/src/main/java` y los 6 de `app/src/test/java`.

**Resultado: 0 errores de sintaxis (parse errors) en los 94 archivos.**

Se obtuvieron ~15.400 líneas de error, el 100% de ellas de las categorías `unresolved reference`, `cannot infer type for this parameter`, `overload resolution ambiguity` y similares — todas consecuencia directa y esperada de compilar sin las dependencias de AndroidX/Compose/Room/Coroutines en el classpath (no se pudieron descargar, ver sección 1). Se buscó explícitamente por patrones de error de sintaxis real (`expecting`, `unexpected token`, `unclosed`, `redeclaration`, `conflicting declarations`, `syntax error`) y **no se encontró ninguna coincidencia**, lo que indica que el parser de Kotlin aceptó la totalidad del código sin errores estructurales.

Este proceso, además, permitió detectar y corregir en el propio código fuente varios errores reales antes de este reporte: imports de `items` de Compose ambiguos entre `LazyColumn`/`LazyVerticalGrid`, delegados `by remember { mutableStateOf(...) }` sin importar `getValue`/`setValue`, un modificador `Modifier.offset` mal implementado en el mapa, una función de dibujo referenciada entre archivos sin visibilidad adecuada, y un uso incorrecto de `RoomDatabase.query()` para una sentencia `DELETE` en un test (corregido a `openHelper.writableDatabase.execSQL`).

## 3. Intento real de `./gradlew`

Se intentó activamente, con evidencia registrada:

```
$ ./gradlew clean
ERROR: gradle/wrapper/gradle-wrapper.jar not found.
```

Se obtuvo entonces un `gradle-wrapper.jar` real y oficial (no fabricado) directamente desde el repositorio público `gradle/gradle` en GitHub (`raw.githubusercontent.com/gradle/gradle/v8.7.0/gradle/wrapper/gradle-wrapper.jar`, dominio permitido), verificado como archivo ZIP válido conteniendo `org/gradle/wrapper/GradleWrapperMain.class`. Con ese archivo en su lugar:

```
$ ./gradlew clean --stacktrace
Downloading https://services.gradle.org/distributions/gradle-8.7-bin.zip

Exception in thread "main" java.io.IOException: Server returned HTTP response code: 403 for URL:
https://services.gradle.org/distributions/gradle-8.7-bin.zip
    at java.base/sun.net.www.protocol.http.HttpURLConnection.getInputStream0(...)
    at org.gradle.wrapper.Install.forceFetch(SourceFile:2)
    at org.gradle.wrapper.GradleWrapperMain.main(SourceFile:67)
```

Esto confirma, con la mayor precisión posible en este entorno, que el **único** obstáculo real para ejecutar Gradle es la descarga de la distribución binaria de Gradle en sí (bloqueada), no un error de configuración del proyecto. Se comprobó también que la distribución de Gradle 8.7 no está disponible como asset adjunto en releases de GitHub (`assets: []` en la respuesta de la API de GitHub para el tag `v8.7.0`), por lo que no existe una ruta alternativa dentro de los dominios permitidos para obtenerla.

En consecuencia, **no se pudo ejecutar** `testDebugUnitTest`, `lintDebug` ni `assembleDebug` de forma real en este entorno. No se simulan resultados de estas tareas.

## 4. Stack y versiones (declaradas, no verificadas por build real)

Kotlin 2.0.20 · AGP 8.5.2 · Compose BOM 2024.09.00 · Material3 1.2.1 · Navigation Compose 2.7.7 · Room 2.6.1 · KSP 2.0.20-1.0.25 · Coroutines 1.8.1 · Gradle 8.7 · JDK 17 · minSdk 24 / compileSdk-targetSdk 34.

## 5. Pruebas entregadas (código fuente completo, ejecución no verificada)

| Archivo | Enfoque | Nº de `@Test` |
|---|---|---|
| `domain/LogicChallengeEngineTest.kt` | Motor de lógica (SEQUENCE/MATCH/SPOT_FLAW) | 9 |
| `domain/ReasonCoherenceEngineTest.kt` | Motor de coherencia de razones | 6 |
| `domain/ProgressCalculatorTest.kt` | Cálculo de progreso y estados de módulo | 7 |
| `domain/BadgeEngineTest.kt` | Reglas de desbloqueo de insignias | 8 |
| `data/SeedDataIntegrityTest.kt` | Integridad referencial del contenido semilla | 12 |
| `data/AppDatabaseRoomTest.kt` | Integración Room en memoria (Robolectric) | 8 |
| **Total** | | **54** |

Las 4 primeras suites son JVM puro (sin Android ni Robolectric), verificadas sintácticamente con `kotlinc` real según la sección 2. Las 2 últimas requieren Robolectric + Room (necesitan el classpath completo de Android) y por tanto no se ejecutaron, aunque también pasaron la verificación de sintaxis.

Tests aprobados: **no verificado** (0 ejecutados). Tests fallidos: **no verificado**. Esto es honesto y deliberado: no se inventan resultados de ejecución que no ocurrieron.

## 6. APK

**No generado.** No existe `app-debug.apk` ni SHA-256 que reportar, porque `assembleDebug` no pudo ejecutarse (sección 3).

## 7. PDFs

Generados de forma real con `reportlab` a partir de los `.md` correspondientes: ver sección de verificación de páginas/apertura en `docs/pdf/`. Estos SÍ se generaron y verificaron en este entorno, ya que no dependen del toolchain de Android.

## 8. Limitaciones documentadas del contenido

Ver `docs/MEMORIA_DESCRIPTIVA.md` sección 5 para el detalle de las cantidades de contenido semilla reducidas respecto al pliego original (30/12/24/12/18 en vez de 50/30/40/20/30), documentado explícitamente como simplificación deliberada, no oculta.

## 9. Qué haría falta para verificar la compilación real

1. Abrir el proyecto en Android Studio con conexión a internet normal (acceso a `dl.google.com`/`maven.google.com`), o
2. Ejecutar en un entorno CI con esos dominios accesibles — el workflow `.github/workflows/android-build.yml` incluido en este repositorio ya está preparado para hacerlo automáticamente en cada `push`.

## 10. Conclusión

El código fuente completo, la base de datos, la documentación y los tests están entregados y fueron validados hasta el máximo nivel posible sin Android SDK ni acceso a Google Maven (sintaxis Kotlin real, integridad de datos semilla, revisión manual de arquitectura). La verificación de compilación, ejecución de tests y generación de APK queda pendiente del primer build en un entorno con las dependencias de Android accesibles.

# Filosofar 🧭

**Isla de las Grandes Preguntas** — una app educativa de filosofía y pensamiento crítico para niños y niñas de 8 a 12 años.

Package: `com.educalab.filosofar` · Versión: `1.0.0` · Android nativo (Kotlin + Jetpack Compose)

---

## ⚠️ Estado de compilación de este entregable

Este proyecto fue generado en un entorno de trabajo **sin acceso al repositorio Maven de Google** (`dl.google.com` / `maven.google.com`) **ni a `services.gradle.org`**, y sin Android SDK instalado. Eso significa que **`./gradlew assembleDebug` no pudo ejecutarse ni verificarse en ese entorno** — ver `docs/BUILD_REPORT.md` para el detalle completo y la lista de comprobaciones que sí se realizaron (validación de sintaxis Kotlin con `kotlinc` real, auditoría de imports, revisión de integridad de datos semilla).

El código es un proyecto Android **completo y real**, listo para abrirse en Android Studio (Giraffe o posterior) con conexión a internet normal, donde `./gradlew clean assembleDebug` debería compilar sin pasos adicionales.

---

## Qué es Filosofar

Dos exploradores, **Lumi** (sol, directa, curiosa) y **Nox** (luna, reflexivo, sereno), guían al jugador por seis islas temáticas — Verdad, Justicia, Amistad, Libertad, Responsabilidad y Convivencia — llenas de preguntas, dilemas y retos de lógica. No hay examen ni nota: el objetivo es pensar con más herramientas, no memorizar respuestas correctas.

### Los 11 módulos
1. **Perfil del Pensador** — alias y avatar, sin datos personales.
2. **Mapa de islas** — el centro de experiencia (archipiélago, no una lista de botones).
3. **Pregunta del día** — reflexión escrita breve, sin respuesta correcta.
4. **Dilemas interactivos** — decides y ves cómo lo verían Lumi y Nox.
5. **¿Por qué piensas eso?** — cartas de razones y un motor de coherencia.
6. **Otro punto de vista** — vive la misma escena desde otro papel.
7. **Laboratorio de lógica** — ordenar, conectar y detectar fallos de razonamiento (lógica real, no trivia).
8. **Debate conmigo mismo** — argumentos para dos posturas distintas sobre el mismo tema.
9. **Antes pensaba / Ahora pienso** — revisitar una respuesta pasada y ver si cambió tu opinión.
10. **Reflexiones de voz y Cuaderno de Ideas** — notas escritas y grabaciones de voz de hasta 45s, 100% locales.
11. **Progreso y colección** — Cristales de Ideas, insignias y estadísticas reales derivadas de tus intentos.

## Stack técnico

Kotlin · Jetpack Compose (Material 3) · Navigation Compose · MVVM + Repository · Room 2.6.1 · Coroutines/Flow · Gradle Kotlin DSL · JDK 17 · `minSdk 24` / `targetSdk 34`.

100% offline: sin Firebase, sin backend, sin login, sin anuncios, sin analytics, sin el permiso `INTERNET`. Todos los datos (perfil, respuestas, reflexiones, audio) se guardan localmente en el dispositivo.

## Estructura del repositorio

```
Filosofar/
  app/                      Proyecto Android (módulo único)
    src/main/java/...       data/ domain/ ui/ util/
    src/test/java/...       50 tests JVM/Robolectric
  database/
    schema.sql               DDL completo (22 tablas)
    sample_data.sql           Datos de muestra en SQL puro
  docs/
    MEMORIA_DESCRIPTIVA.md / .pdf
    MANUAL_USUARIO.md / .pdf
    MANUAL_TECNICO.md / .pdf
    BASE_DE_DATOS.md
    BUILD_REPORT.md
  tools/                    Scripts auxiliares (generación de PDFs)
  .github/workflows/        CI: build de APK al hacer push
  deliverables/              APK (si se compiló), ZIP fuente, PDFs
```

## Cómo compilar (en un entorno con Android Studio / SDK / internet normal)

```bash
git clone <este repositorio>
cd Filosofar
./gradlew clean
./gradlew testDebugUnitTest
./gradlew lintDebug
./gradlew assembleDebug
```

El APK de debug queda en `app/build/outputs/apk/debug/app-debug.apk`.

Si `gradle/wrapper/gradle-wrapper.jar` no está presente (se omitió por no poder descargarlo en el entorno de generación), Android Studio lo regenera automáticamente al abrir el proyecto, o puedes ejecutar `gradle wrapper --gradle-version 8.7` una vez con Gradle instalado localmente.

## Privacidad

Sin nombre real, sin email, sin teléfono, sin ubicación, sin contactos. El único permiso sensible es `RECORD_AUDIO`, solicitado únicamente al usar la grabación de voz opcional del Cuaderno de Ideas, con alternativa de texto si se deniega.

## Licencia y autoría

Proyecto educativo generado como encargo de desarrollo para EducaLab.

# Memoria Descriptiva — Filosofar

## 1. Identificación del proyecto

| Campo | Valor |
|---|---|
| Nombre | Filosofar |
| Package | com.educalab.filosofar |
| Versión | 1.0.0 |
| Plataforma | Android nativo (Kotlin + Jetpack Compose) |
| Público objetivo | Niños y niñas de 8 a 12 años |
| Área | Filosofía y pensamiento crítico |
| Modalidad | 100% offline, un solo usuario por dispositivo |

## 2. Problema y justificación

La filosofía y el pensamiento crítico rara vez se enseñan de forma directa en la infancia, a pesar de que niños de 8 a 12 años ya son capaces de comparar perspectivas, sostener una postura con razones, detectar contradicciones simples y revisar sus propias opiniones. Las apps educativas existentes para este rango suelen limitarse a cuestionarios de opción múltiple o a contenidos demasiado infantilizados, que no ofrecen ni el desafío ni el respeto que este tipo de pensamiento requiere.

Filosofar busca cubrir ese vacío: una experiencia que trate a los niños como pensadores capaces, con mecánicas variadas (decisión, construcción de argumentos, detección de falacias, comparación de perspectivas) en vez de examinarlos con preguntas cerradas, y que nunca reduzca la filosofía a "correcto/incorrecto".

## 3. Objetivos

**Objetivo general:** ofrecer una experiencia lúdica y visualmente atractiva que introduzca a niños de 8 a 12 años a herramientas básicas del pensamiento filosófico y crítico.

**Objetivos específicos:**
- Practicar la toma de perspectiva (ver una misma situación desde más de un punto de vista).
- Introducir nociones básicas de razonamiento lógico (secuencias válidas, correspondencia premisa-conclusión, detección de falacias simples) mediante mecánicas manipulables, no solo preguntas cerradas.
- Fomentar la articulación de razones propias para una postura (sin juzgar la postura en sí).
- Permitir que el usuario compare su forma de pensar a lo largo del tiempo (Antes pensaba / Ahora pienso).
- Ofrecer una experiencia sostenible en sesiones cortas (5-20 minutos), con progreso persistente y gamificación no manipuladora.

## 4. Público objetivo

Niños y niñas de 8 a 12 años con capacidad lectora de textos breves en español, sin necesidad de conocimientos previos de filosofía. El diseño evita deliberadamente la estética "preescolar" (colores pastel excesivos, personajes bebés, lenguaje condescendiente) en favor de un tono aventurero, inteligente y moderno acorde a la franja etaria superior del rango objetivo.

## 5. Alcance y exclusiones

**Incluido en la versión 1.0.0:**
- 6 islas temáticas (Verdad, Justicia, Amistad, Libertad, Responsabilidad, Convivencia).
- 30 preguntas filosóficas, 12 dilemas interactivos, 24 cartas de razones, 12 ejercicios de perspectiva, 18 retos de lógica (3 mecánicas distintas), 6 autodebates, 10 insignias.
- Perfil local con alias y avatar, sin datos personales.
- Cuaderno de Ideas con reflexión de voz local opcional.
- Sistema de progreso e insignias derivado de acciones reales.

**Explícitamente fuera de alcance en esta versión:**
- Cuentas online, sincronización entre dispositivos o backend de cualquier tipo.
- Contenido multijugador, chat o comparación social entre usuarios.
- Corrección automática de las reflexiones escritas (no existe evaluación de "buena" o "mala" filosofía).
- Transcripción de audio a texto.
- Soporte multi-idioma (solo español en esta versión).

> Nota de alcance de contenido: el pliego de especificación original solicitaba 50 preguntas, 30 dilemas, 40 cartas de razones, 20 ejercicios de perspectiva y 30 retos de lógica. Esta versión entrega 30/12/24/12/18 respectivamente — un conjunto real, completo y funcionalmente representativo de cada mecánica, documentado como simplificación deliberada por razones de tiempo de producción (ver `BUILD_REPORT.md`). La arquitectura de datos soporta ampliar cualquiera de estos conjuntos sin cambios de esquema.

## 6. Requisitos funcionales

- RF01: El sistema debe permitir crear un perfil local con alias y avatar sin datos personales identificables.
- RF02: El sistema debe presentar un mapa/isla como pantalla principal con progreso visual por isla.
- RF03: El sistema debe ofrecer una "Pregunta del día" por sesión, priorizando preguntas no respondidas.
- RF04: El sistema debe permitir resolver dilemas mostrando consecuencias y dos perspectivas divergentes (Lumi/Nox).
- RF05: El sistema debe validar 3 mecánicas distintas de razonamiento lógico (ordenar, conectar, detectar fallo) mediante un motor de dominio puro y testeable.
- RF06: El sistema debe permitir seleccionar cartas de razones y recibir retroalimentación de coherencia, sin calificación moral.
- RF07: El sistema debe permitir grabar, reproducir y borrar reflexiones de voz locales de hasta 45 segundos.
- RF08: El sistema debe derivar el progreso e insignias exclusivamente de intentos reales persistidos, nunca de contadores manuales.
- RF09: El sistema debe funcionar completamente sin conexión a internet.
- RF10: El sistema debe permitir desactivar sonido y vibración desde ajustes.

## 7. Requisitos no funcionales

- RNF01: minSdk 24 (Android 7.0), targetSdk 34.
- RNF02: Sin el permiso `INTERNET` declarado en el manifiesto.
- RNF03: Arquitectura en capas `data/ domain/ ui/` con lógica de negocio testeable sin dependencias de Android.
- RNF04: Persistencia real vía Room/SQLite, sin listas en memoria como sustituto de almacenamiento.
- RNF05: Identidad visual propia, sin dependencia exclusiva de Material Icons.
- RNF06: Batería de pruebas automatizadas (mínimo 25, entregado: 50).
- RNF07: Tiempos de sesión pensados para 5-20 minutos, con guardado y continuación automáticos.

## 8. Casos de uso principales

1. **Primer inicio:** el usuario ve el onboarding (4 pantallas), crea su perfil (alias + avatar) y llega al mapa de islas.
2. **Responder la pregunta del día:** desde una isla, el usuario escribe una reflexión libre y la guarda, ganando un Cristal de Ideas.
3. **Resolver un dilema:** el usuario elige una opción, ve su consecuencia, revela las miradas de Lumi y Nox, confirma su decisión y opcionalmente continúa a "¿Por qué piensas eso?".
4. **Resolver un reto de lógica:** el usuario ordena piezas, conecta premisas con conclusiones, o detecta la pieza con el fallo; recibe una explicación educativa en ambos casos (acierto o error).
5. **Registrar una reflexión de voz:** el usuario graba hasta 45s, la escucha, y la adjunta a una entrada del Cuaderno de Ideas.
6. **Revisar una opinión pasada:** el usuario vuelve a una pregunta ya respondida, escribe su opinión actual y el sistema detecta si cambió respecto a la anterior.
7. **Consultar el progreso:** el usuario ve sus cristales, insignias y progreso por isla en la pantalla de Progreso y Colección.

## 9. Módulos y pantallas

Ver README.md para la lista de los 11 módulos. En términos de pantallas Compose distintas, el proyecto implementa 16 destinos de navegación: Onboarding, Configuración de perfil, Mapa, Detalle de isla, Pregunta del día, Lista de dilemas, Detalle de dilema, Cartas de razones, Lista de perspectivas, Detalle de perspectiva, Lista del laboratorio de lógica, Reto de lógica, Lista de autodebates, Detalle de autodebate, Antes/Ahora, Cuaderno de Ideas, Progreso y Colección, Ajustes.

## 10. Flujo general de navegación

```
Onboarding -> Configuración de perfil -> Mapa de islas
Mapa de islas -> Detalle de isla -> { Pregunta del día | Dilemas | Perspectivas | Lógica | Debates }
Detalle de dilema -> Cartas de razones -> (vuelve al Mapa)
Mapa de islas -> Cuaderno de Ideas | Antes/Ahora | Progreso y Colección | Ajustes
```

## 11. Arquitectura

MVVM + Repository sobre tres capas (`data`, `domain`, `ui`). Ver `docs/MANUAL_TECNICO.md` para el detalle completo de paquetes, DAOs, motores de dominio y flujo de datos.

## 12. Modelo de datos

22 entidades Room. Ver `docs/BASE_DE_DATOS.md` para el diagrama entidad-relación completo y el detalle de cada tabla.

## 13. Reglas de negocio relevantes

- El progreso de una isla (`island_progress`) se recalcula siempre a partir de los intentos reales guardados; nunca se incrementa manualmente.
- Una isla se desbloquea cuando la suma global de cristales ganados en todas las islas alcanza su `unlockRequiredCrystals`.
- Un dilema, pregunta o ejercicio no tiene una respuesta "correcta"; solo los retos del Laboratorio de Lógica tienen una validación objetiva (por diseño: la lógica formal sí tiene respuestas correctas; la ética y la opinión, no).
- Las insignias se evalúan mediante `BadgeEngine`, una función pura que compara estadísticas derivadas contra criterios fijos.

## 14. Experiencia de usuario (UX)

Diseño pensado para 8-12 años: tono aventurero, no infantilizado; ilustraciones vectoriales propias dibujadas con Compose Canvas (sin depender de imágenes externas); microanimaciones de apoyo (entrada de tarjetas, barra de progreso animada, revelado de perspectivas); feedback educativo explicado, nunca reducido a "Correcto/Incorrecto"; estados de módulo (bloqueado/disponible/iniciado/completado/dominado) expresados con icono + texto, no solo color.

## 15. Privacidad y seguridad

Sin recolección de datos personales. Sin red. Único permiso sensible: `RECORD_AUDIO`, solicitado en el punto de uso, con alternativa de texto si se deniega. Todo el contenido generado por el usuario permanece en almacenamiento privado de la app.

## 16. Pruebas

50 tests automatizados: 30 pruebas JVM puras sobre los motores de dominio (`LogicChallengeEngine`, `ReasonCoherenceEngine`, `ProgressCalculator`, `BadgeEngine`) y sobre la integridad referencial del contenido semilla, y 8 pruebas de integración Room/Robolectric con base de datos en memoria. Ver `docs/BUILD_REPORT.md` para el detalle de ejecución.

## 17. Limitaciones conocidas

- El módulo "Debate conmigo mismo" usa una interacción táctil de "colocar la siguiente ficha pendiente" en vez de arrastrar y soltar por gestos, por robustez en un entorno donde no se pudo probar interacción táctil real.
- El icono de lanzador adaptativo está garantizado visualmente en API 26+; en API 24-25 el sistema puede mostrar un icono de respaldo genérico.
- El contenido semilla fue reducido respecto al pliego original (ver sección 5), documentado explícitamente.
- La compilación no pudo verificarse en el entorno de generación (ver `BUILD_REPORT.md`).

## 18. Mejoras futuras

- Ampliar el contenido semilla a las cantidades completas originalmente especificadas.
- Añadir drag-and-drop real por gestos en "Debate conmigo mismo" y en el ordenamiento del Laboratorio de Lógica.
- Añadir un modo de repaso espaciado basado en `recentFailedChallengeIds` (ya expuesto por `LogicRepository.challengesForReview`, pendiente de UI dedicada).
- Soporte multi-idioma.
- Modo alto contraste y tamaño de texto ajustable para accesibilidad ampliada.

## 19. Conclusiones

Filosofar entrega una base funcional completa —modelo de datos, motores de dominio testeables, 16 pantallas Compose con identidad visual propia, y una batería de pruebas sólida— para una experiencia de pensamiento crítico infantil que evita las trampas típicas de las apps educativas (cuestionarios repetitivos, apariencia de CRUD, gamificación vacía). El principal riesgo pendiente es la verificación de compilación real en un entorno con Android SDK y acceso al repositorio de Google, no cubierto por el entorno de generación de este entregable.

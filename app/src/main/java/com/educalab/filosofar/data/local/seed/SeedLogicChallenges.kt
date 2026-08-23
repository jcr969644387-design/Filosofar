package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.entity.LogicChallengeEntity
import com.educalab.filosofar.data.local.entity.LogicChallengeItemEntity

/**
 * 54 retos de lógica real (9 por isla: 3 días x 3 tipos por día): SEQUENCE
 * (ordenar un razonamiento), MATCH (conectar premisas con su conclusión) y
 * SPOT_FLAW (encontrar la pieza que rompe el razonamiento).
 */
object SeedLogicChallenges {

    const val SEQUENCE = "SEQUENCE"
    const val MATCH = "MATCH"
    const val SPOT_FLAW = "SPOT_FLAW"

    data class RawChallenge(
        val id: String,
        val islandId: String,
        val type: String,
        val prompt: String,
        val explanation: String,
        val order: Int,
        val items: List<RawItem>
    )

    data class RawItem(
        val text: String,
        val correctPosition: Int = -1,
        val pairKey: String = "",
        val role: String = "",
        val isFlawed: Boolean = false
    )

    private val raw = listOf(
        RawChallenge(
            "logic_verdad_1", SeedIslands.VERDAD, SEQUENCE,
            "Ordena este razonamiento sobre por qué confiar en una fuente.",
            "Un buen razonamiento va de la observación a la conclusión: primero varias fuentes coinciden, luego confiamos más en la información.",
            0,
            listOf(
                RawItem("Tres libros distintos cuentan la misma fecha de un evento histórico.", correctPosition = 0),
                RawItem("Las tres fuentes son independientes entre sí.", correctPosition = 1),
                RawItem("Es poco probable que las tres se equivoquen exactamente igual por azar.", correctPosition = 2),
                RawItem("Por eso, esa fecha es una información en la que podemos confiar más.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_verdad_2", SeedIslands.VERDAD, MATCH,
            "Conecta cada observación con la conclusión que realmente se sigue de ella.",
            "Una conclusión válida se apoya solo en lo que la observación permite afirmar, ni más ni menos.",
            1,
            listOf(
                RawItem("El termómetro marca 2°C en la calle.", pairKey = "p1", role = "PREMISE"),
                RawItem("Hace frío afuera ahora mismo.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("El libro que buscabas no está en la estantería.", pairKey = "p2", role = "PREMISE"),
                RawItem("Alguien pudo haberlo movido o llevado prestado.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Todas las luces de la casa están apagadas.", pairKey = "p3", role = "PREMISE"),
                RawItem("Es probable que no haya nadie despierto en casa.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_verdad_3", SeedIslands.VERDAD, SPOT_FLAW,
            "Este razonamiento sobre un compañero tiene un fallo. Encuéntralo.",
            "El fallo es una generalización apresurada: un solo caso no basta para concluir algo sobre todo un grupo.",
            2,
            listOf(
                RawItem("Ayer un compañero de otra clase llegó tarde.", isFlawed = false),
                RawItem("Por lo tanto, todos los compañeros de esa clase siempre llegan tarde.", isFlawed = true),
                RawItem("Llegar tarde una vez puede tener muchas causas distintas.", isFlawed = false),
                RawItem("Para saber si es una costumbre del grupo, habría que observar varias veces.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_justicia_1", SeedIslands.JUSTICIA, SEQUENCE,
            "Ordena los pasos para decidir un reparto justo de tareas en un proyecto grupal.",
            "Repartir justamente empieza por conocer la situación de cada persona, no por repartir al azar.",
            0,
            listOf(
                RawItem("Se pregunta a cada integrante cuánto tiempo tiene disponible.", correctPosition = 0),
                RawItem("Se listan todas las tareas que hay que hacer.", correctPosition = 1),
                RawItem("Se reparten las tareas según el tiempo disponible de cada uno.", correctPosition = 2),
                RawItem("Se revisa juntos si el reparto final parece equilibrado.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_justicia_2", SeedIslands.JUSTICIA, MATCH,
            "Conecta cada situación con el tipo de justicia que mejor la describe.",
            "Existen distintas formas de pensar la justicia: repartir igual, repartir según necesidad, o reparar un daño.",
            1,
            listOf(
                RawItem("Dar el mismo trozo de pizza a cada niño del grupo.", pairKey = "p1", role = "PREMISE"),
                RawItem("Reparto igualitario.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Dar más tiempo de examen a quien tiene una dificultad para leer.", pairKey = "p2", role = "PREMISE"),
                RawItem("Reparto según necesidad.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Pedir a quien rompió un juguete que ayude a arreglarlo.", pairKey = "p3", role = "PREMISE"),
                RawItem("Justicia reparadora.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_justicia_3", SeedIslands.JUSTICIA, SPOT_FLAW,
            "Este razonamiento sobre un castigo en clase tiene un fallo. Encuéntralo.",
            "El fallo es suponer que, porque alguien fue castigado antes, merece cualquier castigo ahora, sin mirar lo que realmente pasó esta vez.",
            2,
            listOf(
                RawItem("Un alumno fue castigado el mes pasado por hablar en clase.", isFlawed = false),
                RawItem("Hoy se rompió una silla y nadie sabe quién fue.", isFlawed = false),
                RawItem("Por lo tanto, seguro que fue el mismo alumno de la vez pasada.", isFlawed = true),
                RawItem("Sin pruebas de esta vez, no se puede culpar solo por lo que pasó antes.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_amistad_1", SeedIslands.AMISTAD, SEQUENCE,
            "Ordena los pasos para resolver un malentendido con un amigo.",
            "Resolver un malentendido empieza por escuchar, no por asumir ni por reaccionar.",
            0,
            listOf(
                RawItem("Notas que tu amigo está distante contigo.", correctPosition = 0),
                RawItem("Le preguntas con calma si pasa algo entre ustedes.", correctPosition = 1),
                RawItem("Escuchas su versión antes de sacar conclusiones.", correctPosition = 2),
                RawItem("Juntos aclaran el malentendido y siguen adelante.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_amistad_2", SeedIslands.AMISTAD, MATCH,
            "Conecta cada acción con lo que probablemente demuestra sobre una amistad.",
            "Las acciones concretas suelen decir tanto o más que las palabras sobre cómo es una amistad.",
            1,
            listOf(
                RawItem("Un amigo te espera aunque llegues tarde sin avisar mucho.", pairKey = "p1", role = "PREMISE"),
                RawItem("Muestra paciencia y confianza.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Un amigo te avisa cuando cree que estás cometiendo un error.", pairKey = "p2", role = "PREMISE"),
                RawItem("Muestra sinceridad, aunque incomode.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Un amigo solo te llama cuando necesita algo.", pairKey = "p3", role = "PREMISE"),
                RawItem("Puede mostrar una amistad poco equilibrada.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_amistad_3", SeedIslands.AMISTAD, SPOT_FLAW,
            "Este razonamiento sobre una amistad tiene un fallo. Encuéntralo.",
            "El fallo es la conclusión circular: repite la misma idea con otras palabras, sin aportar una razón nueva.",
            2,
            listOf(
                RawItem("Ana dice que Beto es su mejor amigo.", isFlawed = false),
                RawItem("Beto es su mejor amigo porque Ana lo considera su mejor amigo.", isFlawed = true),
                RawItem("Esa frase no explica qué hace que Beto sea un buen amigo.", isFlawed = false),
                RawItem("Una buena razón daría un ejemplo concreto, como su confianza o su apoyo.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_libertad_1", SeedIslands.LIBERTAD, SEQUENCE,
            "Ordena el razonamiento sobre por qué existen los límites de velocidad para bicicletas en el parque.",
            "Una buena norma parte de un riesgo real, no de prohibir por prohibir.",
            0,
            listOf(
                RawItem("Ir muy rápido en bici cerca de otras personas puede causar accidentes.", correctPosition = 0),
                RawItem("El parque es un espacio compartido por muchas personas distintas.", correctPosition = 1),
                RawItem("Por eso se establece un límite de velocidad razonable.", correctPosition = 2),
                RawItem("Ese límite protege la libertad de todos, no solo la de quien va en bici.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_libertad_2", SeedIslands.LIBERTAD, MATCH,
            "Conecta cada situación con el tipo de límite a la libertad que representa.",
            "No todos los límites son iguales: algunos protegen a otros, otros solo organizan la convivencia.",
            1,
            listOf(
                RawItem("No se permite fumar dentro de un aula.", pairKey = "p1", role = "PREMISE"),
                RawItem("Protege la salud de otras personas.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Hay un horario para usar la cancha de baloncesto.", pairKey = "p2", role = "PREMISE"),
                RawItem("Organiza el uso de un espacio compartido.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Se debe pedir turno para hablar en una asamblea.", pairKey = "p3", role = "PREMISE"),
                RawItem("Permite que todos puedan expresarse.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_libertad_3", SeedIslands.LIBERTAD, SPOT_FLAW,
            "Este razonamiento sobre las normas tiene un fallo. Encuéntralo.",
            "El fallo es una falsa causa: que dos cosas ocurran juntas no significa que una haya causado la otra.",
            2,
            listOf(
                RawItem("Desde que hay una norma nueva de silencio, las notas del curso subieron.", isFlawed = false),
                RawItem("Por lo tanto, la norma de silencio fue la única causa de la mejora.", isFlawed = true),
                RawItem("Pudo haber otras causas al mismo tiempo, como más horas de estudio.", isFlawed = false),
                RawItem("Para estar seguros, habría que comparar con un grupo sin esa norma.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_resp_1", SeedIslands.RESPONSABILIDAD, SEQUENCE,
            "Ordena los pasos para reparar un error cometido con un compañero.",
            "Reparar un error real sigue un orden: reconocerlo, disculparse, y actuar para que no se repita.",
            0,
            listOf(
                RawItem("Te das cuenta de que tu comentario hirió a un compañero.", correctPosition = 0),
                RawItem("Reconoces que lo que dijiste estuvo mal, sin excusas.", correctPosition = 1),
                RawItem("Te disculpas directamente con esa persona.", correctPosition = 2),
                RawItem("Cuidas tus palabras la próxima vez en una situación parecida.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_resp_2", SeedIslands.RESPONSABILIDAD, MATCH,
            "Conecta cada situación con el nivel de responsabilidad que mejor le corresponde.",
            "La responsabilidad cambia según si algo fue intencional, accidental o ajeno a nuestro control.",
            1,
            listOf(
                RawItem("Empujas a alguien sin querer al correr por un pasillo estrecho.", pairKey = "p1", role = "PREMISE"),
                RawItem("Responsabilidad por accidente: se pide disculpas y se tiene más cuidado.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Rompes algo a propósito porque estabas enfadado.", pairKey = "p2", role = "PREMISE"),
                RawItem("Responsabilidad plena: hay que reparar el daño y reflexionar sobre el enfado.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Llueve y se cancela un partido que habías organizado.", pairKey = "p3", role = "PREMISE"),
                RawItem("No hay responsabilidad personal: fue una causa fuera de tu control.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_resp_3", SeedIslands.RESPONSABILIDAD, SPOT_FLAW,
            "Este razonamiento sobre un trabajo en grupo tiene un fallo. Encuéntralo.",
            "El fallo es culpar a todo el grupo por igual sin distinguir quién hizo qué realmente.",
            2,
            listOf(
                RawItem("El trabajo en grupo se entregó incompleto.", isFlawed = false),
                RawItem("Solo dos de los cinco integrantes no enviaron su parte a tiempo.", isFlawed = false),
                RawItem("Por lo tanto, los cinco integrantes son igual de responsables.", isFlawed = true),
                RawItem("Distinguir quién cumplió y quién no permite repartir la responsabilidad de forma más justa.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_conv_1", SeedIslands.CONVIVENCIA, SEQUENCE,
            "Ordena los pasos para resolver un conflicto entre dos grupos del recreo por el uso de la cancha.",
            "Resolver un conflicto de convivencia empieza por escuchar a ambas partes antes de proponer una solución.",
            0,
            listOf(
                RawItem("Dos grupos quieren usar la misma cancha a la misma hora.", correctPosition = 0),
                RawItem("Un profesor escucha lo que quiere cada grupo.", correctPosition = 1),
                RawItem("Entre todos proponen turnos alternos para usar la cancha.", correctPosition = 2),
                RawItem("Ambos grupos aceptan el nuevo horario compartido.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_conv_2", SeedIslands.CONVIVENCIA, MATCH,
            "Conecta cada actitud con el efecto que suele tener en la convivencia de un grupo.",
            "Ciertas actitudes tienden a acercar a un grupo, y otras tienden a alejarlo, aunque no sea la intención.",
            1,
            listOf(
                RawItem("Preguntar a alguien nuevo cómo se llama y qué le gusta.", pairKey = "p1", role = "PREMISE"),
                RawItem("Tiende a acercar y generar confianza.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Reírse de un acento distinto al hablar.", pairKey = "p2", role = "PREMISE"),
                RawItem("Tiende a alejar y generar incomodidad.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Explicar con calma una regla del juego a quien no la conoce.", pairKey = "p3", role = "PREMISE"),
                RawItem("Tiende a incluir a quien se siente perdido.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_conv_3", SeedIslands.CONVIVENCIA, SPOT_FLAW,
            "Este razonamiento sobre una costumbre distinta tiene un fallo. Encuéntralo.",
            "El fallo es suponer que 'distinto' significa 'incorrecto', sin ninguna razón real que lo respalde.",
            2,
            listOf(
                RawItem("Un compañero celebra una fiesta que tu familia no celebra.", isFlawed = false),
                RawItem("Esa fiesta es diferente a las que tú conoces.", isFlawed = false),
                RawItem("Por lo tanto, esa fiesta debe de estar mal o ser rara.", isFlawed = true),
                RawItem("Que algo sea distinto a lo que conocemos no lo hace incorrecto.", isFlawed = false)
            )
        ),
        // --- Día 2 (orden 3-5) ---
        RawChallenge(
            "logic_verdad_4", SeedIslands.VERDAD, SEQUENCE,
            "Ordena el razonamiento sobre revisar una fuente antes de compartirla.",
            "Buscar confirmación antes de compartir algo dudoso es parte de cuidar la verdad.",
            3,
            listOf(
                RawItem("Un compañero comparte una noticia sorprendente en el chat.", correctPosition = 0),
                RawItem("Antes de reenviarla, buscas si otra fuente confiable dice lo mismo.", correctPosition = 1),
                RawItem("Encuentras que ninguna otra fuente la confirma.", correctPosition = 2),
                RawItem("Decides no compartirla hasta tener más certeza.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_verdad_5", SeedIslands.VERDAD, MATCH,
            "Conecta cada pista con la conclusión que realmente se sigue.",
            "Una conclusión válida se queda dentro de lo que la pista permite afirmar.",
            4,
            listOf(
                RawItem("El termómetro del salón marca 30°C.", pairKey = "p1", role = "PREMISE"),
                RawItem("Hace calor dentro del salón.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Todos los libros de esa repisa tienen el mismo sello.", pairKey = "p2", role = "PREMISE"),
                RawItem("Pertenecen a la misma biblioteca.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Un amigo llega con el pelo mojado y paraguas.", pairKey = "p3", role = "PREMISE"),
                RawItem("Es probable que esté lloviendo afuera.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_verdad_6", SeedIslands.VERDAD, SPOT_FLAW,
            "Este razonamiento sobre un examen tiene un fallo. Encuéntralo.",
            "El fallo es generalizar a partir de un solo caso.",
            5,
            listOf(
                RawItem("Un estudiante sacó buena nota copiando una vez.", isFlawed = false),
                RawItem("Por lo tanto, copiar siempre garantiza buenas notas.", isFlawed = true),
                RawItem("Una sola vez no prueba una regla general.", isFlawed = false),
                RawItem("Muchos otros factores pueden explicar esa nota.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_justicia_4", SeedIslands.JUSTICIA, SEQUENCE,
            "Ordena los pasos para resolver de forma justa un conflicto por un objeto perdido.",
            "Buscar evidencia concreta antes de decidir ayuda a que el reparto sea más justo.",
            3,
            listOf(
                RawItem("Dos niños dicen que el mismo lápiz es suyo.", correctPosition = 0),
                RawItem("Se les pregunta a ambos por detalles del lápiz.", correctPosition = 1),
                RawItem("Uno describe correctamente una marca que el otro no sabía.", correctPosition = 2),
                RawItem("Se le entrega el lápiz a quien dio la descripción correcta.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_justicia_5", SeedIslands.JUSTICIA, MATCH,
            "Conecta cada situación con el principio de justicia que representa.",
            "Existen varias formas válidas de pensar la justicia, según la situación.",
            4,
            listOf(
                RawItem("Se da más tiempo de examen a quien tiene una dificultad de lectura.", pairKey = "p1", role = "PREMISE"),
                RawItem("Justicia según la necesidad.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Se reparte el mismo número de golosinas a cada niño.", pairKey = "p2", role = "PREMISE"),
                RawItem("Justicia igualitaria.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Se pide a quien rayó la pared que ayude a limpiarla.", pairKey = "p3", role = "PREMISE"),
                RawItem("Justicia reparadora.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_justicia_6", SeedIslands.JUSTICIA, SPOT_FLAW,
            "Este razonamiento sobre un castigo tiene un fallo. Encuéntralo.",
            "El fallo es castigar sin distinguir responsabilidad real de solo estar presente.",
            5,
            listOf(
                RawItem("Un grupo de tres niños estaba cerca cuando se rompió una ventana.", isFlawed = false),
                RawItem("Por lo tanto, los tres merecen el mismo castigo.", isFlawed = true),
                RawItem("Estar cerca no prueba haber roto la ventana.", isFlawed = false),
                RawItem("Habría que averiguar quién la rompió realmente.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_amistad_4", SeedIslands.AMISTAD, SEQUENCE,
            "Ordena los pasos para reconciliarte con un amigo después de una pelea.",
            "Reconciliarse suele empezar con un gesto de acercamiento y termina en escuchar, no en tener la razón.",
            3,
            listOf(
                RawItem("Tú y tu amigo dejan de hablarse después de una discusión.", correctPosition = 0),
                RawItem("Uno de los dos decide dar el primer paso y buscar hablar.", correctPosition = 1),
                RawItem("Ambos explican cómo se sintieron, sin gritar.", correctPosition = 2),
                RawItem("Deciden seguir siendo amigos, aunque no estén de acuerdo en todo.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_amistad_5", SeedIslands.AMISTAD, MATCH,
            "Conecta cada gesto con lo que muestra sobre una amistad.",
            "Los gestos concretos suelen mostrar más sobre una amistad que las palabras.",
            4,
            listOf(
                RawItem("Un amigo te guarda un secreto importante.", pairKey = "p1", role = "PREMISE"),
                RawItem("Muestra confianza.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Un amigo te felicita aunque él haya perdido el juego.", pairKey = "p2", role = "PREMISE"),
                RawItem("Muestra deportividad y generosidad.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Un amigo cambia de planes para ayudarte en una emergencia.", pairKey = "p3", role = "PREMISE"),
                RawItem("Muestra un compromiso real.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_amistad_6", SeedIslands.AMISTAD, SPOT_FLAW,
            "Este razonamiento sobre una amistad tiene un fallo. Encuéntralo.",
            "El fallo es generalizar el futuro de una amistad a partir de un solo momento difícil.",
            5,
            listOf(
                RawItem("Marta y Sofía se pelearon una vez hace un año.", isFlawed = false),
                RawItem("Por lo tanto, ya no pueden ser buenas amigas nunca más.", isFlawed = true),
                RawItem("Una pelea pasada no define toda una amistad.", isFlawed = false),
                RawItem("Muchas amistades se reconstruyen después de un conflicto.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_libertad_4", SeedIslands.LIBERTAD, SEQUENCE,
            "Ordena el razonamiento sobre por qué existe un límite de volumen en audífonos compartidos.",
            "Un límite razonable parte de proteger algo real, no de prohibir por prohibir.",
            3,
            listOf(
                RawItem("Usar el volumen muy alto en audífonos compartidos puede dañar el oído.", correctPosition = 0),
                RawItem("Varias personas usan los mismos audífonos en la clase.", correctPosition = 1),
                RawItem("Por eso se establece un límite razonable de volumen.", correctPosition = 2),
                RawItem("Ese límite protege la salud de todos los que los usan.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_libertad_5", SeedIslands.LIBERTAD, MATCH,
            "Conecta cada situación con el tipo de libertad que está en juego.",
            "La libertad tiene varias formas: elegir, no participar, y expresar lo que piensas.",
            4,
            listOf(
                RawItem("Elegir qué libro leer en tiempo libre.", pairKey = "p1", role = "PREMISE"),
                RawItem("Libertad de elección personal.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Decidir no participar en una actividad opcional.", pairKey = "p2", role = "PREMISE"),
                RawItem("Libertad de no participar.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Expresar una opinión distinta en un debate de clase.", pairKey = "p3", role = "PREMISE"),
                RawItem("Libertad de expresión.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_libertad_6", SeedIslands.LIBERTAD, SPOT_FLAW,
            "Este razonamiento sobre las normas tiene un fallo. Encuéntralo.",
            "El fallo es asumir que una norma es arbitraria solo porque no se conoce su razón.",
            5,
            listOf(
                RawItem("Una escuela puso una norma nueva sobre el uso del patio.", isFlawed = false),
                RawItem("Por lo tanto, esa norma no tiene ninguna razón detrás.", isFlawed = true),
                RawItem("No conocer la razón de una norma no significa que no exista.", isFlawed = false),
                RawItem("Preguntar por qué existe ayuda a entenderla mejor.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_resp_4", SeedIslands.RESPONSABILIDAD, SEQUENCE,
            "Ordena los pasos para atender una responsabilidad que se te olvidó.",
            "Ser responsable no es no fallar nunca, sino reaccionar bien cuando ocurre un olvido.",
            3,
            listOf(
                RawItem("Te comprometiste a regar las plantas de la clase toda la semana.", correctPosition = 0),
                RawItem("Un día se te olvida por completo.", correctPosition = 1),
                RawItem("Al notarlo, riegas las plantas apenas puedes.", correctPosition = 2),
                RawItem("Piensas en una forma de recordarlo mejor la próxima vez.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_resp_5", SeedIslands.RESPONSABILIDAD, MATCH,
            "Conecta cada situación con el nivel de responsabilidad que le corresponde.",
            "La responsabilidad cambia según si sabías, si decidiste algo, o si fue un accidente.",
            4,
            listOf(
                RawItem("Olvidas avisar algo importante por estar distraído.", pairKey = "p1", role = "PREMISE"),
                RawItem("Responsabilidad por descuido: se puede mejorar prestando más atención.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Ayudas a un amigo aunque nadie te lo pidió.", pairKey = "p2", role = "PREMISE"),
                RawItem("Responsabilidad voluntaria: decides asumirla tú mismo.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Te piden explicar algo que no sabías que debías hacer.", pairKey = "p3", role = "PREMISE"),
                RawItem("Poca responsabilidad: no tenías la información necesaria.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_resp_6", SeedIslands.RESPONSABILIDAD, SPOT_FLAW,
            "Este razonamiento sobre una promesa tiene un fallo. Encuéntralo.",
            "El fallo es ignorar el contexto: no toda promesa incumplida por una razón válida es irresponsabilidad.",
            5,
            listOf(
                RawItem("Prometiste ayudar a armar un proyecto grupal el sábado.", isFlawed = false),
                RawItem("Se te presentó una emergencia familiar real ese día.", isFlawed = false),
                RawItem("Por lo tanto, no cumplir la promesa te hace irresponsable de todas formas.", isFlawed = true),
                RawItem("Una razón real y explicada cambia cómo se evalúa el incumplimiento.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_conv_4", SeedIslands.CONVIVENCIA, SEQUENCE,
            "Ordena los pasos para incluir a alguien que se siente fuera del grupo.",
            "Incluir a alguien suele empezar con un gesto pequeño, no con un cambio grande de golpe.",
            3,
            listOf(
                RawItem("Notas que un compañero casi siempre está solo en el recreo.", correctPosition = 0),
                RawItem("Te acercas y lo invitas a unirse a lo que están haciendo.", correctPosition = 1),
                RawItem("Él se une, aunque al principio esté algo callado.", correctPosition = 2),
                RawItem("Con el tiempo, se siente más parte del grupo.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_conv_5", SeedIslands.CONVIVENCIA, MATCH,
            "Conecta cada actitud con su efecto probable en la convivencia.",
            "Pequeñas actitudes diarias construyen, o debilitan, la convivencia de un grupo.",
            4,
            listOf(
                RawItem("Escuchar con atención cuando alguien cuenta algo importante.", pairKey = "p1", role = "PREMISE"),
                RawItem("Fortalece la confianza del grupo.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Interrumpir constantemente a los demás al hablar.", pairKey = "p2", role = "PREMISE"),
                RawItem("Genera frustración en el grupo.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Reconocer cuando cometiste un error frente al grupo.", pairKey = "p3", role = "PREMISE"),
                RawItem("Genera respeto y confianza.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_conv_6", SeedIslands.CONVIVENCIA, SPOT_FLAW,
            "Este razonamiento sobre una costumbre distinta tiene un fallo. Encuéntralo.",
            "El fallo es asumir que tu propia costumbre es la única forma correcta.",
            5,
            listOf(
                RawItem("Un compañero nuevo saluda de una forma distinta a la tuya.", isFlawed = false),
                RawItem("Por lo tanto, su forma de saludar es la incorrecta.", isFlawed = true),
                RawItem("Existen muchas formas válidas de saludar según cada cultura.", isFlawed = false),
                RawItem("Que algo sea distinto no significa que esté mal.", isFlawed = false)
            )
        ),
        // --- Día 3 (orden 6-8) ---
        RawChallenge(
            "logic_verdad_7", SeedIslands.VERDAD, SEQUENCE,
            "Ordena el razonamiento sobre confirmar un dato histórico.",
            "Confirmar un dato en más de una fuente aumenta la confianza en que es verdadero.",
            6,
            listOf(
                RawItem("Lees en un libro una fecha histórica poco común.", correctPosition = 0),
                RawItem("Buscas esa misma fecha en otro libro distinto.", correctPosition = 1),
                RawItem("Ambos libros coinciden en la fecha.", correctPosition = 2),
                RawItem("Confías más en esa fecha al estar confirmada por dos fuentes.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_verdad_8", SeedIslands.VERDAD, MATCH,
            "Conecta cada afirmación con el tipo de prueba que la respalda.",
            "No todas las 'pruebas' tienen el mismo peso: observar, medir y escuchar rumores no son lo mismo.",
            7,
            listOf(
                RawItem("Vi el eclipse con mis propios ojos.", pairKey = "p1", role = "PREMISE"),
                RawItem("Es una observación directa.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Los científicos midieron la temperatura con termómetros durante un mes.", pairKey = "p2", role = "PREMISE"),
                RawItem("Es una medición sistemática.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Un amigo dijo que escuchó que iba a llover.", pairKey = "p3", role = "PREMISE"),
                RawItem("Es un rumor de segunda mano.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_verdad_9", SeedIslands.VERDAD, SPOT_FLAW,
            "Este razonamiento sobre la suerte tiene un fallo. Encuéntralo.",
            "El fallo es una falsa causa: que dos cosas coincidan no significa que una cause la otra.",
            8,
            listOf(
                RawItem("Un jugador ganó dos veces seguidas usando la misma camiseta.", isFlawed = false),
                RawItem("Por lo tanto, la camiseta es la razón de que gane.", isFlawed = true),
                RawItem("Ganar dos veces seguidas puede deberse a muchas otras razones.", isFlawed = false),
                RawItem("Para probarlo, habría que comparar con y sin la camiseta muchas veces.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_justicia_7", SeedIslands.JUSTICIA, SEQUENCE,
            "Ordena los pasos para crear una regla nueva de forma justa en el salón.",
            "Una regla justa nace de escuchar el problema real, no de imponerla sin consultar.",
            6,
            listOf(
                RawItem("Varios compañeros se quejan de un mismo problema en el salón.", correctPosition = 0),
                RawItem("Se junta al grupo para escuchar todas las opiniones.", correctPosition = 1),
                RawItem("Se propone una regla que responde al problema.", correctPosition = 2),
                RawItem("Se revisa después si la regla realmente ayudó.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_justicia_8", SeedIslands.JUSTICIA, MATCH,
            "Conecta cada decisión con si parece justa o injusta.",
            "Evaluar si algo es justo requiere mirar tanto la regla como las circunstancias reales.",
            7,
            listOf(
                RawItem("Un profesor califica igual dos trabajos con el mismo nivel de esfuerzo.", pairKey = "p1", role = "PREMISE"),
                RawItem("Parece justa: mismo esfuerzo, mismo trato.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Se descalifica a un equipo por llegar un minuto tarde por un imprevisto real.", pairKey = "p2", role = "PREMISE"),
                RawItem("Parece injusta: no considera la causa.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Se premia solo al capitán por el logro de todo el equipo.", pairKey = "p3", role = "PREMISE"),
                RawItem("Parece injusta: ignora el esfuerzo del resto.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_justicia_9", SeedIslands.JUSTICIA, SPOT_FLAW,
            "Este razonamiento sobre un premio tiene un fallo. Encuéntralo.",
            "El fallo es confundir rapidez con calidad.",
            8,
            listOf(
                RawItem("Ana entregó su proyecto un día antes que Luis.", isFlawed = false),
                RawItem("Por lo tanto, el proyecto de Ana es mejor que el de Luis.", isFlawed = true),
                RawItem("Entregar antes no dice nada sobre la calidad del trabajo.", isFlawed = false),
                RawItem("Habría que comparar el contenido de ambos proyectos.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_amistad_7", SeedIslands.AMISTAD, SEQUENCE,
            "Ordena los pasos para hacer un nuevo amigo en un lugar nuevo.",
            "Las amistades suelen empezar con un pequeño paso, no con una conexión instantánea.",
            6,
            listOf(
                RawItem("Llegas a un lugar donde no conoces a nadie.", correctPosition = 0),
                RawItem("Te acercas a alguien y empiezas una conversación simple.", correctPosition = 1),
                RawItem("Encuentran algo en común de qué hablar.", correctPosition = 2),
                RawItem("Empiezan a pasar tiempo juntos con más confianza.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_amistad_8", SeedIslands.AMISTAD, MATCH,
            "Conecta cada situación con el tipo de apoyo que representa entre amigos.",
            "El apoyo entre amigos puede tomar formas distintas, todas igual de valiosas.",
            7,
            listOf(
                RawItem("Un amigo te escucha sin intentar arreglar el problema, solo acompañarte.", pairKey = "p1", role = "PREMISE"),
                RawItem("Apoyo emocional.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Un amigo te explica un tema que no entendiste en clase.", pairKey = "p2", role = "PREMISE"),
                RawItem("Apoyo práctico.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Un amigo te anima a intentarlo de nuevo después de un fracaso.", pairKey = "p3", role = "PREMISE"),
                RawItem("Apoyo motivacional.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_amistad_9", SeedIslands.AMISTAD, SPOT_FLAW,
            "Este razonamiento sobre hacer amigos tiene un fallo. Encuéntralo.",
            "El fallo es confundir timidez con falta de interés en la amistad.",
            8,
            listOf(
                RawItem("Pedro es muy callado en clase.", isFlawed = false),
                RawItem("Por lo tanto, Pedro no quiere tener amigos.", isFlawed = true),
                RawItem("Ser callado no significa no querer compañía.", isFlawed = false),
                RawItem("Muchas personas tímidas sí desean tener amigos cercanos.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_libertad_7", SeedIslands.LIBERTAD, SEQUENCE,
            "Ordena los pasos para negociar un límite justo con un adulto.",
            "Negociar un límite empieza por explicar y escuchar, no por imponer tu postura.",
            6,
            listOf(
                RawItem("Sientes que un límite que te pusieron es demasiado estricto.", correctPosition = 0),
                RawItem("Le explicas con calma por qué crees que debería cambiar.", correctPosition = 1),
                RawItem("Escuchas también las razones del adulto.", correctPosition = 2),
                RawItem("Juntos encuentran un punto medio que ambos aceptan.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_libertad_8", SeedIslands.LIBERTAD, MATCH,
            "Conecta cada límite con a quién protege principalmente.",
            "Algunos límites cuidan sobre todo de ti, y otros cuidan sobre todo del espacio compartido.",
            7,
            listOf(
                RawItem("Usar casco al andar en bicicleta.", pairKey = "p1", role = "PREMISE"),
                RawItem("Protege principalmente a ti mismo.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("No usar el teléfono muy alto en la biblioteca.", pairKey = "p2", role = "PREMISE"),
                RawItem("Protege principalmente a los demás.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Ponerte el cinturón de seguridad en el auto.", pairKey = "p3", role = "PREMISE"),
                RawItem("Protege principalmente a ti mismo.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_libertad_9", SeedIslands.LIBERTAD, SPOT_FLAW,
            "Este razonamiento sobre la libertad tiene un fallo. Encuéntralo.",
            "El fallo es exagerar: un límite puntual no equivale a perder toda la libertad.",
            8,
            listOf(
                RawItem("Un niño quiere jugar videojuegos toda la noche sin dormir.", isFlawed = false),
                RawItem("Por lo tanto, cualquier límite a eso le quita toda su libertad.", isFlawed = true),
                RawItem("Un límite sobre una sola actividad no elimina toda la libertad de una persona.", isFlawed = false),
                RawItem("Se puede seguir siendo libre incluso con algunos límites razonables.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_resp_7", SeedIslands.RESPONSABILIDAD, SEQUENCE,
            "Ordena los pasos para reparar un daño que causaste sin querer.",
            "Reparar un daño accidental sigue un orden: contarlo, ofrecer solución, y aprender de eso.",
            6,
            listOf(
                RawItem("Sin querer rompes un juguete de un amigo mientras juegan.", correctPosition = 0),
                RawItem("Le cuentas lo que pasó apenas ocurre.", correctPosition = 1),
                RawItem("Le ofreces ayudar a repararlo o reemplazarlo.", correctPosition = 2),
                RawItem("Tienes más cuidado la próxima vez que juegan con eso.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_resp_8", SeedIslands.RESPONSABILIDAD, MATCH,
            "Conecta cada acción con el tipo de responsabilidad que muestra.",
            "La responsabilidad puede ser contigo mismo, con un grupo, o compartida en familia.",
            7,
            listOf(
                RawItem("Revisas tu propio trabajo antes de entregarlo.", pairKey = "p1", role = "PREMISE"),
                RawItem("Responsabilidad personal.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Recuerdas a un compañero olvidadizo que tiene una tarea pendiente.", pairKey = "p2", role = "PREMISE"),
                RawItem("Responsabilidad hacia el grupo.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Cuidas una mascota que tu familia decidió adoptar.", pairKey = "p3", role = "PREMISE"),
                RawItem("Responsabilidad compartida en casa.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_resp_9", SeedIslands.RESPONSABILIDAD, SPOT_FLAW,
            "Este razonamiento sobre un accidente tiene un fallo. Encuéntralo.",
            "El fallo es cargar toda la culpa a la persona sin considerar causas externas reales.",
            8,
            listOf(
                RawItem("Un niño se resbaló en el patio mojado por la lluvia.", isFlawed = false),
                RawItem("Por lo tanto, es completamente su culpa por no tener cuidado.", isFlawed = true),
                RawItem("El piso mojado por la lluvia es una causa externa, no solo un descuido.", isFlawed = false),
                RawItem("Evaluar bien un accidente requiere mirar todas las causas posibles.", isFlawed = false)
            )
        ),
        RawChallenge(
            "logic_conv_7", SeedIslands.CONVIVENCIA, SEQUENCE,
            "Ordena los pasos para resolver un desacuerdo de convivencia en un grupo grande.",
            "Resolver un desacuerdo grupal funciona mejor escuchando a todos antes de decidir.",
            6,
            listOf(
                RawItem("El grupo no logra ponerse de acuerdo en una actividad para el recreo.", correctPosition = 0),
                RawItem("Cada quien propone brevemente su idea.", correctPosition = 1),
                RawItem("Votan o buscan una idea que combine varias propuestas.", correctPosition = 2),
                RawItem("Todos aceptan seguir la decisión tomada en conjunto.", correctPosition = 3)
            )
        ),
        RawChallenge(
            "logic_conv_8", SeedIslands.CONVIVENCIA, MATCH,
            "Conecta cada situación con la forma de convivencia que representa mejor.",
            "Convivir bien puede lograrse de varias formas, según lo que el grupo necesite.",
            7,
            listOf(
                RawItem("Un grupo turna quién elige la música en un viaje.", pairKey = "p1", role = "PREMISE"),
                RawItem("Convivencia por turnos equitativos.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Un grupo respeta el silencio de quien necesita estudiar.", pairKey = "p2", role = "PREMISE"),
                RawItem("Convivencia por respeto al espacio de otros.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Un grupo celebra las costumbres de cada integrante en distintas fechas.", pairKey = "p3", role = "PREMISE"),
                RawItem("Convivencia por inclusión de la diversidad.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_conv_9", SeedIslands.CONVIVENCIA, SPOT_FLAW,
            "Este razonamiento sobre un grupo nuevo tiene un fallo. Encuéntralo.",
            "El fallo es confundir una dificultad de idioma con falta de interés en convivir.",
            8,
            listOf(
                RawItem("Un estudiante de otro país se une a la clase a mitad de año.", isFlawed = false),
                RawItem("Al principio habla poco porque aún aprende el idioma.", isFlawed = false),
                RawItem("Por lo tanto, no le interesa hacer amigos en la clase.", isFlawed = true),
                RawItem("Hablar poco un idioma nuevo no significa falta de interés social.", isFlawed = false)
            )
        )
    )

    val challenges: List<LogicChallengeEntity> = raw.map {
        LogicChallengeEntity(it.id, it.islandId, it.type, it.prompt, it.explanation, it.order)
    }

    val items: List<LogicChallengeItemEntity> = raw.flatMap { c ->
        c.items.mapIndexed { i, item ->
            LogicChallengeItemEntity(
                id = "${c.id}_item${i + 1}",
                challengeId = c.id,
                text = item.text,
                correctPosition = item.correctPosition,
                pairKey = item.pairKey,
                role = item.role,
                isFlawed = item.isFlawed,
                displayOrder = i
            )
        }
    }
}

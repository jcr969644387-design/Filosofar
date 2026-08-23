package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.entity.LogicChallengeEntity
import com.educalab.filosofar.data.local.entity.LogicChallengeItemEntity

/**
 * 24 retos de lógica real (4 por isla): SEQUENCE (ordenar un razonamiento),
 * MATCH (conectar premisas con su conclusión, x2 por isla) y SPOT_FLAW
 * (encontrar la pieza que rompe el razonamiento).
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
        RawChallenge(
            "logic_verdad_4", SeedIslands.VERDAD, MATCH,
            "Conecta cada pista con lo que realmente puedes concluir de ella.",
            "Una buena conclusión se queda cerca de lo que la pista permite pensar, sin inventar más de la cuenta.",
            3,
            listOf(
                RawItem("Hay un charco en la acera y el cielo está nublado.", pairKey = "p1", role = "PREMISE"),
                RawItem("Es probable que haya llovido hace poco.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Un amigo bosteza varias veces seguidas por la tarde.", pairKey = "p2", role = "PREMISE"),
                RawItem("Es probable que tenga sueño.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Las hojas de un árbol cambian de color.", pairKey = "p3", role = "PREMISE"),
                RawItem("Es una señal de que las estaciones están cambiando.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_justicia_4", SeedIslands.JUSTICIA, MATCH,
            "Conecta cada regla con la razón que la hace justa.",
            "Una regla suele ser justa cuando protege a todos por igual o evita castigar sin motivo real.",
            3,
            listOf(
                RawItem("En una fila, se atiende a quien llegó primero.", pairKey = "p1", role = "PREMISE"),
                RawItem("Da la misma oportunidad a todos según el orden de llegada.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Se explica una norma nueva antes de aplicar sanciones por ella.", pairKey = "p2", role = "PREMISE"),
                RawItem("Nadie debería ser sancionado por algo que no sabía que existía.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Se escuchan las dos versiones antes de decidir quién tiene razón.", pairKey = "p3", role = "PREMISE"),
                RawItem("Decidir sin escuchar a ambas partes puede ser injusto.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_amistad_4", SeedIslands.AMISTAD, MATCH,
            "Conecta cada situación con lo que muestra sobre una amistad.",
            "Las amistades sanas suelen mostrarse en gestos concretos: generosidad, equilibrio y confianza.",
            3,
            listOf(
                RawItem("Un amigo te felicita de verdad cuando ganas algo que él también quería.", pairKey = "p1", role = "PREMISE"),
                RawItem("Muestra generosidad, no solo compañía.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Un amigo te cuenta sus problemas, pero nunca pregunta por los tuyos.", pairKey = "p2", role = "PREMISE"),
                RawItem("Puede ser una amistad poco equilibrada.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Dos amigos pueden estar en silencio juntos sin sentirse incómodos.", pairKey = "p3", role = "PREMISE"),
                RawItem("Muestra una confianza genuina entre ellos.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_libertad_4", SeedIslands.LIBERTAD, MATCH,
            "Conecta cada límite con el motivo real que lo justifica.",
            "Los buenos límites suelen proteger a alguien concreto, no solo prohibir por prohibir.",
            3,
            listOf(
                RawItem("No se permite correr dentro del aula.", pairKey = "p1", role = "PREMISE"),
                RawItem("Evita accidentes en un espacio pequeño y lleno de gente.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Hay una edad mínima recomendada para ciertas películas.", pairKey = "p2", role = "PREMISE"),
                RawItem("Protege a quienes aún no están listos para ese contenido.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Se pide permiso antes de tomar prestado algo de otra persona.", pairKey = "p3", role = "PREMISE"),
                RawItem("Respeta la decisión del dueño sobre sus propias cosas.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_resp_4", SeedIslands.RESPONSABILIDAD, MATCH,
            "Conecta cada acción con la forma de responsabilidad que muestra.",
            "Ser responsable se ve en gestos concretos: avisar, cumplir lo prometido y ayudar sin burlarse.",
            3,
            listOf(
                RawItem("Avisas a un adulto apenas ves que algo se rompió por accidente.", pairKey = "p1", role = "PREMISE"),
                RawItem("Muestra responsabilidad inmediata, aunque no haya sido a propósito.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Prometes ayudar con una tarea y luego se te olvida por completo.", pairKey = "p2", role = "PREMISE"),
                RawItem("Muestra que cumplir promesas también es parte de ser responsable.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Ayudas a un compañero a corregir un error, sin burlarte de él.", pairKey = "p3", role = "PREMISE"),
                RawItem("Muestra responsabilidad compartida dentro de un grupo.", pairKey = "p3", role = "CONCLUSION")
            )
        ),
        RawChallenge(
            "logic_conv_4", SeedIslands.CONVIVENCIA, MATCH,
            "Conecta cada situación con su efecto en la convivencia del grupo.",
            "Algunas acciones acercan al grupo aunque cuesten un poco de esfuerzo, y otras lo alejan sin que sea la intención.",
            3,
            listOf(
                RawItem("Un grupo decide turnarse para elegir el juego del recreo.", pairKey = "p1", role = "PREMISE"),
                RawItem("Ayuda a que todos se sientan incluidos con el tiempo.", pairKey = "p1", role = "CONCLUSION"),
                RawItem("Alguien habla muy alto mientras otros intentan concentrarse.", pairKey = "p2", role = "PREMISE"),
                RawItem("Puede afectar la convivencia sin que sea su intención.", pairKey = "p2", role = "CONCLUSION"),
                RawItem("Un compañero traduce lo que dice alguien que habla otro idioma.", pairKey = "p3", role = "PREMISE"),
                RawItem("Ayuda a que todos puedan participar de la conversación.", pairKey = "p3", role = "CONCLUSION")
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

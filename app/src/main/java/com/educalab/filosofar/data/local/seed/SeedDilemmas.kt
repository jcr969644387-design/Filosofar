package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.entity.DilemmaEntity
import com.educalab.filosofar.data.local.entity.DilemmaOptionEntity

/**
 * 12 dilemas (2 por isla), cada uno con 3 opciones. Alcance reducido y
 * documentado desde los 30 del pliego original (ver BUILD_REPORT.md).
 */
object SeedDilemmas {

    data class RawOption(val label: String, val consequence: String, val lumi: String, val nox: String)
    data class RawDilemma(
        val id: String,
        val islandId: String,
        val title: String,
        val scenario: String,
        val order: Int,
        val options: List<RawOption>
    )

    private val raw = listOf(
        RawDilemma(
            "dil_verdad_1", SeedIslands.VERDAD, "El examen encontrado",
            "Antes del examen de mates, ves por accidente una copia con las respuestas sobre la mesa del profe.",
            0,
            listOf(
                RawOption("La leo rápido y la uso", "Sacas mejor nota, pero no sabes si de verdad aprendiste.", "Lumi piensa: usarla no te ayuda a entender los problemas, solo a pasar el examen.", "Nox piensa: si la encontraste sin buscarla, no planeaste hacer trampa."),
                RawOption("Se la devuelvo al profe sin mirarla", "Pierdes la ventaja, pero el examen mide lo que realmente sabes.", "Lumi piensa: así la nota sí cuenta como algo tuyo de verdad.", "Nox piensa: también está bien sentir la tentación; lo importante es qué haces con ella."),
                RawOption("Se lo cuento a un compañero para que decida él", "Compartes la decisión, pero también el problema.", "Lumi piensa: cada quien debería decidir esto por sí mismo.", "Nox piensa: pedir opinión no es malo, a veces ayuda a pensar mejor.")
            )
        ),
        RawDilemma(
            "dil_verdad_2", SeedIslands.VERDAD, "La noticia que corre rápido",
            "Un compañero comparte en el grupo del cole algo que \"escuchó decir\" sobre otra persona, pero no está seguro de que sea cierto.",
            1,
            listOf(
                RawOption("Lo reenvío, total ya lo sabe medio curso", "El rumor crece más rápido de lo que se puede aclarar.", "Lumi piensa: que algo se repita mucho no lo convierte en verdad.", "Nox piensa: a veces reenviamos sin pensar, no por maldad."),
                RawOption("Pregunto a la persona directamente antes de repetirlo", "Tardas más, pero evitas hacer daño con algo que podría ser falso.", "Lumi piensa: preguntar primero es lo más justo con la verdad.", "Nox piensa: no siempre es fácil preguntar directamente, puede dar vergüenza."),
                RawOption("No lo comparto ni lo comento con nadie", "El rumor no crece por tu parte, aunque igual siga circulando.", "Lumi piensa: no repetir algo dudoso ya es una forma de cuidar la verdad.", "Nox piensa: quedarse callado no siempre resuelve el problema de fondo.")
            )
        ),
        RawDilemma(
            "dil_justicia_1", SeedIslands.JUSTICIA, "El equipo incompleto",
            "En educación física hay que formar equipos y a un compañero, que juega peor, nadie lo quiere elegir.",
            0,
            listOf(
                RawOption("Lo elijo en mi equipo aunque podamos perder", "Tu equipo puede perder puntos, pero nadie se queda fuera.", "Lumi piensa: incluir a todos es parte de jugar limpio, no solo las reglas del juego.", "Nox piensa: ganar también importa, y eso no te hace mala persona."),
                RawOption("Dejo que el profe decida los equipos por sorteo", "Nadie elige directamente, así que nadie se siente señalado.", "Lumi piensa: el azar puede ser una forma justa de repartir cuando duele elegir.", "Nox piensa: el sorteo evita el problema, pero no lo resuelve de verdad."),
                RawOption("Propongo cambiar el juego para que todos puedan participar mejor", "Se necesita más tiempo para explicarlo, pero cambia el problema de raíz.", "Lumi piensa: cambiar las reglas para incluir a todos es pensar más allá de ganar.", "Nox piensa: no siempre hay tiempo o permiso para cambiar el juego.")
            )
        ),
        RawDilemma(
            "dil_justicia_2", SeedIslands.JUSTICIA, "El reparto del premio",
            "Tu grupo gana un concurso de ciencias, pero uno de los cuatro integrantes apenas ayudó porque estuvo enfermo casi todo el proyecto.",
            1,
            listOf(
                RawOption("Reparto el premio en cuatro partes iguales", "Es sencillo y evita discusiones, aunque el esfuerzo no fue igual.", "Lumi piensa: formar parte del equipo también cuenta, aunque no puedas hacer todo.", "Nox piensa: repartir igual sin mirar el esfuerzo puede no sentirse justo para los demás."),
                RawOption("Reparto más para quienes trabajaron más horas", "Reconoces el esfuerzo, pero hay que estar de acuerdo en cómo medirlo.", "Lumi piensa: medir el esfuerzo real puede ser justo, si se hace con cuidado.", "Nox piensa: es difícil medir el esfuerzo de alguien que estuvo enfermo sin culparlo."),
                RawOption("Hablamos los cuatro y decidimos juntos", "Tarda más, pero todos entienden y aceptan el resultado.", "Lumi piensa: decidir juntos suele producir acuerdos más justos que decidir por otros.", "Nox piensa: hablarlo puede ser incómodo si alguien se siente culpable por estar enfermo.")
            )
        ),
        RawDilemma(
            "dil_amistad_1", SeedIslands.AMISTAD, "El secreto pesado",
            "Tu mejor amigo te cuenta en secreto que está teniendo problemas en casa y te pide que no se lo digas a nadie, ni a un adulto.",
            0,
            listOf(
                RawOption("Guardo el secreto tal como me lo pidió", "Mantienes su confianza, pero cargas solo con algo difícil.", "Lumi piensa: la confianza de un amigo es importante y hay que cuidarla.", "Nox piensa: hay secretos demasiado grandes para guardarlos solo."),
                RawOption("Le digo que necesito contárselo a un adulto de confianza", "Puede enfadarse al principio, pero recibe ayuda real.", "Lumi piensa: a veces cuidar a un amigo significa pedir ayuda, aunque él no quiera al principio.", "Nox piensa: romper una promesa duele, aunque sea por una buena razón."),
                RawOption("Le ayudo a que él mismo se lo cuente a un adulto", "Lleva más tiempo, pero él decide cómo y cuándo contarlo.", "Lumi piensa: acompañarlo a decidir respeta su confianza y su seguridad a la vez.", "Nox piensa: puede que él no esté listo todavía, y eso también hay que respetarlo.")
            )
        ),
        RawDilemma(
            "dil_amistad_2", SeedIslands.AMISTAD, "El nuevo grupo",
            "Haces un nuevo amigo que no se lleva bien con tu grupo de amigos de siempre.",
            1,
            listOf(
                RawOption("Sigo viendo a ambos por separado", "Mantienes las dos amistades, aunque a veces sea complicado organizarte.", "Lumi piensa: no tienes por qué elegir entre amistades que no se llevan bien entre sí.", "Nox piensa: mantener todo separado puede cansar a la larga."),
                RawOption("Intento que mi grupo y mi nuevo amigo se conozcan mejor", "Puede salir bien o generar más tensión, pero lo intentas de frente.", "Lumi piensa: dar una oportunidad a que se conozcan es más honesto que evitarlo.", "Nox piensa: no siempre depende de ti que dos personas se lleven bien."),
                RawOption("Dejo de ver al nuevo amigo para no complicar las cosas", "Evitas el conflicto, pero pierdes una amistad que estaba empezando.", "Lumi piensa: alejarte de alguien solo por encajar puede no ser justo para esa persona.", "Nox piensa: a veces evitar un conflicto también es una forma válida de cuidarte.")
            )
        ),
        RawDilemma(
            "dil_libertad_1", SeedIslands.LIBERTAD, "La tarde libre",
            "Tus padres te dan una tarde libre para hacer lo que quieras, pero tienes deberes pendientes para el día siguiente.",
            0,
            listOf(
                RawOption("Uso toda la tarde para jugar", "Disfrutas ahora, pero después tendrás menos tiempo para los deberes.", "Lumi piensa: ser libre incluye poder elegir mal a veces y aprender de eso.", "Nox piensa: elegir sin pensar en después puede quitarte libertad mañana."),
                RawOption("Reparto la tarde entre deberes y juego", "Avanzas en ambas cosas, aunque ninguna al cien por cien.", "Lumi piensa: repartir el tiempo también es una forma de ser libre y responsable a la vez.", "Nox piensa: repartir puede hacer que ninguna de las dos cosas se disfrute del todo."),
                RawOption("Hago primero los deberes y luego juego tranquilo", "Empiezas más despacio, pero disfrutas el resto sin preocupaciones.", "Lumi piensa: terminar antes lo importante te da una libertad más tranquila después.", "Nox piensa: no siempre es fácil concentrarse en deberes cuando lo que quieres es jugar ya.")
            )
        ),
        RawDilemma(
            "dil_libertad_2", SeedIslands.LIBERTAD, "El grupo de música",
            "En el recreo, un grupo grande pone música con el móvil a un volumen que a ti te molesta mientras lees.",
            1,
            listOf(
                RawOption("Les pido que bajen el volumen", "Puede que accedan o que se molesten, pero expresas lo que sientes.", "Lumi piensa: tu libertad para leer tranquilo también importa, no solo la suya para escuchar música.", "Nox piensa: pedirlo directamente puede sentirse incómodo si son muchos."),
                RawOption("Me cambio de sitio a leer", "Resuelves tu molestia sin entrar en conflicto con nadie.", "Lumi piensa: cambiar de sitio también es una forma de cuidar tu propio espacio.", "Nox piensa: cambiarte de sitio no cambia la situación para la próxima vez."),
                RawOption("Aguanto la música porque son más y no quiero problemas", "Evitas el conflicto, pero sigues sintiéndote incómodo.", "Lumi piensa: no siempre hace falta un conflicto grande para decir lo que sientes.", "Nox piensa: a veces es más fácil dejarlo pasar una vez, sin que sea siempre así.")
            )
        ),
        RawDilemma(
            "dil_resp_1", SeedIslands.RESPONSABILIDAD, "La planta olvidada",
            "Te ofreciste a cuidar la planta de la clase durante las vacaciones, pero te olvidaste dos semanas y se está secando.",
            0,
            listOf(
                RawOption("La riego ahora y no digo nada", "La planta puede recuperarse, pero nadie sabrá lo que pasó.", "Lumi piensa: arreglar el problema ya es un paso importante, aunque no sea perfecto.", "Nox piensa: no contarlo puede hacer que se repita el olvido sin que nadie lo note."),
                RawOption("Le cuento a la clase lo que pasó y pido ayuda para cuidarla entre todos", "Es más incómodo al principio, pero la planta tiene más cuidadores.", "Lumi piensa: reconocer el olvido y pedir ayuda es una forma valiente de ser responsable.", "Nox piensa: contarlo puede darte vergüenza, y eso también es normal sentirlo."),
                RawOption("Dejo de encargarme de la planta y que otro se ofrezca", "Sueltas la responsabilidad, pero la planta sigue necesitando cuidados.", "Lumi piensa: reconocer que necesitas ayuda para seguir cuidando algo no es fallar del todo.", "Nox piensa: dejarlo del todo puede sentirse como una salida fácil.")
            )
        ),
        RawDilemma(
            "dil_resp_2", SeedIslands.RESPONSABILIDAD, "El mensaje enviado por error",
            "Sin querer, le envías a todo el grupo de la clase un mensaje que era solo para un amigo, y en él criticabas a otro compañero.",
            1,
            listOf(
                RawOption("Borro el mensaje rápido y espero que nadie lo haya leído", "Puede que funcione, pero no siempre borrar a tiempo es posible.", "Lumi piensa: borrarlo no borra que ya lo pensaste y lo escribiste.", "Nox piensa: fue un error de un clic, no significa que seas mala persona."),
                RawOption("Hablo con el compañero criticado y le pido disculpas", "Es incómodo, pero repara la relación de forma directa.", "Lumi piensa: disculparse directamente muestra que te haces cargo de lo que pasó.", "Nox piensa: puede que él necesite tiempo antes de aceptar la disculpa, y eso está bien."),
                RawOption("No digo nada y espero que se olvide con el tiempo", "El malestar puede quedarse sin resolverse entre ustedes.", "Lumi piensa: los problemas que no se hablan rara vez desaparecen del todo.", "Nox piensa: a veces dejar pasar un poco de tiempo también ayuda a pensar mejor cómo hablarlo.")
            )
        ),
        RawDilemma(
            "dil_conv_1", SeedIslands.CONVIVENCIA, "La costumbre distinta",
            "Un compañero nuevo, de otro país, come de una forma distinta a la tuya y dos niños se ríen de él en el comedor.",
            0,
            listOf(
                RawOption("Me río también para no destacar", "Encajas con el grupo, pero él se siente peor.", "Lumi piensa: reírse de algo distinto no lo hace gracioso, solo lo hace doler.", "Nox piensa: a veces reímos por nervios, sin pensar bien en el efecto que tiene."),
                RawOption("Me siento con él y le pregunto sobre su comida", "Puede sentirse raro al principio, pero él se siente acompañado.", "Lumi piensa: interesarte de verdad por lo distinto es la mejor forma de entenderlo.", "Nox piensa: no siempre es fácil acercarte a alguien nuevo, aunque quieras hacerlo."),
                RawOption("No me río, pero tampoco digo nada", "No participas en la burla, aunque tampoco la frenas.", "Lumi piensa: no burlarse ya es un paso, aunque se puede hacer más.", "Nox piensa: quedarse en silencio a veces es todo lo que te atreves a hacer, y no pasa nada.")
            )
        ),
        RawDilemma(
            "dil_conv_2", SeedIslands.CONVIVENCIA, "La opinión distinta en clase",
            "En un debate de clase, un compañero defiende una opinión con la que estás totalmente en desacuerdo.",
            1,
            listOf(
                RawOption("Lo interrumpo para decir que está equivocado", "Dices lo que piensas rápido, pero puede sentirse como un ataque.", "Lumi piensa: se puede no estar de acuerdo sin necesidad de interrumpir a nadie.", "Nox piensa: a veces las ganas de responder son tan fuertes que cuesta esperar."),
                RawOption("Espero mi turno y explico por qué pienso diferente", "Tarda un poco más, pero el debate se mantiene respetuoso.", "Lumi piensa: escuchar y responder con calma suele construir mejores ideas para todos.", "Nox piensa: esperar el turno puede ser difícil cuando algo te parece muy injusto."),
                RawOption("No digo nada para evitar la discusión", "Evitas el conflicto, pero tu punto de vista no se escucha.", "Lumi piensa: compartir tu opinión, con respeto, también aporta algo al grupo.", "Nox piensa: quedarte callado a veces es la forma en que decides no participar hoy, y también es una opción.")
            )
        )
    )

    val dilemmas: List<DilemmaEntity> = raw.map {
        DilemmaEntity(it.id, it.islandId, it.title, it.scenario, it.order)
    }

    val options: List<DilemmaOptionEntity> = raw.flatMap { d ->
        d.options.mapIndexed { i, o ->
            DilemmaOptionEntity(
                id = "${d.id}_opt${i + 1}",
                dilemmaId = d.id,
                label = o.label,
                consequence = o.consequence,
                lumiView = o.lumi,
                noxView = o.nox,
                sortOrder = i
            )
        }
    }
}

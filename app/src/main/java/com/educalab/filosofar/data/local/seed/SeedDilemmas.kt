package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.entity.DilemmaEntity
import com.educalab.filosofar.data.local.entity.DilemmaOptionEntity

/**
 * 36 dilemas (6 por isla), cada uno con 3 opciones. Se desbloquean 5 por día.
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
            "dil_verdad_3", SeedIslands.VERDAD, "El video increíble",
            "Ves un video con muchísimos 'me gusta' donde alguien asegura algo casi imposible, pero no explica cómo lo comprobó.",
            2,
            listOf(
                RawOption("Lo creo porque tiene muchos 'me gusta'", "Que algo sea popular no significa que sea cierto.", "Lumi piensa: la cantidad de gente que cree algo no lo convierte en verdad.", "Nox piensa: es normal confiar rápido cuando algo parece que todos lo aceptan."),
                RawOption("Busco si alguien más lo explica o lo comprueba", "Tardas un poco más, pero te acercas mejor a la verdad.", "Lumi piensa: comprobar antes de creer es cuidar la verdad.", "Nox piensa: no siempre es fácil encontrar quién lo confirme o lo desmienta."),
                RawOption("No lo creo ni lo comparto porque suena imposible", "Evitas repetir algo dudoso, aunque tal vez no investigues más.", "Lumi piensa: dudar de algo extraño es un buen primer paso.", "Nox piensa: descartarlo sin mirar tampoco es investigar de verdad.")
            )
        ),
        RawDilemma(
            "dil_justicia_3", SeedIslands.JUSTICIA, "La fila del recreo",
            "Hay una sola pelota para jugar y varios compañeros la piden a la vez; uno de ellos casi nunca ha podido jugar en toda la semana.",
            2,
            listOf(
                RawOption("Se la doy a quien llegó primero a pedirla", "Es una regla simple y fácil de aplicar siempre.", "Lumi piensa: una regla igual para todos evita peleas.", "Nox piensa: llegar primero no siempre refleja quién más lo necesita."),
                RawOption("Se la doy a quien casi no ha podido jugar esta semana", "Reconoces una necesidad real, aunque no sea 'lo más justo' para los demás.", "Lumi piensa: a veces ser justo es fijarte en quién lo necesita más.", "Nox piensa: los demás también tienen ganas de jugar y podrían sentirlo injusto."),
                RawOption("Proponemos turnos para que todos jueguen un rato", "Se tarda un poco en organizar, pero nadie se queda sin jugar.", "Lumi piensa: organizar turnos piensa en todos a la vez, no solo en uno.", "Nox piensa: turnarse funciona mejor cuando el grupo está dispuesto a esperar.")
            )
        ),
        RawDilemma(
            "dil_amistad_3", SeedIslands.AMISTAD, "El cumpleaños con dos planes",
            "Te invitan el mismo día a dos cumpleaños: el de tu mejor amigo y el de un compañero nuevo que quiere conocerte mejor.",
            2,
            listOf(
                RawOption("Voy solo al de mi mejor amigo", "Cuidas una amistad de siempre, aunque el compañero nuevo se sienta dejado de lado.", "Lumi piensa: las amistades de mucho tiempo también merecen prioridad a veces.", "Nox piensa: el compañero nuevo puede sentir que no le diste una oportunidad."),
                RawOption("Voy un rato a cada uno de los dos", "Te esfuerzas por estar en ambos, aunque no disfrutes ninguno con calma.", "Lumi piensa: intentar estar en los dos muestra que te importan ambas personas.", "Nox piensa: repartirte puede hacer que ninguna de las dos fiestas se sienta completa."),
                RawOption("Hablo con ambos con sinceridad sobre lo que puedo hacer", "Es incómodo explicarlo, pero los dos entienden tu situación real.", "Lumi piensa: ser sincero sobre un límite es mejor que desaparecer sin explicar.", "Nox piensa: aun explicándolo bien, alguno de los dos puede sentirse un poco triste igual.")
            )
        ),
        RawDilemma(
            "dil_libertad_3", SeedIslands.LIBERTAD, "El videojuego nuevo",
            "Tus papás te dejan elegir cuánto tiempo juegas videojuegos entre semana, pero notas que cada día te cuesta más dejar de jugar.",
            2,
            listOf(
                RawOption("Sigo jugando el tiempo que yo decida cada día", "Disfrutas la libertad ahora, pero puede afectarte en otras cosas.", "Lumi piensa: elegir libremente también significa notar cuándo algo empieza a costarte controlar.", "Nox piensa: no es fácil darte cuenta tú solo de cuándo algo se te está yendo de las manos."),
                RawOption("Me pongo yo mismo un límite de tiempo antes de empezar", "Te cuesta al principio, pero mantienes el control sobre tu propio tiempo.", "Lumi piensa: poner tus propios límites es una forma madura de ser libre.", "Nox piensa: cumplir un límite que tú mismo pusiste puede ser más difícil de lo que parece."),
                RawOption("Le pido a un adulto que me ayude a poner un límite", "Compartes la decisión, y te resulta más fácil sostenerla.", "Lumi piensa: pedir ayuda para cuidarte no te quita libertad, te la protege.", "Nox piensa: puede sentirse raro pedir ayuda para algo que crees que deberías controlar solo.")
            )
        ),
        RawDilemma(
            "dil_resp_3", SeedIslands.RESPONSABILIDAD, "El experimento arruinado",
            "En el laboratorio de ciencias, tu grupo pierde datos de un experimento porque tú tocaste algo sin darte cuenta de que no debías.",
            2,
            listOf(
                RawOption("No digo nada y dejo que crean que fue un fallo del material", "Evitas el momento incómodo, pero tu grupo no sabe la verdadera causa.", "Lumi piensa: entender qué pasó de verdad ayuda a que no vuelva a ocurrir.", "Nox piensa: admitirlo enseguida puede dar mucho miedo o vergüenza."),
                RawOption("Explico lo que pasó y ofrezco ayudar a repetir el experimento", "Cuesta un poco al principio, pero el grupo entiende y avanza junto contigo.", "Lumi piensa: reconocer el error y ofrecer ayuda es hacerte cargo de verdad.", "Nox piensa: contarlo no borra el tiempo perdido, aunque sí repare la confianza."),
                RawOption("Le cuento solo a un integrante del grupo, en privado", "Compartes el peso con alguien, pero el resto del grupo sigue sin saberlo.", "Lumi piensa: contarlo a alguien ya es un paso, aunque no sea a todos todavía.", "Nox piensa: tarde o temprano el grupo entero necesitará saber qué pasó realmente.")
            )
        ),
        RawDilemma(
            "dil_conv_3", SeedIslands.CONVIVENCIA, "El equipo mezclado",
            "El profe forma equipos mezclando a propósito a niños que casi nunca se juntan entre sí, y a algunos no les gusta la idea.",
            2,
            listOf(
                RawOption("Me quejo para que nos deje elegir a nuestros amigos", "Puede que lo consiga, pero pierdes la oportunidad de conocer a otros compañeros.", "Lumi piensa: quedarte siempre con quien ya conoces no te ayuda a convivir con más gente.", "Nox piensa: es normal sentirte más cómodo con quienes ya conoces bien."),
                RawOption("Acepto el equipo y trato de conocer mejor a los demás", "Al principio se siente raro, pero puedes descubrir cosas nuevas del grupo.", "Lumi piensa: convivir con quien no elegiste también enseña a colaborar de verdad.", "Nox piensa: no siempre es fácil ni cómodo trabajar con quien casi no conoces."),
                RawOption("Acepto el equipo, pero casi no participo con ellos", "Evitas el conflicto, pero tampoco aprovechas la oportunidad de conocerlos.", "Lumi piensa: estar presente sin participar deja pasar la oportunidad de convivir mejor.", "Nox piensa: a veces cuesta abrirse de golpe con un grupo nuevo, y eso también es válido.")
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
        ),
        RawDilemma(
            "dil_verdad_4", SeedIslands.VERDAD, "La respuesta parecida",
            "En un trabajo en pareja, la respuesta de tu compañero se parece mucho a la de otro grupo, y no sabes si copiaron o llegaron a lo mismo por casualidad.",
            3,
            listOf(
                RawOption("Lo acuso directamente de copiar", "Puede generar un conflicto si en realidad no copiaron.", "Lumi piensa: acusar sin pruebas puede dañar la confianza de tu compañero.", "Nox piensa: es normal sospechar cuando algo se parece demasiado."),
                RawOption("Le pregunto primero cómo llegó a esa respuesta", "Tardas un poco, pero entiendes mejor lo que pasó antes de opinar.", "Lumi piensa: preguntar antes de juzgar ayuda a acercarte más a la verdad.", "Nox piensa: puede sentirse incómodo preguntar algo así a un amigo."),
                RawOption("No digo nada y dejo que el profe decida", "Evitas el conflicto, pero no ayudas a aclarar la duda.", "Lumi piensa: a veces callar deja la duda sin resolver para nadie.", "Nox piensa: no siempre es tu responsabilidad investigarlo tú mismo.")
            )
        ),
        RawDilemma(
            "dil_verdad_5", SeedIslands.VERDAD, "La app que adivina el futuro",
            "Una app promete adivinar tu futuro y varios compañeros la usan y comparten lo que 'les dijo'.",
            4,
            listOf(
                RawOption("La descargo y creo lo que me diga", "Puede ser divertido, pero confundes un juego con algo real.", "Lumi piensa: divertirse con algo no significa que sea verdad.", "Nox piensa: es fácil dejarse llevar cuando todos a tu alrededor lo hacen."),
                RawOption("La pruebo, pero recuerdo que es solo un juego", "Te diviertes sin confundir el juego con la realidad.", "Lumi piensa: disfrutar algo sabiendo que no es real es una forma sana de jugar.", "Nox piensa: a veces cuesta recordar eso cuando algo 'acierta' por casualidad."),
                RawOption("No la uso porque no tiene forma de comprobarse", "Te pierdes la diversión del grupo, pero evitas confundirte.", "Lumi piensa: dudar de algo que no se puede comprobar es razonable.", "Nox piensa: quedarte fuera del juego también tiene su costo social.")
            )
        ),
        RawDilemma(
            "dil_verdad_6", SeedIslands.VERDAD, "El experimento que no salió como esperabas",
            "Haces un experimento de ciencias en casa y el resultado no es el que esperabas según lo que leíste.",
            5,
            listOf(
                RawOption("Cambio el resultado en mi reporte para que coincida con lo esperado", "El reporte se ve 'correcto', pero no refleja lo que realmente pasó.", "Lumi piensa: cambiar un resultado real rompe la confianza en lo que reportas.", "Nox piensa: da miedo entregar algo que parece 'un error'."),
                RawOption("Anoto el resultado real y trato de explicar por qué fue distinto", "Tarda más pensar la explicación, pero el reporte es honesto.", "Lumi piensa: un resultado inesperado, bien explicado, también enseña algo verdadero.", "Nox piensa: puede que no encuentres una explicación clara, y eso también está bien decirlo."),
                RawOption("No entrego el experimento porque no salió bien", "Evitas mostrar el resultado, pero pierdes la oportunidad de aprender de él.", "Lumi piensa: un experimento 'fallido' también aporta información real.", "Nox piensa: da vergüenza mostrar algo que no salió como se esperaba.")
            )
        ),
        RawDilemma(
            "dil_justicia_4", SeedIslands.JUSTICIA, "El asiento reservado",
            "En el autobús escolar hay un asiento marcado para quien lo necesite, y dos compañeros lo piden a la vez: uno llegó primero, el otro tiene una pierna lastimada.",
            3,
            listOf(
                RawOption("Se lo doy a quien llegó primero", "Es una regla simple, aunque no tenga en cuenta la lesión.", "Lumi piensa: una regla igual para todos es fácil de aplicar.", "Nox piensa: llegar primero no siempre refleja quién más lo necesita."),
                RawOption("Se lo doy a quien tiene la pierna lastimada", "Reconoces una necesidad real, aunque el otro también quería sentarse.", "Lumi piensa: a veces ser justo es fijarte en quién lo necesita más en ese momento.", "Nox piensa: quien llegó primero también puede sentir que no se respetó su turno."),
                RawOption("Les pregunto a ambos qué les parece más justo", "Tarda un poco, pero los dos participan en la decisión.", "Lumi piensa: decidir juntos suele sentirse más justo que decidir por ellos.", "Nox piensa: puede ser incómodo para el lastimado tener que explicar su situación en voz alta.")
            )
        ),
        RawDilemma(
            "dil_justicia_5", SeedIslands.JUSTICIA, "El proyecto grupal desigual",
            "En un proyecto de cuatro personas, dos trabajaron mucho más que las otras dos, pero la nota es una sola para todo el grupo.",
            4,
            listOf(
                RawOption("Acepto la nota grupal sin decir nada", "Evitas el conflicto, pero el esfuerzo desigual queda sin reconocerse.", "Lumi piensa: quedarse callado no resuelve la sensación de injusticia.", "Nox piensa: a veces es más fácil dejarlo pasar que abrir un conflicto."),
                RawOption("Hablo con el profe para explicar cómo se repartió el trabajo", "Es incómodo, pero el profe puede tomarlo en cuenta para futuras notas.", "Lumi piensa: contar lo que pasó de forma honesta puede ayudar a que sea más justo la próxima vez.", "Nox piensa: puede sentirse como 'acusar' a los compañeros, aunque no sea la intención."),
                RawOption("Hablo primero con el grupo antes de decir algo al profe", "Tarda más, pero les da a todos la oportunidad de arreglarlo entre ustedes.", "Lumi piensa: resolverlo en grupo primero respeta a todos los involucrados.", "Nox piensa: no siempre el grupo está dispuesto a reconocer el desequilibrio.")
            )
        ),
        RawDilemma(
            "dil_justicia_6", SeedIslands.JUSTICIA, "La regla nueva a mitad de juego",
            "Estás jugando un juego de mesa con amigos y, a mitad de la partida, alguien propone cambiar una regla que te perjudica a ti.",
            5,
            listOf(
                RawOption("Me niego porque la regla cambió a mitad del juego", "Defiendes lo acordado al inicio, aunque el grupo quiera cambiarlo.", "Lumi piensa: cambiar las reglas a mitad de camino puede no ser justo para quien ya jugaba con las anteriores.", "Nox piensa: negarte puede hacer que el grupo sienta que no quieres ceder nunca."),
                RawOption("Acepto el cambio para no pelear con el grupo", "Evitas el conflicto, aunque sientas que te perjudica.", "Lumi piensa: ceder a veces ayuda a que el grupo siga disfrutando el juego.", "Nox piensa: aceptar algo que sientes injusto solo para evitar pelear no siempre se siente bien."),
                RawOption("Propongo aplicar el cambio recién en la siguiente partida", "Se tarda en decidir, pero respeta lo acordado y a la vez escucha la propuesta.", "Lumi piensa: buscar un punto medio puede ser más justo que aceptar o negarse del todo.", "Nox piensa: no siempre el grupo quiere esperar hasta la próxima partida.")
            )
        ),
        RawDilemma(
            "dil_amistad_4", SeedIslands.AMISTAD, "El chiste que no le gustó",
            "Le haces una broma a un amigo frente a otros y, aunque tú la creías graciosa, a él claramente no le gustó.",
            3,
            listOf(
                RawOption("Sigo bromeando porque los demás se ríen", "El grupo se divierte, pero tu amigo se siente peor.", "Lumi piensa: que otros se rían no borra que a tu amigo le esté doliendo.", "Nox piensa: a veces cuesta parar cuando el grupo está reaccionando bien."),
                RawOption("Paro enseguida y le pregunto si está bien", "El momento se vuelve más serio, pero tu amigo se siente escuchado.", "Lumi piensa: notar y frenar cuando algo incomoda a un amigo es cuidar la amistad.", "Nox piensa: puede ser difícil darse cuenta a tiempo cuando estás disfrutando la broma."),
                RawOption("Sigo como si nada, pensando que se le pasará solo", "Evitas el momento incómodo, pero no atiendes cómo se siente.", "Lumi piensa: ignorar cómo se siente un amigo no ayuda a reparar el momento.", "Nox piensa: a veces de verdad se pasa solo, sin necesidad de hacer un gran tema.")
            )
        ),
        RawDilemma(
            "dil_amistad_5", SeedIslands.AMISTAD, "El amigo que siempre gana",
            "Un amigo se enoja mucho cada vez que pierde en un juego, y eso empieza a incomodar al grupo.",
            4,
            listOf(
                RawOption("Dejo que gane siempre para que no se enoje", "Evitas el conflicto, pero el juego deja de ser justo para los demás.", "Lumi piensa: dejarlo ganar siempre no lo ayuda a aprender a manejar perder.", "Nox piensa: a veces es más fácil ceder que lidiar con su enojo."),
                RawOption("Hablo con él a solas sobre cómo reacciona al perder", "Es incómodo al principio, pero puede ayudarlo a notar su propia reacción.", "Lumi piensa: decirle algo con cariño puede ayudarlo más que solo evitar el tema.", "Nox piensa: puede que se ponga a la defensiva al principio, y eso también es normal."),
                RawOption("Dejo de invitarlo a jugar para evitar el problema", "El grupo juega más tranquilo, pero tu amigo se queda fuera.", "Lumi piensa: alejarlo no lo ayuda a mejorar, solo evita el problema por ahora.", "Nox piensa: a veces necesitas un descanso del conflicto antes de poder hablarlo con calma.")
            )
        ),
        RawDilemma(
            "dil_amistad_6", SeedIslands.AMISTAD, "La amistad por conveniencia",
            "Notas que un compañero solo te busca cuando necesita copiar la tarea o pedirte algo prestado.",
            5,
            listOf(
                RawOption("Sigo ayudándolo igual, sin decir nada", "Mantienes la relación como está, aunque sientas que es desigual.", "Lumi piensa: seguir ayudando sin hablarlo no cambia el patrón que te incomoda.", "Nox piensa: puede que él no se dé cuenta de cómo se ve desde afuera."),
                RawOption("Le digo con calma que sientes que solo te busca por interés", "Es incómodo decirlo, pero le das la oportunidad de cambiar.", "Lumi piensa: decir lo que sientes con respeto puede mejorar una relación desequilibrada.", "Nox piensa: puede que se sorprenda o se sienta mal al escucharlo, sin ser su intención."),
                RawOption("Dejo de ayudarlo sin explicarle por qué", "Pones un límite, pero él se queda sin entender qué pasó.", "Lumi piensa: poner un límite está bien, aunque explicarlo ayuda a que se entienda mejor.", "Nox piensa: a veces no tienes ánimo de dar explicaciones, y también es válido.")
            )
        ),
        RawDilemma(
            "dil_libertad_4", SeedIslands.LIBERTAD, "El grupo de chat de la clase",
            "Tus papás te dejan tener un chat grupal con tus compañeros, pero piden revisar los mensajes de vez en cuando.",
            3,
            listOf(
                RawOption("Les muestro el chat cuando lo piden, sin problema", "Mantienes la confianza con tus papás, aunque a veces se sienta invasivo.", "Lumi piensa: aceptar una supervisión razonable no te quita toda tu libertad.", "Nox piensa: puede sentirse incómodo que revisen conversaciones con amigos."),
                RawOption("Les pido hablar sobre qué tanto necesitan revisar y por qué", "Se abre una conversación honesta sobre confianza y límites.", "Lumi piensa: negociar el límite juntos puede ser más libre que solo aceptarlo o rechazarlo.", "Nox piensa: no siempre los adultos están dispuestos a negociar esto."),
                RawOption("Borro mensajes antes de mostrarles el chat", "Sientes que proteges tu privacidad, pero arriesgas la confianza si lo descubren.", "Lumi piensa: ocultar algo puede dañar la confianza más de lo que protege tu libertad.", "Nox piensa: es comprensible querer un espacio propio, aunque no sea la mejor forma de lograrlo.")
            )
        ),
        RawDilemma(
            "dil_libertad_5", SeedIslands.LIBERTAD, "Elegir la actividad extra",
            "Tus papás te dejan elegir una actividad extraescolar, pero prefieren que sea algo distinto a lo que tú quieres.",
            4,
            listOf(
                RawOption("Elijo lo que yo quiero, sin importar su opinión", "Sigues tu propio interés, aunque ellos no estén de acuerdo.", "Lumi piensa: elegir lo que de verdad te gusta es parte de ser libre.", "Nox piensa: ignorar por completo su opinión puede generar más conflicto en casa."),
                RawOption("Elijo lo que ellos prefieren para evitar discutir", "Evitas el conflicto, pero no haces lo que realmente querías.", "Lumi piensa: ceder del todo tampoco es la única forma de mostrar respeto.", "Nox piensa: a veces es más fácil ceder que sostener lo que uno quiere."),
                RawOption("Les explico por qué prefiero mi opción y escucho la de ellos", "Tarda más llegar a un acuerdo, pero ambos se sienten escuchados.", "Lumi piensa: explicar tus razones y escuchar las de otros es una libertad más madura.", "Nox piensa: no siempre se llega a un acuerdo, aunque lo intenten con calma.")
            )
        ),
        RawDilemma(
            "dil_libertad_6", SeedIslands.LIBERTAD, "El compañero que no quiere jugar el juego que todos eligieron",
            "En el recreo, el grupo vota jugar a algo y un compañero no quiere participar porque prefiere otra cosa.",
            5,
            listOf(
                RawOption("Lo obligamos a jugar porque 'ganó la votación'", "El grupo juega completo, pero él se siente forzado a participar.", "Lumi piensa: ganar una votación no da derecho a obligar a alguien a participar.", "Nox piensa: puede sentirse justo seguir lo que decidió la mayoría."),
                RawOption("Lo dejamos hacer lo que quiera aparte del grupo", "Respetas su libertad, aunque el grupo se divida un poco.", "Lumi piensa: nadie está obligado a participar en algo que no quiere.", "Nox piensa: puede sentirse un poco solo si se aparta del grupo."),
                RawOption("Buscamos un juego que combine lo que quieren los dos lados", "Se tarda en decidir, pero se busca incluir a todos de alguna forma.", "Lumi piensa: buscar una opción intermedia respeta la libertad de todos a la vez.", "Nox piensa: no siempre existe un juego que combine gustos tan distintos.")
            )
        ),
        RawDilemma(
            "dil_resp_4", SeedIslands.RESPONSABILIDAD, "La mascota que se escapó",
            "Te encargas de cerrar la jaula del hámster de la clase, pero un día se te olvida y el hámster se escapa.",
            3,
            listOf(
                RawOption("No digo nada y espero que alguien más lo encuentre", "Evitas el momento incómodo, pero nadie sabe que hay que buscarlo con cuidado.", "Lumi piensa: avisar rápido ayuda a que lo encuentren antes de que se lastime.", "Nox piensa: da mucho miedo admitir el descuido enseguida."),
                RawOption("Aviso enseguida a la profe y ayudo a buscarlo", "Es incómodo admitirlo, pero se actúa rápido para encontrarlo.", "Lumi piensa: avisar de inmediato es la forma más responsable de reparar un descuido.", "Nox piensa: no siempre es fácil hablar enseguida cuando algo sale mal."),
                RawOption("Busco al hámster yo solo antes de decir algo", "Puedes encontrarlo o no, pero pierdes tiempo valioso sin ayuda del grupo.", "Lumi piensa: pedir ayuda antes suele ser más efectivo que intentarlo solo primero.", "Nox piensa: es comprensible querer arreglarlo tú mismo antes de admitir el error.")
            )
        ),
        RawDilemma(
            "dil_resp_5", SeedIslands.RESPONSABILIDAD, "El compromiso que ya no puedes cumplir",
            "Te comprometes a ayudar a un compañero con un proyecto, pero luego te das cuenta de que tienes demasiadas cosas pendientes.",
            4,
            listOf(
                RawOption("Sigo con el compromiso aunque no dé abasto", "Cumples lo prometido, aunque termines muy cansado o con trabajos a medias.", "Lumi piensa: cumplir lo prometido es importante, aunque también hay que cuidar tus propios límites.", "Nox piensa: forzarte demasiado puede afectar tanto tu ayuda como tus propias tareas."),
                RawOption("Le aviso pronto que no podré ayudar como pensaba", "Es incómodo decirlo, pero le das tiempo para buscar otra solución.", "Lumi piensa: avisar a tiempo es más responsable que desaparecer sin explicación.", "Nox piensa: puede sentirse mal decir que no después de haber dicho que sí."),
                RawOption("Dejo de responder sus mensajes para no tener que explicarlo", "Evitas la conversación incómoda, pero tu compañero se queda sin saber qué pasó.", "Lumi piensa: desaparecer sin avisar suele doler más que decir la verdad a tiempo.", "Nox piensa: a veces cuesta mucho encontrar las palabras para decir que no puedes.")
            )
        ),
        RawDilemma(
            "dil_resp_6", SeedIslands.RESPONSABILIDAD, "El material prestado que se dañó",
            "Pides prestados unos colores a un compañero y, sin darte cuenta, uno se rompe mientras los usas.",
            5,
            listOf(
                RawOption("Los devuelvo sin decir nada del color roto", "Evitas el momento incómodo, pero tu compañero puede descubrirlo después.", "Lumi piensa: no contarlo puede hacer que confíe menos en prestarte cosas otra vez.", "Nox piensa: puede que ni siquiera note el color roto entre tantos otros."),
                RawOption("Le cuento lo que pasó y le ofrezco reponerlo", "Es incómodo admitirlo, pero mantienes la confianza para futuros préstamos.", "Lumi piensa: contar lo que pasó y ofrecer reparar el daño muestra responsabilidad real.", "Nox piensa: no siempre es fácil, sobre todo si no tienes cómo reponerlo enseguida."),
                RawOption("Le compro uno nuevo sin explicarle qué pasó", "Reparas el daño material, pero él no sabe realmente qué ocurrió.", "Lumi piensa: reparar el daño está bien, pero explicar lo que pasó también importa.", "Nox piensa: a veces es más fácil resolverlo en silencio que dar explicaciones.")
            )
        ),
        RawDilemma(
            "dil_conv_4", SeedIslands.CONVIVENCIA, "El compañero que come distinto",
            "Un compañero trae siempre una comida muy distinta a la del resto, y algunos hacen comentarios sobre el olor o el aspecto.",
            3,
            listOf(
                RawOption("Me quedo callado aunque escuche los comentarios", "No participas en la burla, pero tampoco la frenas.", "Lumi piensa: quedarte callado ya evita sumar a la burla, aunque se puede hacer más.", "Nox piensa: no siempre te animas a decir algo frente al grupo."),
                RawOption("Le digo al grupo que pare con los comentarios", "Es un poco incómodo, pero defiendes a tu compañero frente a todos.", "Lumi piensa: decir algo cuando ves una burla ayuda a cambiar el ambiente del grupo.", "Nox piensa: puede que el grupo se ponga a la defensiva contigo también."),
                RawOption("Me siento con él y le pregunto sobre su comida", "Puede sentirse raro al principio, pero él se siente acompañado.", "Lumi piensa: interesarte de verdad por lo distinto ayuda más que solo callar la burla.", "Nox piensa: acercarte no borra los comentarios que ya escuchó antes.")
            )
        ),
        RawDilemma(
            "dil_conv_5", SeedIslands.CONVIVENCIA, "El idioma que no todos entienden",
            "En un grupo de trabajo, dos compañeros hablan entre ellos en otro idioma que el resto no entiende.",
            4,
            listOf(
                RawOption("Les pido que dejen de hablar en ese idioma", "El grupo entiende todo, pero ellos sienten que se les pide dejar de ser ellos mismos.", "Lumi piensa: pedirlo sin explicar puede sentirse como rechazar su idioma.", "Nox piensa: es comprensible querer entender todo lo que se dice en tu propio grupo."),
                RawOption("Les pido que, si pueden, traduzcan lo importante para el grupo", "Se tarda un poco más, pero todos quedan incluidos en lo esencial.", "Lumi piensa: pedir traducción en vez de silencio total respeta ambas necesidades.", "Nox piensa: no siempre es fácil traducir todo en el momento."),
                RawOption("No digo nada y sigo trabajando aparte de esa conversación", "Evitas el conflicto, pero te pierdes de participar en esa parte del grupo.", "Lumi piensa: quedarte al margen no resuelve la sensación de exclusión.", "Nox piensa: a veces es más cómodo simplemente seguir con tu parte del trabajo.")
            )
        ),
        RawDilemma(
            "dil_conv_6", SeedIslands.CONVIVENCIA, "La regla del salón que a nadie le gusta",
            "La clase tiene una norma de silencio total durante el trabajo individual, y varios compañeros creen que es demasiado estricta.",
            5,
            listOf(
                RawOption("La rompo cuando creo que nadie se dará cuenta", "A veces funciona, pero rompe una norma que el grupo acordó seguir.", "Lumi piensa: romper una norma en secreto no ayuda a cambiarla de verdad.", "Nox piensa: es comprensible que una norma estricta cueste seguir todo el tiempo."),
                RawOption("La sigo aunque no me guste, sin decir nada", "Mantienes la convivencia del grupo, aunque sientas que la norma es injusta.", "Lumi piensa: seguir una norma con la que no estás de acuerdo también es parte de convivir.", "Nox piensa: quedarse con la molestia sin decirla puede acumularse con el tiempo."),
                RawOption("Propongo al grupo hablarlo con la profe para ajustarla juntos", "Tarda más lograr un cambio, pero se hace de forma que todos puedan aceptar.", "Lumi piensa: proponer un cambio de forma abierta respeta la convivencia del grupo.", "Nox piensa: no siempre la profe está dispuesta a cambiar una norma ya establecida.")
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

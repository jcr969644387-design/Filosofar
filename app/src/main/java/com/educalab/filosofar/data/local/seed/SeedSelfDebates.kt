package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.entity.DebateArgumentEntity
import com.educalab.filosofar.data.local.entity.SelfDebateEntity

/** 18 debates (3 por isla). Se desbloqueja 1 por día. */
object SeedSelfDebates {

    data class RawArg(val text: String, val side: String)
    data class RawDebate(
        val id: String, val islandId: String, val topic: String,
        val sideA: String, val sideB: String, val order: Int, val args: List<RawArg>
    )

    private val raw = listOf(
        RawDebate(
            "debate_verdad", SeedIslands.VERDAD,
            "¿Deberíamos creer algo solo porque lo dice la mayoría?",
            "Sí, confiar en la mayoría", "No, hay que revisarlo igual",
            0,
            listOf(
                RawArg("Muchas personas de acuerdo reduce la probabilidad de un error individual.", "A"),
                RawArg("Es más rápido confiar en lo que ya piensa la mayoría.", "A"),
                RawArg("En el pasado, la mayoría creyó cosas que luego resultaron falsas.", "B"),
                RawArg("La verdad no se decide por votación, sino por evidencia.", "B"),
                RawArg("Escuchar a la mayoría puede ser un buen punto de partida para investigar.", "A"),
                RawArg("Revisar la evidencia por uno mismo ayuda a entender mejor, no solo a repetir.", "B")
            )
        ),
        RawDebate(
            "debate_justicia", SeedIslands.JUSTICIA,
            "¿Es más justo repartir por igual o repartir según el esfuerzo?",
            "Repartir por igual", "Repartir según el esfuerzo",
            0,
            listOf(
                RawArg("Repartir igual evita discusiones sobre quién trabajó más.", "A"),
                RawArg("Todos forman parte del grupo, aunque su aporte varíe.", "A"),
                RawArg("Repartir según el esfuerzo reconoce a quien se dedicó más.", "B"),
                RawArg("Puede motivar a esforzarse más la próxima vez.", "B"),
                RawArg("Medir el esfuerzo de cada quien no siempre es fácil ni justo.", "A"),
                RawArg("Repartir siempre igual puede desanimar a quien se esfuerza mucho.", "B")
            )
        ),
        RawDebate(
            "debate_amistad", SeedIslands.AMISTAD,
            "¿Un buen amigo debe decirte siempre la verdad, aunque duela?",
            "Sí, siempre la verdad", "A veces es mejor callar",
            0,
            listOf(
                RawArg("La sinceridad ayuda a mejorar y a confiar más en el otro.", "A"),
                RawArg("Ocultar la verdad puede parecer amable, pero no ayuda a largo plazo.", "A"),
                RawArg("Hay momentos en los que decir todo puede herir sin necesidad.", "B"),
                RawArg("Se puede elegir el momento adecuado para decir algo difícil.", "B"),
                RawArg("Un amigo sincero, dicho con cuidado, sigue siendo un buen amigo.", "A"),
                RawArg("La forma de decir algo importa tanto como el contenido.", "B")
            )
        ),
        RawDebate(
            "debate_libertad", SeedIslands.LIBERTAD,
            "¿Deberían los niños poder decidir solos su hora de dormir?",
            "Sí, ellos deciden", "No, mejor con ayuda de un adulto",
            0,
            listOf(
                RawArg("Decidir por uno mismo ayuda a aprender a organizarse.", "A"),
                RawArg("Cada persona conoce mejor que nadie cómo se siente de cansada.", "A"),
                RawArg("Dormir poco afecta a la salud y a la concentración en el cole.", "B"),
                RawArg("Los adultos tienen experiencia sobre las consecuencias a largo plazo.", "B"),
                RawArg("Se puede negociar un horario entre el niño y sus padres.", "A"),
                RawArg("Una guía adulta no significa quitar toda la libertad de decidir.", "B")
            )
        ),
        RawDebate(
            "debate_resp", SeedIslands.RESPONSABILIDAD,
            "Si un grupo comete un error, ¿todos son igual de responsables?",
            "Sí, todos por igual", "No, depende de cada uno",
            0,
            listOf(
                RawArg("Formar parte del grupo implica asumir juntos lo que pasa.", "A"),
                RawArg("Repartir la responsabilidad por igual simplifica las cosas.", "A"),
                RawArg("Quien más participó en el error debería asumir más responsabilidad.", "B"),
                RawArg("No es justo que alguien que avisó del error cargue lo mismo que quien lo causó.", "B"),
                RawArg("Compartir responsabilidad puede fortalecer al grupo si se hace con honestidad.", "A"),
                RawArg("Distinguir responsabilidades ayuda a que no se repita el mismo error.", "B")
            )
        ),
        RawDebate(
            "debate_conv", SeedIslands.CONVIVENCIA,
            "¿Es mejor tener las mismas costumbres que tu grupo o mantener las tuyas propias?",
            "Adaptarse al grupo", "Mantener lo propio",
            0,
            listOf(
                RawArg("Compartir costumbres puede facilitar sentirse parte de un grupo.", "A"),
                RawArg("Adaptarse un poco ayuda a que todos se entiendan mejor.", "A"),
                RawArg("Mantener lo propio enriquece al grupo con algo distinto.", "B"),
                RawArg("Cambiar quién eres solo por encajar no siempre se siente bien.", "B"),
                RawArg("Un grupo sano permite adaptarse sin perder lo importante de cada quien.", "A"),
                RawArg("La diversidad dentro de un grupo puede ser una fortaleza, no un problema.", "B")
            )
        ),
        RawDebate(
            "debate_verdad_2", SeedIslands.VERDAD,
            "¿Es más confiable lo que ves con tus propios ojos o lo que dicen los expertos?",
            "Confiar en lo que veo", "Confiar en los expertos",
            1,
            listOf(
                RawArg("Ver algo con tus propios ojos te da una prueba directa.", "A"),
                RawArg("Nadie puede engañarte sobre algo que tú mismo viviste.", "A"),
                RawArg("Los expertos usan instrumentos que ven más de lo que el ojo humano puede.", "B"),
                RawArg("A veces los ojos se equivocan, como en las ilusiones ópticas.", "B"),
                RawArg("Confiar solo en ti te hace pensar por tu cuenta.", "A"),
                RawArg("Los expertos comparan muchos casos, no solo uno.", "B")
            )
        ),
        RawDebate(
            "debate_verdad_3", SeedIslands.VERDAD,
            "¿Una mentira piadosa (para no herir a alguien) sigue siendo una mentira?",
            "Sí, sigue siendo mentira", "No, es distinto a mentir para hacer daño",
            2,
            listOf(
                RawArg("Decir algo falso es mentir, sin importar la intención.", "A"),
                RawArg("Aunque sea con buena intención, la otra persona no sabe la verdad.", "A"),
                RawArg("La intención de cuidar a alguien es muy distinta a la de engañarlo.", "B"),
                RawArg("A veces decir la verdad completa no ayuda en nada a la otra persona.", "B"),
                RawArg("Empezar a justificar mentiras 'pequeñas' puede volverse costumbre.", "A"),
                RawArg("Hay formas de ser honesto sin herir, aunque no sea toda la verdad de golpe.", "B")
            )
        ),
        RawDebate(
            "debate_justicia_2", SeedIslands.JUSTICIA,
            "¿Está bien romper una regla si crees que es injusta?",
            "Sí, si de verdad es injusta", "No, mejor cambiarla sin romperla",
            1,
            listOf(
                RawArg("Algunas reglas injustas solo cambian cuando alguien las desafía.", "A"),
                RawArg("Seguir una regla que sabes injusta puede sentirse peor que romperla.", "A"),
                RawArg("Romper una regla puede tener consecuencias para ti y para otros.", "B"),
                RawArg("Cambiarla por las vías correctas respeta a quienes también deben seguirla.", "B"),
                RawArg("La historia muestra que romper reglas injustas a veces trajo cambios importantes.", "A"),
                RawArg("No todos estarán de acuerdo en qué regla es 'injusta', y eso puede generar caos.", "B")
            )
        ),
        RawDebate(
            "debate_justicia_3", SeedIslands.JUSTICIA,
            "¿Deberían todos recibir el mismo premio aunque hayan tenido distinta suerte?",
            "Sí, el mismo premio para todos", "No, depende también de la suerte de cada uno",
            2,
            listOf(
                RawArg("Un mismo premio evita que la suerte decida quién gana más.", "A"),
                RawArg("Todos participaron con las mismas reglas del juego.", "A"),
                RawArg("Ignorar la suerte puede no reconocer a quien tuvo condiciones más difíciles.", "B"),
                RawArg("Tener en cuenta las circunstancias de cada quien puede ser más justo.", "B"),
                RawArg("Repartir igual es más simple y evita peleas.", "A"),
                RawArg("No toda la suerte es igual para todos desde el inicio.", "B")
            )
        ),
        RawDebate(
            "debate_amistad_2", SeedIslands.AMISTAD,
            "¿Puedes tener un mejor amigo y seguir siendo amigo cercano de otras personas?",
            "Sí, sin ningún problema", "Puede generar celos o desequilibrio",
            1,
            listOf(
                RawArg("Tener un mejor amigo no significa querer menos a los demás.", "A"),
                RawArg("Cada amistad puede ser distinta sin que compitan entre ellas.", "A"),
                RawArg("A veces alguien puede sentirse menos importante si hay un 'mejor' amigo declarado.", "B"),
                RawArg("Comparar amistades puede generar tensiones sin que sea la intención.", "B"),
                RawArg("Un buen amigo entiende que tú tengas más de una amistad cercana.", "A"),
                RawArg("No siempre es fácil equilibrar el tiempo entre varias amistades cercanas.", "B")
            )
        ),
        RawDebate(
            "debate_amistad_3", SeedIslands.AMISTAD,
            "¿Es necesario tener los mismos gustos que tu amigo para llevarse bien?",
            "Sí, ayuda mucho tener gustos parecidos", "No, se puede ser amigo con gustos distintos",
            2,
            listOf(
                RawArg("Compartir gustos da más cosas de qué hablar y hacer juntos.", "A"),
                RawArg("Es más fácil encontrarse en actividades cuando les gusta lo mismo.", "A"),
                RawArg("Los gustos distintos pueden hacer que aprendan cosas nuevas el uno del otro.", "B"),
                RawArg("Lo que realmente une una amistad suele ser la confianza, no los gustos.", "B"),
                RawArg("Gustos parecidos pueden evitar algunos desacuerdos.", "A"),
                RawArg("Muchas amistades duraderas combinan personas muy distintas entre sí.", "B")
            )
        ),
        RawDebate(
            "debate_libertad_2", SeedIslands.LIBERTAD,
            "¿Deberían los niños poder elegir completamente su propia ropa, sin ninguna opinión de un adulto?",
            "Sí, es su cuerpo y su elección", "No, a veces se necesita orientación",
            1,
            listOf(
                RawArg("Elegir su propia ropa ayuda a que un niño exprese quién es.", "A"),
                RawArg("Equivocarse eligiendo ropa no tiene consecuencias graves.", "A"),
                RawArg("El clima o una ocasión especial a veces requiere cierto tipo de ropa.", "B"),
                RawArg("Un adulto puede ayudar a pensar en la comodidad o seguridad de la elección.", "B"),
                RawArg("Practicar elegir cosas pequeñas ayuda a decidir cosas más grandes después.", "A"),
                RawArg("Orientar no es lo mismo que decidir todo por el niño.", "B")
            )
        ),
        RawDebate(
            "debate_libertad_3", SeedIslands.LIBERTAD,
            "¿Ser libre significa no tener que pedir permiso para nada?",
            "Sí, mientras más permisos, menos libertad", "No, pedir permiso también puede ser parte de la libertad",
            2,
            listOf(
                RawArg("Cada permiso que hay que pedir limita lo que puedes decidir tú solo.", "A"),
                RawArg("Demasiadas reglas de 'pedir permiso' pueden sentirse agobiantes.", "A"),
                RawArg("Pedir permiso a veces es una forma de cuidar a quienes te rodean.", "B"),
                RawArg("Vivir en grupo implica algunos acuerdos, no libertad total sin límites.", "B"),
                RawArg("La libertad crece cuando confían más en tus decisiones.", "A"),
                RawArg("Pedir permiso puede ser una muestra de respeto, no una pérdida de libertad.", "B")
            )
        ),
        RawDebate(
            "debate_resp_2", SeedIslands.RESPONSABILIDAD,
            "¿Debes disculparte aunque no creas haber hecho nada malo, si alguien se sintió herido?",
            "Sí, disculparse igual ayuda", "No, disculparse sin sentirlo no es honesto",
            1,
            listOf(
                RawArg("Disculparse puede reconocer el efecto en el otro, aunque no hubiera mala intención.", "A"),
                RawArg("Una disculpa puede sanar la relación aunque no cambie tu opinión.", "A"),
                RawArg("Disculparse sin sentirlo puede parecer falso.", "B"),
                RawArg("Es mejor explicar tu punto de vista que fingir estar de acuerdo.", "B"),
                RawArg("No cuesta mucho decir 'lamento que te sintieras así'.", "A"),
                RawArg("Una disculpa vacía no ayuda a entender realmente lo que pasó.", "B")
            )
        ),
        RawDebate(
            "debate_resp_3", SeedIslands.RESPONSABILIDAD,
            "¿Eres responsable de algo que un amigo hizo mal si tú estabas presente y no dijiste nada?",
            "Sí, quedarte callado también cuenta", "No, la responsabilidad es de quien actuó",
            2,
            listOf(
                RawArg("Ver algo mal y no decir nada puede sentirse como aprobarlo.", "A"),
                RawArg("A veces callar ayuda a que un error se repita.", "A"),
                RawArg("No siempre es fácil ni seguro decir algo en el momento.", "B"),
                RawArg("Quien decide y actúa es quien carga la responsabilidad principal.", "B"),
                RawArg("Estar presente te da la oportunidad de decir algo, aunque sea difícil.", "A"),
                RawArg("No todos se sienten con la confianza de hablar frente a un amigo.", "B")
            )
        ),
        RawDebate(
            "debate_conv_2", SeedIslands.CONVIVENCIA,
            "¿Está bien seguir una norma de grupo con la que no estás de acuerdo, para no generar conflicto?",
            "Sí, a veces es mejor seguirla igual", "No, hay que decir lo que piensas",
            1,
            listOf(
                RawArg("Seguir la norma mantiene la paz del grupo mientras se busca cambiarla.", "A"),
                RawArg("No todo desacuerdo necesita convertirse en un conflicto grande.", "A"),
                RawArg("Quedarte callado siempre puede acumular malestar con el tiempo.", "B"),
                RawArg("Decir lo que piensas, con respeto, también ayuda a mejorar al grupo.", "B"),
                RawArg("A veces conviene elegir bien el momento para expresar el desacuerdo.", "A"),
                RawArg("Un grupo sano permite que sus miembros expresen desacuerdos.", "B")
            )
        ),
        RawDebate(
            "debate_conv_3", SeedIslands.CONVIVENCIA,
            "¿Es más importante llevarse bien con todos o ser fiel a lo que piensas, aunque incomode?",
            "Llevarse bien con todos", "Ser fiel a lo que piensas",
            2,
            listOf(
                RawArg("La buena convivencia facilita la vida diaria en un grupo.", "A"),
                RawArg("Evitar conflictos innecesarios ayuda a mantener relaciones sanas.", "A"),
                RawArg("Ceder siempre puede hacer que pierdas de vista lo que realmente piensas.", "B"),
                RawArg("Ser honesto contigo mismo, aunque incomode, genera respeto a largo plazo.", "B"),
                RawArg("Se puede ser amable sin dejar de ser uno mismo.", "A"),
                RawArg("No siempre se puede (ni se debe) estar de acuerdo con todos.", "B")
            )
        )
    )

    val debates: List<SelfDebateEntity> = raw.map {
        SelfDebateEntity(it.id, it.islandId, it.topic, it.sideA, it.sideB, it.order)
    }

    val arguments: List<DebateArgumentEntity> = raw.flatMap { d ->
        d.args.mapIndexed { i, a ->
            DebateArgumentEntity(
                id = "${d.id}_arg${i + 1}",
                debateId = d.id,
                correctSide = a.side,
                text = a.text
            )
        }
    }
}

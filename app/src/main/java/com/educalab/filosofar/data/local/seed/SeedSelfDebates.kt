package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.entity.DebateArgumentEntity
import com.educalab.filosofar.data.local.entity.SelfDebateEntity

object SeedSelfDebates {

    data class RawArg(val text: String, val side: String)
    data class RawDebate(
        val id: String, val islandId: String, val topic: String,
        val sideA: String, val sideB: String, val args: List<RawArg>
    )

    private val raw = listOf(
        RawDebate(
            "debate_verdad", SeedIslands.VERDAD,
            "¿Deberíamos creer algo solo porque lo dice la mayoría?",
            "Sí, confiar en la mayoría", "No, hay que revisarlo igual",
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
            listOf(
                RawArg("Compartir costumbres puede facilitar sentirse parte de un grupo.", "A"),
                RawArg("Adaptarse un poco ayuda a que todos se entiendan mejor.", "A"),
                RawArg("Mantener lo propio enriquece al grupo con algo distinto.", "B"),
                RawArg("Cambiar quién eres solo por encajar no siempre se siente bien.", "B"),
                RawArg("Un grupo sano permite adaptarse sin perder lo importante de cada quien.", "A"),
                RawArg("La diversidad dentro de un grupo puede ser una fortaleza, no un problema.", "B")
            )
        )
    )

    val debates: List<SelfDebateEntity> = raw.map {
        SelfDebateEntity(it.id, it.islandId, it.topic, it.sideA, it.sideB)
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

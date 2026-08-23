package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.entity.DailyQuestionEntity

object SeedQuestions {
    val all: List<DailyQuestionEntity> = buildList {
        addIsland(
            SeedIslands.VERDAD,
            "¿Algo deja de ser verdad si nadie lo cree?" to "Piensa en algo que la gente creía hace mucho tiempo y hoy sabemos que no era así.",
            "¿Puedes estar seguro de algo sin haberlo visto tú mismo?" to "Piensa en cómo sabes que existen lugares que nunca has visitado.",
            "Si un amigo te cuenta una historia exagerada para hacerte reír, ¿está mintiendo?" to "Piensa en la diferencia entre mentir y contar un cuento."
        )
        addIsland(
            SeedIslands.JUSTICIA,
            "¿Repartir algo en partes iguales es siempre lo más justo?" to "Piensa en repartir un pastel entre alguien que ya comió y alguien que no.",
            "¿Debe recibir el mismo castigo quien rompe algo sin querer y quien lo rompe a propósito?" to "Piensa en dos formas distintas de romper un jarrón.",
            "¿Es justo cambiar una regla si casi nadie la está cumpliendo?" to "Piensa en una norma del cole que casi nadie respeta."
        )
        addIsland(
            SeedIslands.AMISTAD,
            "¿Un amigo tiene que estar de acuerdo contigo siempre?" to "Piensa en una vez que discutiste con un amigo y seguisteis siéndolo.",
            "¿Se puede ser amigo de alguien a quien casi nunca ves?" to "Piensa en un amigo que se mudó de ciudad.",
            "¿Es mejor un amigo sincero que a veces duele, o uno que siempre te dice lo que quieres oír?" to "Piensa en cuando un amigo te avisó de un error."
        )
        addIsland(
            SeedIslands.LIBERTAD,
            "¿Ser libre significa poder hacer todo lo que quieras?" to "Piensa en qué pasaría si todos hicieran solo lo que quieren, sin límites.",
            "¿Las reglas te quitan libertad o te ayudan a tenerla?" to "Piensa en las reglas de un juego que te gusta.",
            "¿Eres libre de elegir aunque tus padres decidan algunas cosas por ti?" to "Piensa en las decisiones pequeñas que sí eliges cada día."
        )
        addIsland(
            SeedIslands.RESPONSABILIDAD,
            "Si rompes algo sin querer, ¿eres igual de responsable que si lo haces a propósito?" to "Piensa en la diferencia entre un accidente y una decisión.",
            "¿Eres responsable de algo que hiciste hace mucho tiempo, aunque hayas cambiado?" to "Piensa en una promesa que hiciste hace tiempo.",
            "¿Puedes ser responsable de cuidar algo que no es tuyo?" to "Piensa en cuidar la mascota de un vecino."
        )
        addIsland(
            SeedIslands.CONVIVENCIA,
            "¿Por qué necesitamos normas para vivir juntos?" to "Piensa en qué pasaría en un parque sin ninguna norma.",
            "¿Está bien no estar de acuerdo con alguien y aun así respetarlo?" to "Piensa en una opinión distinta a la tuya que sigues respetando.",
            "¿Escuchar a alguien significa estar de acuerdo con lo que dice?" to "Piensa en la diferencia entre escuchar y aceptar."
        )
    }

    private fun MutableList<DailyQuestionEntity>.addIsland(
        islandId: String,
        vararg items: Pair<String, String>
    ) {
        items.forEachIndexed { index, (text, hint) ->
            add(
                DailyQuestionEntity(
                    id = "${islandId}_q${index + 1}",
                    islandId = islandId,
                    text = text,
                    hint = hint,
                    orderInIsland = index
                )
            )
        }
    }
}

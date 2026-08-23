package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.entity.PerspectiveExerciseEntity

/**
 * Ejercicios "Otro punto de vista" (6 por isla, 36 en total). Se desbloquean
 * 5 por día en cada isla.
 */
object SeedPerspectives {

    data class RawPerspective(
        val situation: String,
        val roleAText: String,
        val roleAViewpoint: String,
        val roleBText: String,
        val roleBViewpoint: String,
        val reflectionPrompt: String
    )

    val all: List<PerspectiveExerciseEntity> = buildList {
        addIsland(
            SeedIslands.VERDAD,
            RawPerspective(
                "Dos amigos ven el mismo partido de fútbol desde lados opuestos de la cancha.",
                "Desde la portería norte", "Desde aquí, el gol claramente entró.",
                "Desde la portería sur", "Desde aquí, el balón pareció salir antes de entrar.",
                "¿Puede cada uno estar diciendo la verdad sobre lo que vio, aunque cuenten algo distinto?"
            ),
            RawPerspective(
                "Un abuelo y su nieta hablan sobre si la Tierra 'siempre se supo' que era redonda.",
                "El abuelo, que estudió hace muchos años", "Recuerda que de niño le enseñaron cosas que hoy sabemos que eran incorrectas.",
                "La nieta, que lo estudia ahora", "Aprende con instrumentos y datos que antes no existían.",
                "¿Cómo cambia lo que 'sabemos con seguridad' cuando cambian nuestras herramientas?"
            ),
            RawPerspective(
                "Dos niños describen de forma distinta un mismo choque de bicicletas en el patio.",
                "Quien vio el choque desde la izquierda", "Vio que la bici azul se metió en el camino de la roja.",
                "Quien vio el choque desde la derecha", "Vio que la bici roja frenó de golpe sin avisar.",
                "¿Puede ser útil juntar varios puntos de vista para entender mejor lo que pasó?"
            ),
            RawPerspective(
                "Una familia recuerda una misma fiesta de forma distinta: unos dicen que fue divertidísima, otros que fue aburrida.",
                "El primo que bailó toda la noche", "Recuerda la música y los juegos.",
                "El primo que se quedó sentado", "Recuerda que no conocía a casi nadie.",
                "¿Puede un mismo evento ser verdad de dos formas distintas para dos personas?"
            ),
            RawPerspective(
                "Dos compañeros leen el mismo cuento y sacan conclusiones distintas sobre su final.",
                "Quien cree que el final es feliz", "Se fija en que el personaje logró su meta.",
                "Quien cree que el final es triste", "Se fija en lo que el personaje tuvo que dejar atrás para lograrlo.",
                "¿Puede un mismo final ser leído de más de una forma verdadera?"
            ),
            RawPerspective(
                "Un grupo mide la altura de un árbol de formas distintas y obtiene números un poco diferentes.",
                "Quien midió con una cinta métrica", "Confía en su medida porque tocó el árbol directamente.",
                "Quien midió por la sombra del árbol", "Confía en su medida porque usó un cálculo matemático.",
                "¿Dos formas distintas de medir pueden acercarse igual de bien a la verdad?"
            )
        )
        addIsland(
            SeedIslands.JUSTICIA,
            RawPerspective(
                "En una carrera escolar, un niño con una pierna lesionada corre con los demás sin ninguna ventaja.",
                "El árbitro, que sigue la regla igual para todos", "Cree que dar el mismo trato a todos es lo más justo.",
                "Un compañero, que ve la dificultad extra del niño lesionado", "Cree que tratar igual a quien parte en desventaja no siempre es justo.",
                "¿Puede la igualdad de trato a veces no ser lo mismo que la justicia?"
            ),
            RawPerspective(
                "Un grupo decide un castigo colectivo porque no saben quién rompió una ventana del cole.",
                "Un alumno que sí fue responsable", "Piensa que es injusto que todos paguen por lo que hizo una sola persona.",
                "Un alumno inocente del castigo", "Piensa que también es injusto para él, que no hizo nada.",
                "¿Qué sería más justo: castigar a todos, a nadie, o buscar otra solución?"
            ),
            RawPerspective(
                "En un cumpleaños se reparte un pastel en partes iguales, pero uno de los invitados es alérgico y no puede comerlo.",
                "Quien organiza el reparto igual para todos", "Cree que repartir igual es lo más simple y justo.",
                "El invitado alérgico", "Cree que necesitaría algo distinto, no una parte igual que no puede comer.",
                "¿Repartir igual es siempre lo mismo que repartir de forma justa?"
            ),
            RawPerspective(
                "Dos hermanos reciben la misma cantidad de tiempo de videojuegos, pero uno terminó sus tareas y el otro no.",
                "El hermano que terminó sus tareas", "Cree que debería tener más tiempo por haber cumplido.",
                "El hermano que no terminó", "Cree que el tiempo de juego no debería depender de las tareas.",
                "¿Debe la justicia premiar el esfuerzo o repartir siempre igual?"
            ),
            RawPerspective(
                "En un concurso, dos equipos empatan en puntos pero uno jugó con menos integrantes.",
                "El equipo completo", "Cree que ganó en igualdad de condiciones para todos.",
                "El equipo con menos integrantes", "Cree que lo lograron con más esfuerzo por persona.",
                "¿Debería un empate en puntos tener en cuenta las condiciones de cada equipo?"
            ),
            RawPerspective(
                "Un maestro decide dar puntos extra a quien entregó el trabajo primero, aunque todos lo hicieron bien.",
                "Quien entregó primero", "Cree que merece un reconocimiento por su rapidez.",
                "Quien entregó al final pero con más cuidado", "Cree que la calidad debería valer más que la rapidez.",
                "¿Qué es más justo premiar: la rapidez o el cuidado del trabajo?"
            )
        )
        addIsland(
            SeedIslands.AMISTAD,
            RawPerspective(
                "Dos amigos discuten porque uno cree que el otro lo dejó plantado, pero hubo un malentendido de horario.",
                "El amigo que esperó solo", "Sintió que no le importaba a su amigo.",
                "El amigo que llegó tarde", "Pensó que habían quedado una hora después.",
                "¿Cómo cambia el enfado cuando se descubre que fue solo un malentendido?"
            ),
            RawPerspective(
                "Un niño tímido no habla mucho en el recreo y un compañero cree que no quiere hacer amigos.",
                "El compañero extrovertido", "Piensa que si alguien no habla, es porque no le interesa socializar.",
                "El niño tímido", "En realidad quiere tener amigos, pero le cuesta dar el primer paso.",
                "¿Cómo se ve distinta la timidez desde dentro y desde fuera?"
            ),
            RawPerspective(
                "Dos amigas se pelean porque una prestó un juguete de la otra sin pedir permiso.",
                "La dueña del juguete", "Siente que no respetaron algo suyo.",
                "La amiga que lo prestó", "Pensó que estaba bien porque eran muy amigas.",
                "¿La confianza entre amigos borra la necesidad de pedir permiso?"
            ),
            RawPerspective(
                "Un grupo de amigos deja fuera sin querer a alguien al organizar un plan por un chat en el que esa persona no estaba.",
                "Los amigos que organizaron el plan", "No lo hicieron a propósito, solo no pensaron en avisarle por otro lado.",
                "El amigo que se enteró tarde", "Sintió que lo dejaron fuera aunque no fuera la intención.",
                "¿Puede algo doler igual aunque no haya sido intencional?"
            ),
            RawPerspective(
                "Dos amigos compiten por el mismo puesto en el equipo de fútbol del cole.",
                "Quien queda en el equipo", "Está feliz, pero le preocupa cómo se sienta su amigo.",
                "Quien no queda en el equipo", "Está triste, pero también contento por su amigo.",
                "¿Pueden convivir la alegría y la tristeza por lo mismo entre dos amigos?"
            ),
            RawPerspective(
                "Un amigo cambia de gustos de música y juegos, y el otro siente que ya no tienen tanto en común.",
                "El amigo que cambió de gustos", "Sigue queriendo a su amigo, aunque le gusten cosas distintas ahora.",
                "El amigo que no cambió", "Siente que se están alejando poco a poco.",
                "¿Una amistad necesita tener siempre los mismos gustos para seguir siendo real?"
            )
        )
        addIsland(
            SeedIslands.LIBERTAD,
            RawPerspective(
                "Una familia discute si un niño de 10 años debería poder elegir su propia hora de dormir.",
                "El niño", "Siente que ya es lo bastante grande para decidir esto.",
                "El adulto", "Sabe que dormir poco afecta a la concentración y a la salud.",
                "¿Puede alguien tener razón en lo que siente y, aun así, no ser la mejor decisión?"
            ),
            RawPerspective(
                "En un parque, un grupo quiere poner música alta y otro grupo quiere silencio para leer.",
                "El grupo de la música", "Cree que un parque es un lugar para divertirse en grupo.",
                "El grupo que lee", "Cree que un parque también es un lugar para estar tranquilo.",
                "¿Cómo se puede compartir un mismo espacio con necesidades distintas?"
            ),
            RawPerspective(
                "Un niño quiere elegir su propia ropa para ir al cole, pero sus papás prefieren algo distinto por el clima.",
                "El niño", "Siente que elegir su ropa es parte de ser él mismo.",
                "El adulto", "Sabe que el clima de hoy puede hacer que se enferme con esa ropa.",
                "¿Puede alguien tener razón en lo que quiere y, aun así, no ser lo mejor para hoy?"
            ),
            RawPerspective(
                "En clase, el profe deja elegir el tema del proyecto libremente, pero pide que sea sobre ciencias.",
                "Un alumno que quería un tema totalmente libre", "Siente que la libertad debería ser total.",
                "El profe", "Cree que un límite razonable no quita la libertad de elegir dentro de él.",
                "¿Puede haber libertad real incluso dentro de un límite?"
            ),
            RawPerspective(
                "Dos amigos organizan su tiempo libre de forma muy distinta: uno planea todo, el otro improvisa.",
                "Quien planea todo", "Se siente libre sabiendo qué va a pasar.",
                "Quien improvisa", "Se siente libre no teniendo que decidir con anticipación.",
                "¿Puede la libertad sentirse distinta según la personalidad de cada quien?"
            ),
            RawPerspective(
                "Un grupo de amigos decide entre todos qué película ver, pero uno quería ver otra completamente distinta.",
                "La mayoría del grupo", "Cree que decidir juntos es la forma más libre de elegir en grupo.",
                "Quien quedó en minoría", "Siente que su libertad de elegir quedó en segundo lugar.",
                "¿Cómo se puede ser libre dentro de una decisión de grupo?"
            )
        )
        addIsland(
            SeedIslands.RESPONSABILIDAD,
            RawPerspective(
                "Un equipo pierde un partido y todos se culpan entre ellos, aunque el resultado dependió de varias jugadas.",
                "El portero, que recibió el gol decisivo", "Siente que la derrota fue principalmente su culpa.",
                "El resto del equipo", "Sabe que hubo varias jugadas antes que también influyeron en el resultado.",
                "¿Por qué solemos sentirnos más responsables de lo que en realidad somos?"
            ),
            RawPerspective(
                "Una niña olvida el cumpleaños de su mejor amiga porque tuvo una semana muy difícil en casa.",
                "La amiga que se sintió olvidada", "Siente que no le importó lo suficiente para recordarlo.",
                "La niña que olvidó la fecha", "Estaba tan preocupada por su familia que no pudo pensar en nada más.",
                "¿Cómo cambia lo que sentimos al conocer las razones detrás de un olvido?"
            ),
            RawPerspective(
                "Un grupo deja sucio el salón después de una actividad y nadie quiere limpiar porque 'no fue solo mi culpa'.",
                "Quien ensució más", "Siente que no debería limpiar solo, ya que todos usaron el salón.",
                "Quien casi no ensució", "Siente que no debería limpiar igual que quien ensució mucho más.",
                "¿Cómo se reparte la responsabilidad cuando el desorden lo hicieron varios, pero no por igual?"
            ),
            RawPerspective(
                "Un niño presta su bicicleta a un amigo, que la devuelve con un pequeño rayón que no sabe cuándo pasó.",
                "El dueño de la bicicleta", "Siente que su amigo debería haber tenido más cuidado.",
                "El amigo que la usó", "No sabe exactamente cuándo pasó el rayón y se siente mal igual.",
                "¿Se puede ser responsable de algo aunque no se sepa exactamente cómo pasó?"
            ),
            RawPerspective(
                "Una niña se compromete a cuidar el jardín de la escuela por un mes, pero se enferma la última semana.",
                "La niña que se enfermó", "Siente que hizo casi todo lo que prometió.",
                "Un compañero que esperaba que terminara el mes completo", "Siente que el jardín quedó sin cuidar esa última semana.",
                "¿Qué tan responsable es alguien que cumplió casi todo, pero no pudo terminar por algo fuera de su control?"
            ),
            RawPerspective(
                "Dos compañeros de banco olvidan avisar que se rompió un lápiz de la clase durante una actividad grupal.",
                "Quien rompió el lápiz sin querer", "No lo dijo enseguida porque no se dio cuenta al momento.",
                "El compañero de banco", "Se dio cuenta pero pensó que el otro ya lo había dicho.",
                "¿Cómo se reparte la responsabilidad cuando dos personas comparten un mismo descuido?"
            )
        )
        addIsland(
            SeedIslands.CONVIVENCIA,
            RawPerspective(
                "En una clase con niños de distintos países, algunos celebran fiestas que otros no conocen.",
                "Un niño que celebra una fiesta poco conocida en la clase", "Le gustaría que sus compañeros se interesaran por ella.",
                "Un niño que nunca oyó hablar de esa fiesta", "No sabe mucho sobre ella y no está seguro de cómo preguntar.",
                "¿Qué puede hacer cada uno para acercar sus mundos sin sentirse incómodo?"
            ),
            RawPerspective(
                "Un vecindario debate si construir una zona de juegos ruidosa cerca de las casas de personas mayores.",
                "Los niños del barrio", "Quieren un lugar cercano donde jugar después del cole.",
                "Los vecinos mayores", "Valoran la tranquilidad de sus tardes en casa.",
                "¿Existe alguna solución que tenga en cuenta las dos necesidades a la vez?"
            ),
            RawPerspective(
                "En una clase con niños de distintas edades mezcladas, algunos juegos son fáciles para unos y difíciles para otros.",
                "Los niños más grandes", "Sienten que los juegos son demasiado fáciles para ellos.",
                "Los niños más pequeños", "Sienten que los juegos son demasiado difíciles para ellos.",
                "¿Cómo se puede convivir bien en un grupo con necesidades tan distintas?"
            ),
            RawPerspective(
                "Un compañero que habla muy fuerte por naturaleza es percibido por otros como que 'siempre está gritando'.",
                "El compañero que habla fuerte", "No se da cuenta de que su volumen normal molesta a otros.",
                "Los compañeros que lo perciben como gritos", "Sienten que es difícil concentrarse cerca de él.",
                "¿Cómo se puede convivir con una diferencia que no es intencional pero sí molesta?"
            ),
            RawPerspective(
                "En un equipo de trabajo, uno prefiere hablar mucho las ideas y otro prefiere escribirlas antes de hablar.",
                "Quien prefiere hablar las ideas", "Siente que así el equipo avanza más rápido.",
                "Quien prefiere escribir primero", "Siente que necesita ese tiempo para pensar mejor.",
                "¿Puede un grupo convivir bien respetando formas distintas de trabajar?"
            ),
            RawPerspective(
                "Dos compañeros de pupitre tienen alergias alimentarias distintas y a veces se preocupan por la comida del otro.",
                "Quien tiene una alergia", "Le gustaría que su compañero entendiera por qué debe tener cuidado.",
                "El compañero sin alergias", "A veces olvida que debe tener cuidado con lo que comparte.",
                "¿Cómo ayuda conocer las necesidades de quienes conviven contigo cada día?"
            )
        )
    }

    private fun MutableList<PerspectiveExerciseEntity>.addIsland(islandId: String, vararg items: RawPerspective) {
        items.forEachIndexed { index, p ->
            add(
                PerspectiveExerciseEntity(
                    id = "${islandId}_persp${index + 1}",
                    islandId = islandId,
                    situation = p.situation,
                    roleAText = p.roleAText,
                    roleAViewpoint = p.roleAViewpoint,
                    roleBText = p.roleBText,
                    roleBViewpoint = p.roleBViewpoint,
                    reflectionPrompt = p.reflectionPrompt,
                    orderInIsland = index
                )
            )
        }
    }
}

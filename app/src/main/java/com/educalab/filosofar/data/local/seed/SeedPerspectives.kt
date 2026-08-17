package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.entity.PerspectiveExerciseEntity

/**
 * 12 ejercicios "Otro punto de vista" (2 por isla). Alcance reducido y
 * documentado desde los 20 del pliego original.
 */
object SeedPerspectives {
    val all = listOf(
        PerspectiveExerciseEntity(
            "persp_verdad_1", SeedIslands.VERDAD,
            "Dos amigos ven el mismo partido de fútbol desde lados opuestos de la cancha.",
            "Desde la portería norte", "Desde aquí, el gol claramente entró.",
            "Desde la portería sur", "Desde aquí, el balón pareció salir antes de entrar.",
            "¿Puede cada uno estar diciendo la verdad sobre lo que vio, aunque cuenten algo distinto?"
        ),
        PerspectiveExerciseEntity(
            "persp_verdad_2", SeedIslands.VERDAD,
            "Un abuelo y su nieta hablan sobre si la Tierra 'siempre se supo' que era redonda.",
            "El abuelo, que estudió hace muchos años", "Recuerda que de niño le enseñaron cosas que hoy sabemos que eran incorrectas.",
            "La nieta, que lo estudia ahora", "Aprende con instrumentos y datos que antes no existían.",
            "¿Cómo cambia lo que 'sabemos con seguridad' cuando cambian nuestras herramientas?"
        ),
        PerspectiveExerciseEntity(
            "persp_justicia_1", SeedIslands.JUSTICIA,
            "En una carrera escolar, un niño con una pierna lesionada corre con los demás sin ninguna ventaja.",
            "El árbitro, que sigue la regla igual para todos", "Cree que dar el mismo trato a todos es lo más justo.",
            "Un compañero, que ve la dificultad extra del niño lesionado", "Cree que tratar igual a quien parte en desventaja no siempre es justo.",
            "¿Puede la igualdad de trato a veces no ser lo mismo que la justicia?"
        ),
        PerspectiveExerciseEntity(
            "persp_justicia_2", SeedIslands.JUSTICIA,
            "Un grupo decide un castigo colectivo porque no saben quién rompió una ventana del cole.",
            "Un alumno que sí fue responsable", "Piensa que es injusto que todos paguen por lo que hizo una sola persona.",
            "Un alumno inocente del castigo", "Piensa que también es injusto para él, que no hizo nada.",
            "¿Qué sería más justo: castigar a todos, a nadie, o buscar otra solución?"
        ),
        PerspectiveExerciseEntity(
            "persp_amistad_1", SeedIslands.AMISTAD,
            "Dos amigos discuten porque uno cree que el otro lo dejó plantado, pero hubo un malentendido de horario.",
            "El amigo que esperó solo", "Sintió que no le importaba a su amigo.",
            "El amigo que llegó tarde", "Pensó que habían quedado una hora después.",
            "¿Cómo cambia el enfado cuando se descubre que fue solo un malentendido?"
        ),
        PerspectiveExerciseEntity(
            "persp_amistad_2", SeedIslands.AMISTAD,
            "Un niño tímido no habla mucho en el recreo y un compañero cree que no quiere hacer amigos.",
            "El compañero extrovertido", "Piensa que si alguien no habla, es porque no le interesa socializar.",
            "El niño tímido", "En realidad quiere tener amigos, pero le cuesta dar el primer paso.",
            "¿Cómo se ve distinta la timidez desde dentro y desde fuera?"
        ),
        PerspectiveExerciseEntity(
            "persp_libertad_1", SeedIslands.LIBERTAD,
            "Una familia discute si un niño de 10 años debería poder elegir su propia hora de dormir.",
            "El niño", "Siente que ya es lo bastante grande para decidir esto.",
            "El adulto", "Sabe que dormir poco afecta a la concentración y a la salud.",
            "¿Puede alguien tener razón en lo que siente y, aun así, no ser la mejor decisión?"
        ),
        PerspectiveExerciseEntity(
            "persp_libertad_2", SeedIslands.LIBERTAD,
            "En un parque, un grupo quiere poner música alta y otro grupo quiere silencio para leer.",
            "El grupo de la música", "Cree que un parque es un lugar para divertirse en grupo.",
            "El grupo que lee", "Cree que un parque también es un lugar para estar tranquilo.",
            "¿Cómo se puede compartir un mismo espacio con necesidades distintas?"
        ),
        PerspectiveExerciseEntity(
            "persp_resp_1", SeedIslands.RESPONSABILIDAD,
            "Un equipo pierde un partido y todos se culpan entre ellos, aunque el resultado dependió de varias jugadas.",
            "El portero, que recibió el gol decisivo", "Siente que la derrota fue principalmente su culpa.",
            "El resto del equipo", "Sabe que hubo varias jugadas antes que también influyeron en el resultado.",
            "¿Por qué solemos sentirnos más responsables de lo que en realidad somos?"
        ),
        PerspectiveExerciseEntity(
            "persp_resp_2", SeedIslands.RESPONSABILIDAD,
            "Una niña olvida el cumpleaños de su mejor amiga porque tuvo una semana muy difícil en casa.",
            "La amiga que se sintió olvidada", "Siente que no le importó lo suficiente para recordarlo.",
            "La niña que olvidó la fecha", "Estaba tan preocupada por su familia que no pudo pensar en nada más.",
            "¿Cómo cambia lo que sentimos al conocer las razones detrás de un olvido?"
        ),
        PerspectiveExerciseEntity(
            "persp_conv_1", SeedIslands.CONVIVENCIA,
            "En una clase con niños de distintos países, algunos celebran fiestas que otros no conocen.",
            "Un niño que celebra una fiesta poco conocida en la clase", "Le gustaría que sus compañeros se interesaran por ella.",
            "Un niño que nunca oyó hablar de esa fiesta", "No sabe mucho sobre ella y no está seguro de cómo preguntar.",
            "¿Qué puede hacer cada uno para acercar sus mundos sin sentirse incómodo?"
        ),
        PerspectiveExerciseEntity(
            "persp_conv_2", SeedIslands.CONVIVENCIA,
            "Un vecindario debate si construir una zona de juegos ruidosa cerca de las casas de personas mayores.",
            "Los niños del barrio", "Quieren un lugar cercano donde jugar después del cole.",
            "Los vecinos mayores", "Valoran la tranquilidad de sus tardes en casa.",
            "¿Existe alguna solución que tenga en cuenta las dos necesidades a la vez?"
        )
    )
}

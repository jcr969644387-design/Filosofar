package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.entity.ReasonCardEntity

/**
 * 24 cartas de razones reutilizables entre islas y dilemas (alcance reducido
 * y documentado desde las 40 del pliego original). Cada carta lleva
 * etiquetas de valor usadas por ReasonCoherenceEngine para evaluar
 * coherencia, nunca corrección moral.
 */
object SeedReasonCards {
    val all = listOf(
        ReasonCardEntity("rc_01", "Porque así nadie sale perjudicado", "justicia,cuidado", "scale"),
        ReasonCardEntity("rc_02", "Porque es lo que me gustaría que hicieran conmigo", "empatia,justicia", "heart"),
        ReasonCardEntity("rc_03", "Porque una promesa se debe cumplir", "confianza,responsabilidad", "handshake"),
        ReasonCardEntity("rc_04", "Porque todos merecen la misma oportunidad", "justicia,igualdad", "scale"),
        ReasonCardEntity("rc_05", "Porque decir la verdad evita problemas mayores", "verdad,confianza", "compass"),
        ReasonCardEntity("rc_06", "Porque cada persona tiene derecho a decidir por sí misma", "libertad,respeto", "key"),
        ReasonCardEntity("rc_07", "Porque las consecuencias afectarían a más gente", "responsabilidad,cuidado", "shield"),
        ReasonCardEntity("rc_08", "Porque hay que reparar el daño que se causó", "responsabilidad,justicia", "toolbox"),
        ReasonCardEntity("rc_09", "Porque escuchar antes de juzgar es más justo", "respeto,verdad", "ear"),
        ReasonCardEntity("rc_10", "Porque la amistad se cuida con hechos, no solo con palabras", "amistad,confianza", "heart"),
        ReasonCardEntity("rc_11", "Porque no toda regla vieja sigue teniendo sentido hoy", "libertad,verdad", "compass"),
        ReasonCardEntity("rc_12", "Porque pedir ayuda no te hace menos capaz", "responsabilidad,cuidado", "handshake"),
        ReasonCardEntity("rc_13", "Porque respetar no es lo mismo que estar de acuerdo", "respeto,convivencia", "scale"),
        ReasonCardEntity("rc_14", "Porque lo fácil no siempre es lo correcto", "responsabilidad,justicia", "toolbox"),
        ReasonCardEntity("rc_15", "Porque cada persona vive las cosas de forma distinta", "empatia,respeto", "ear"),
        ReasonCardEntity("rc_16", "Porque las palabras también pueden hacer daño", "respeto,cuidado", "shield"),
        ReasonCardEntity("rc_17", "Porque aprender de un error vale más que ocultarlo", "verdad,responsabilidad", "compass"),
        ReasonCardEntity("rc_18", "Porque incluir a todos hace mejor al grupo", "justicia,convivencia", "handshake"),
        ReasonCardEntity("rc_19", "Porque mi libertad no puede quitarle la suya a otra persona", "libertad,respeto", "key"),
        ReasonCardEntity("rc_20", "Porque confiar merece confianza a cambio", "confianza,amistad", "heart"),
        ReasonCardEntity("rc_21", "Porque callar a veces también tiene consecuencias", "verdad,responsabilidad", "shield"),
        ReasonCardEntity("rc_22", "Porque las costumbres distintas no son costumbres equivocadas", "respeto,convivencia", "ear"),
        ReasonCardEntity("rc_23", "Porque pensarlo dos veces evita arrepentimientos", "responsabilidad,verdad", "compass"),
        ReasonCardEntity("rc_24", "Porque el bienestar del grupo también importa, no solo el mío", "convivencia,cuidado", "scale")
    )
}

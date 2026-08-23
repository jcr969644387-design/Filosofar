package com.educalab.filosofar.domain.logic

import java.util.Calendar

/**
 * Cálculo compartido de "día lógico": el día cambia a las 6:00 am hora local,
 * no a medianoche. Lo usan los sistemas de desbloqueo diario (pregunta del
 * día, dilemas, otro punto de vista) para decidir cuánto contenido nuevo
 * mostrar cada día.
 */
object DayGating {
    fun logicalDayKey(atMillis: Long = System.currentTimeMillis()): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = atMillis
        if (cal.get(Calendar.HOUR_OF_DAY) < 6) {
            cal.add(Calendar.DAY_OF_YEAR, -1)
        }
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis / 86_400_000L
    }
}

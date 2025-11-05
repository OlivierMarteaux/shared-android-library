package com.oliviermarteaux.shared.extensions

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import org.junit.Assert.*
import java.util.GregorianCalendar

class DateExtensionsTest {

    @Test
    fun date_ToHumanDate_ReturnsCorrectFormattedString() {
        // Given a date
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2023)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 15)
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 30)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val date = calendar.time

        // When convert it to human date
        val formatted = date.toHumanDate()

        // Then
        val expected = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
        assertEquals(expected, formatted)
    }

    @Test
    fun date_ToHumanDate_RespectsLeadingZeros() {
        // Given a date
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2024)
            set(Calendar.MONTH, Calendar.MARCH) // Month = 3rd
            set(Calendar.DAY_OF_MONTH, 7)
        }
        val date = calendar.time

        // When convert it to human date
        val formatted = date.toHumanDate()

        // Then expected format always two digits for day and month
        assertEquals("07/03/2024", formatted)
    }

    @Test
    fun date_ToHumanDate_UsesCurrentLocaleFormat() {
        // Given a date
        val date = GregorianCalendar(2025, Calendar.DECEMBER, 25).time

        // When convert it to human date
        val formatted = date.toHumanDate()

        // Then displayed in local format
        val expected = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
        assertEquals(expected, formatted)
    }
}
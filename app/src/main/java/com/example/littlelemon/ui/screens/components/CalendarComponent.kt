package com.example.littlelemon.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarComponent(onDayClick: (LocalDate) -> Unit, selectedDate: LocalDate?) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    HorizontalCalendar(
        state = state,
        dayContent = {
            val selectable = LocalDate.now().isBefore(it.date)
            Day(
                it,
                selectable = selectable,
                onDayClick = {
                    onDayClick(it.date)
                },
                isSelected = selectedDate != null && selectedDate == it.date
            )
        },
        monthHeader = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Select date and time",
                )
                Text(
                    text = it.yearMonth.month.name,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                        .background(color = MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }
    )
}

@Composable
fun Day(day: CalendarDay, selectable: Boolean, onDayClick: () -> Unit, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
            .clickable {
                if (selectable) {
                    onDayClick()
                }
            }, // This is important for square sizing!
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (!selectable) MaterialTheme.colorScheme.errorContainer else {
                if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondary
            },
            fontSize = 13.sp
        )
    }
}


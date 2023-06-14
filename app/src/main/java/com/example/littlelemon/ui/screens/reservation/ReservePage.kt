package com.example.littlelemon.ui.screens.reservation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.littlelemoncoursera.R
import com.example.littlelemon.data.local.TimeSlotsList
import com.example.littlelemon.ui.screens.components.ActionButton
import com.example.littlelemon.ui.screens.components.CalendarComponent
import com.example.littlelemon.ui.screens.components.CommonAppBar
import com.example.littlelemon.ui.screens.components.QtySelectorComponent
import com.example.littlelemon.ui.screens.home.CategoryPill
import com.example.littlelemon.viewmodels.rsvn.ReservationViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationContent(viewModel: ReservationViewModel,onReserve:()->Unit) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CommonAppBar(
                title = stringResource(R.string.reserve_a_table),
                onBackClicked = { /*TODO*/ },
                isBackIconContains = false
            )
        },
        bottomBar = {
            if (uiState.selectedDate != null && uiState.selectedTime != null) {
                val dateFormat =
                    uiState.selectedDate.month.toString() + " " + uiState.selectedDate.dayOfMonth.toString() + ", " + uiState.selectedDate.year.toString()
                ActionButton(
                    onClick = {
                        viewModel.onReserve()
                        Toast.makeText(context, "Reservation Success", Toast.LENGTH_SHORT).show()
                        onReserve()
                    },
                    label = "Book For ${uiState.currentSelectPerson} on ${uiState.selectedTime}, $dateFormat",
                    verticalPadding = 10
                )
            }
        }

    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 15.dp)
        ) {
            Column{
                SelectGuestSizeComp(
                    selectedPersonCount = uiState.currentSelectPerson,
                    onIncreased = { newValue ->
                        viewModel.changePersonCount(newValue)
                    },
                    onDecreased = { newValue ->
                        viewModel.changePersonCount(newValue)
                    }
                )
                SelectDateAndTime(
                    onDayClick = { newDate ->
                        viewModel.onDayClicked(newDate)
                    },
                    selectedDate = uiState.selectedDate,
                    selectedTime = uiState.selectedTime,
                    onTimeSelect = { newTime ->
                        viewModel.onTimeClicked(newTime)
                    }
                )
            }
        }
    }
}

@Composable
fun SelectGuestSizeComp(
    selectedPersonCount: Int,
    onDecreased: (Int) -> Unit,
    onIncreased: (Int) -> Unit,
) {
    Column {
        Text(
            text = stringResource(R.string.select_the_guest_size),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        QtySelectorComponent(
            selectedPersonCount = selectedPersonCount,
            onIncreased = onIncreased,
            onDecreased = onDecreased
        )
    }
}

@Composable
fun SelectDateAndTime(
    onDayClick: (LocalDate) -> Unit,
    selectedDate: LocalDate?,
    selectedTime: String?,
    onTimeSelect: (String) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 5.dp, vertical = 20.dp)) {
        CalendarComponent(onDayClick = onDayClick, selectedDate = selectedDate)
        if (selectedDate != null) {
            TimeList(
                allSlots = TimeSlotsList.allTimeSlots,
                selectedDate = selectedDate,
                selectedTime = selectedTime,
                onTimeSelect = onTimeSelect
            )
        }
    }
}

@Composable
fun TimeList(
    allSlots: List<String>, selectedDate: LocalDate, selectedTime: String?,
    onTimeSelect: (String) -> Unit
) {
    val dateFormat =
        selectedDate.month.toString() + " " + selectedDate.dayOfMonth.toString() + ", " + selectedDate.year.toString()
    Column(modifier = Modifier.padding(top = 10.dp)) {
        Text(text = "Available Slots for $dateFormat")
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 80.dp),
            content = {
                items(allSlots) { slot ->
                    CategoryPill(
                        label = slot,
                        onCategoryItemClicked = {
                            onTimeSelect(slot)
                        },
                        isSelected = selectedTime != null && selectedTime == slot,
                        paddingModifier = Modifier.padding(3.dp),
                        roundPercentage = 10
                    )
                }
            },
            modifier = Modifier.padding(vertical = 15.dp)
        )
    }
}
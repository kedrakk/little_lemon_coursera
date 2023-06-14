package com.example.littlelemon.ui.screens.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.littlelemoncoursera.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QtySelectorComponent(
    selectedPersonCount: Int,
    onDecreased: (Int) -> Unit,
    onIncreased: (Int) -> Unit
) {
    val context = LocalContext.current
    OutlinedTextField(
        value = "$selectedPersonCount Person",
        readOnly = true,
        onValueChange = {},
        leadingIcon = {
            IconButton(
                onClick = {
                    if(selectedPersonCount>1){
                        val newValue = selectedPersonCount - 1
                        onDecreased(newValue)
                    }else{
                        Toast.makeText(context,"Please select at least one person ",Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .padding(start = 5.dp)
                    .border(
                        width = 1.dp,
                        color = if (selectedPersonCount > 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .background(color = if (selectedPersonCount > 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.errorContainer)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_remove_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        trailingIcon = {
            IconButton(
                onClick = {

                    if(selectedPersonCount < 10){
                        val newValue = selectedPersonCount + 1
                        onIncreased(newValue)
                    }else{
                        Toast.makeText(context,"Please select at most ten person ",Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .padding(end = 5.dp)
                    .border(
                        width = 1.dp,
                        color = if (selectedPersonCount < 10) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .background(color = if (selectedPersonCount < 10) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.errorContainer,)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center),
        modifier = Modifier.fillMaxWidth()
    )
}
package com.example.littlelemoncoursera.ui.screens.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.littlelemoncoursera.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QtySelectorComponent() {
    OutlinedTextField(
        value = "1",
        readOnly = true,
        onValueChange = {},
        leadingIcon = {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(start = 5.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .background(color = MaterialTheme.colorScheme.primary)
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
                onClick = { /*TODO*/ },
                // colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .padding(end = 5.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .background(color = MaterialTheme.colorScheme.primary)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
        modifier = Modifier.fillMaxWidth()
    )
}
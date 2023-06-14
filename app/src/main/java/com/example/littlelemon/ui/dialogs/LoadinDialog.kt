package com.example.littlelemon.ui.dialogs

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingDialog(onDialogDismiss:()->Unit) {
    Dialog(onDismissRequest = { onDialogDismiss() }) {
        CircularProgressIndicator(
            modifier = Modifier.then(Modifier.size(32.dp))
        )
    }
}
package com.example.littlelemon.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.littlelemoncoursera.R

@Composable
fun LogoImage(size: Dp) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Little Lemon Logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(size)
    )

}

@Composable
fun TextInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityChanged: () -> Unit = {},
    isSingleLine:Boolean=true,
    maxLines:Int=1,
    isEnabled: Boolean = true,
) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
        )
        LittleLemonTextBox(
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            isPassword=isPassword,
            passwordVisible = passwordVisible,
            onPasswordVisibilityChanged=onPasswordVisibilityChanged,
            isSingleLine = isSingleLine,
            maxLines = maxLines,
            placeholderText = label,
            isEnabled = isEnabled
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LittleLemonTextBox(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityChanged: () -> Unit = {},
    isOutline:Boolean = true,
    isSingleLine:Boolean=true,
    maxLines:Int=1,
    placeholderText:String,
    isEnabled:Boolean = true
) {
    if(isOutline)
    {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            isError = isError,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth(),
            singleLine = isSingleLine,
            maxLines = maxLines,
            label={
              Text(text = placeholderText)
            },
            enabled = isEnabled,
            trailingIcon = {
                if (isPassword) PasswordVisibility(isVisible = passwordVisible) {
                    onPasswordVisibilityChanged()
                }
            },
            visualTransformation = if (isPassword && passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        )
    }else{
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 5.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
            ),
            label={
                Text(text = placeholderText)
            },
        )
    }
}

@Composable
fun ActionButton(onClick: () -> Unit, label: String,isOutline: Boolean=false,verticalPadding:Int=20) {
    Button(
        onClick = { onClick() }, shape = RoundedCornerShape(10),
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = verticalPadding.dp)
            .fillMaxWidth(),
        colors = if(isOutline) ButtonDefaults.outlinedButtonColors() else ButtonDefaults.buttonColors()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Composable
fun TextButton(onClick: () -> Unit, label: String,modifier: Modifier=Modifier) {
    ClickableText(
        text = AnnotatedString(label), onClick = { onClick() },
        style = TextStyle(textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.secondary),
        modifier = modifier
    )
}

@Composable
fun PasswordVisibility(isVisible: Boolean, onVisibilityChanged: () -> Unit) {
    IconButton(onClick = { onVisibilityChanged() }) {
        if (isVisible) Icon(
            painter = painterResource(id = R.drawable.baseline_visibility_off_24),
            contentDescription = "Hide Visibility"
        )
        else Icon(
            painter = painterResource(id = R.drawable.baseline_visibility_24),
            contentDescription = "Show Visibility"
        )
    }
}
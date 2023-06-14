package com.example.littlelemon.helper

import android.util.Patterns

fun String.emptyValidation():Boolean{
    return this.isEmpty();
}

fun String.emailValidation():Boolean{
    return !Patterns.EMAIL_ADDRESS.matcher(this).matches();
}
package com.example.littlelemon.model

data class PaymentMethod(val payment: Payment, val iconData: Int)

enum class Payment {
    CASH, CARD
}
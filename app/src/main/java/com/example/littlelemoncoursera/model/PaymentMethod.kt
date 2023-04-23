package com.example.littlelemoncoursera.model

data class PaymentMethod(val payment: Payment, val iconData: Int)

enum class Payment {
    CASH, CARD
}
package com.example.littlelemon.data.local

import com.example.littlelemon.model.Payment
import com.example.littlelemon.model.PaymentMethod
import com.example.littlelemoncoursera.R

object PaymentMethodList {
    val allPaymentMethods:List<PaymentMethod> = listOf(
        PaymentMethod(payment = Payment.CASH, iconData = R.drawable.baseline_money_24),
        PaymentMethod(payment = Payment.CARD, iconData = R.drawable.baseline_credit_card_24),
    )
}
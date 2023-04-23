package com.example.littlelemoncoursera.data.local

import com.example.littlelemoncoursera.R
import com.example.littlelemoncoursera.model.Payment
import com.example.littlelemoncoursera.model.PaymentMethod

object PaymentMethodList {
    val allPaymentMethods:List<PaymentMethod> = listOf(
        PaymentMethod(payment = Payment.CASH, iconData = R.drawable.baseline_money_24),
        PaymentMethod(payment = Payment.CARD, iconData = R.drawable.baseline_credit_card_24),
    )
}
package com.example.littlelemon.data.local

import com.example.littlelemon.model.AppLanguage
import com.example.littlelemoncoursera.R

object AppLanguageList {
    val allLanguages:List<AppLanguage> = listOf(
        AppLanguage(name = "English", res = R.drawable.us, langCode = "en",),
        AppLanguage(name = "Norsk", res = R.drawable.nb, langCode = "nb",),
        AppLanguage(name = "Burmese", res = R.drawable.my, langCode = "my",),
    )
}
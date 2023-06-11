package com.example.littlelemoncoursera.data.local

import com.example.littlelemoncoursera.R
import com.example.littlelemoncoursera.model.AppLanguage

object AppLanguageList {
    val allLanguages:List<AppLanguage> = listOf(
        AppLanguage(name = "English", res = R.drawable.profile, langCode = "en",),
        AppLanguage(name = "Norsk", res = R.drawable.profile, langCode = "nb",),
        AppLanguage(name = "Burmese", res = R.drawable.profile, langCode = "my",),
    )
}
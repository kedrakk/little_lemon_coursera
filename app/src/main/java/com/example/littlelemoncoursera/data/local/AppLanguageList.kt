package com.example.littlelemoncoursera.data.local

import com.example.littlelemoncoursera.R
import com.example.littlelemoncoursera.model.AppLanguage

object AppLanguageList {
    val allLanguages:List<AppLanguage> = listOf(
        AppLanguage(langName = "English", res = R.drawable.profile),
        AppLanguage(langName = "Norsk", res = R.drawable.profile),
        AppLanguage(langName = "Burmese", res = R.drawable.profile),
    )
}
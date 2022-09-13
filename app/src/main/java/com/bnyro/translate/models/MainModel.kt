package com.bnyro.translate.models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bnyro.translate.obj.Language
import com.bnyro.translate.util.RetrofitInstance
import kotlinx.coroutines.launch

class MainModel : ViewModel() {
    var availableLanguages: List<Language> by mutableStateOf(
        emptyList()
    )

    var sourceLanguage: String by mutableStateOf(
        "auto"
    )

    var targetLanguage: String by mutableStateOf(
        "en"
    )

    var insertedText: String by mutableStateOf(
        ""
    )

    var translatedText: String by mutableStateOf(
        ""
    )

    fun translate() {
        if (insertedText == "" || targetLanguage == sourceLanguage) {
            translatedText = ""
            return
        }

        viewModelScope.launch {
            val response = try {
                RetrofitInstance.api.translate(
                    insertedText,
                    sourceLanguage,
                    targetLanguage
                )
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
                return@launch
            }
            translatedText = response.translatedText ?: ""
        }
    }

    fun fetchLanguages() {
        viewModelScope.launch {
            val languages = try {
                RetrofitInstance.api.getLanguages()
            } catch (e: Exception) {
                return@launch
            }
            this@MainModel.availableLanguages = languages
        }
    }
}

package com.example.allinone.ui.news.ui.savednews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedNewsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is SavedNews Fragment"
    }
    val text: LiveData<String> = _text
}
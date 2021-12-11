package com.example.allinone.ui.ui.searchnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchNewsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is SearchNews Fragment"
    }
    val text: LiveData<String> = _text
}
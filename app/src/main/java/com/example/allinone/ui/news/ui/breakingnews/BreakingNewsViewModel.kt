package com.example.allinone.ui.news.ui.breakingnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BreakingNewsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is BreakingNews Fragment"
    }
    val text: LiveData<String> = _text
}
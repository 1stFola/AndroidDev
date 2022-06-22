package com.example.journal.AppHome.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.journal.data.model.Journal

class HomeViewModel : ViewModel() {
    val journals : MutableLiveData<MutableList<Journal>> = MutableLiveData(
    emptyList()
    )

}

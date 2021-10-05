package org.dps.admin.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.dps.admin.model.SuggestionAddress
import org.dps.admin.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

    fun setSuggestions(query:String){
        return repository.setSuggestions(query)
    }
    fun getSuggestions(): MutableLiveData<List<SuggestionAddress>> {
        return repository.getSuggestions()
    }
    fun getErrorMessage(): MutableLiveData<String> {
        return repository.getErrorMessage()
    }
}

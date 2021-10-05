package org.dps.admin.repository

import androidx.lifecycle.MutableLiveData
import org.dps.admin.App
import org.dps.admin.R
import org.dps.admin.model.SuggestionAddress
import org.dps.admin.network.ApiStatus.isCheckAPIStatus
import org.dps.admin.network.RestClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Repository(val restClient: RestClient) {

    private val userList = MutableLiveData<List<SuggestionAddress>>()
    private val errorMess = MutableLiveData<String>()


    fun setSuggestions(query:String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().setSuggestionsAsync(query).await().let {
                    if (it.isSuccessful)
                        userList.postValue(it.body()!!.data!!.addressList!!)
                    else
                        errorMess.value = isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMess.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }

    fun getSuggestions(): MutableLiveData<List<SuggestionAddress>> {
        return userList
    }

    fun getErrorMessage(): MutableLiveData<String> {
        return errorMess
    }
}


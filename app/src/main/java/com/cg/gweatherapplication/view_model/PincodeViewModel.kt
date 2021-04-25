package com.cg.gweatherapplication.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cg.gweatherapplication.model.PincodeData
import com.cg.gweatherapplication.model.RepositoryPincode
import kotlinx.coroutines.launch

class PincodeViewModel(app : Application) : AndroidViewModel(app) {

    private val repo = RepositoryPincode(app)

    fun add(pin : PincodeData){
        viewModelScope.launch {
            repo.addPincode(pin)
        }
    }

    fun delete(pin : PincodeData){
        viewModelScope.launch {
            repo.deletePincode(pin)
        }
    }

    fun update(pin : PincodeData){
        viewModelScope.launch {
            repo.updatePincode(pin)
        }
    }

    fun getPin(){
        viewModelScope.launch {
          repo.getPincode()
        }
    }


}
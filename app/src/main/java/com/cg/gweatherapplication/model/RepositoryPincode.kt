package com.cg.gweatherapplication.model

import android.content.Context

class RepositoryPincode(context:Context) {
    private val pincodeDao = PincodeDataBase.getInstance(context).PincodeDao()

    suspend fun addPincode(pin:PincodeData) = pincodeDao.insert(pin)
    suspend fun updatePincode(pin:PincodeData) = pincodeDao.update(pin)
    suspend fun deletePincode(pin:PincodeData) = pincodeDao.delete(pin)
    suspend fun getPincode() = pincodeDao.getPincode()

}
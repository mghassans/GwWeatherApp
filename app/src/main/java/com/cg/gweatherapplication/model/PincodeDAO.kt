package com.cg.gweatherapplication.model

import androidx.room.*


@Dao
interface PincodeDAO {
    @Insert
    suspend fun insert(pin:PincodeData)

    @Update
    suspend fun update(pin:PincodeData)

    @Delete
    suspend fun delete(pin:PincodeData)

    @Query("SELECT * FROM pincode_table ")
    suspend fun getPincode(): PincodeData
}
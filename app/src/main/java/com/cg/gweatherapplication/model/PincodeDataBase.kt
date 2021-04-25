package com.cg.gweatherapplication.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [PincodeData::class],version = 1)
abstract class PincodeDataBase: RoomDatabase() {
    abstract  fun PincodeDao(): PincodeDAO
    companion object{
        private var instance:PincodeDataBase?=null
        fun getInstance(context :Context)= instance ?: buildDatabase(context).also { instance=it }

        private fun buildDatabase(context: Context): PincodeDataBase {
            val build = Room.databaseBuilder(context.applicationContext,
               PincodeDataBase::class.java,"pincode.db")
            return build.build()
        }
    }
}
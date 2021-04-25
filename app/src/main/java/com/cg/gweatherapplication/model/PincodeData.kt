package com.cg.gweatherapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pincode_table")
data class PincodeData (@ColumnInfo(name = "latitude_val") var lat:Double ,
                        @ColumnInfo(name = "longitude_val") var longi :Double ,
                        @PrimaryKey(autoGenerate = true) var id:Int=0)



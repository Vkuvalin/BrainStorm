package com.kuvalin.brainstorm.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface UserDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSSS(sss: String)
//    @Query("SELECT * FROM table WHERE ")

}
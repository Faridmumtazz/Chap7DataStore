package mumtaz.binar.chap7datastore.view

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "user_login")

    suspend fun saveData(
        username : String,
        password : String,
        nama : String,
        umur : String,
        image : String,
        address : String,
        id : String
    ){
        dataStore.edit {
            it[user] = username
            it[pass] = password
            it[Nama] = nama
            it[Umur] = umur
            it[Image] = image
            it[Address] = address
            it[Id] = id

        }
    }

    val userUsername : Flow<String> = dataStore.data.map {
        it[user]?: ""
    }
    val userPassword : Flow<String> = dataStore.data.map {
        it[pass]?: ""
    }
    val userNama : Flow<String> = dataStore.data.map {
        it[Nama]?: ""
    }
    val userUmur : Flow<String> = dataStore.data.map {
        it[Umur]?: ""
    }
    val userImage : Flow<String> = dataStore.data.map {
        it[Image]?: ""
    }
    val userAddress : Flow<String> = dataStore.data.map {
        it[Address]?: ""
    }
    val userId: Flow<String> = dataStore.data.map {
        it[Id]?: ""
    }
    companion object {
        val user = preferencesKey<String>("user_username")
        val pass = preferencesKey<String>("user_pass")
        val Nama = preferencesKey<String>("user_nama")
        val Umur = preferencesKey<String>("user_umur")
        val Image = preferencesKey<String>("user_image")
        val Address = preferencesKey<String>("user_address")
        val Id = preferencesKey<String>("user_id")
    }
}
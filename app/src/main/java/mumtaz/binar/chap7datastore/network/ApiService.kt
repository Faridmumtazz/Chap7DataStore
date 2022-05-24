package mumtaz.binar.chap7datastore.network

import mumtaz.binar.chap7datastore.model.GetAllUserResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("user")
    fun getUser(
        @Query("username") username : String
    ) : Call<List<GetAllUserResponseItem>>


}
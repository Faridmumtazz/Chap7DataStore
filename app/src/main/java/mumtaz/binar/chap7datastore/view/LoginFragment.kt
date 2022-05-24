package mumtaz.binar.chap7datastore.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mumtaz.binar.chap7datastore.R
import mumtaz.binar.chap7datastore.model.GetAllUserResponseItem
import mumtaz.binar.chap7datastore.network.ApiClient
import retrofit2.Call
import retrofit2.Response

class LoginFragment : Fragment() {

    private var usermanager : UserManager? = null
    lateinit var email : String
    lateinit var password: String
    lateinit var emailLogin: String
    lateinit var passLogin : String
    private lateinit var userlogin : List<GetAllUserResponseItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usermanager = UserManager(requireContext())
        emailLogin = ""

        usermanager!!.userUsername.asLiveData().observe(viewLifecycleOwner,{
            if (it != ""){
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
            }
        })


            btn_login.setOnClickListener {
                if (et_email.text.isNotEmpty() && et_pass.text.isNotEmpty()){
                    email = et_email.text.toString()
                    password = et_pass.text.toString()

                    login(email,password)
                }else{
                    Toast.makeText(requireContext(), "Isi data", Toast.LENGTH_LONG).show()
                }
//            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
            }


    }

    fun login(username : String, password:String){
        ApiClient.instance.getUser(username)
            .enqueue(object : retrofit2.Callback<List<GetAllUserResponseItem>>{
                override fun onResponse(
                    call: Call<List<GetAllUserResponseItem>>,
                    response: Response<List<GetAllUserResponseItem>>
                ) {
                    if (response.isSuccessful){
                        if (response.body()?.isEmpty()== true){
                            Toast.makeText(requireContext(), "unknown user", Toast.LENGTH_LONG).show()
                        }else{
                            when{
                                response.body()?.size!! > 1 -> {
                                    Toast.makeText(requireContext(), "Mohon masukkan data yang benar", Toast.LENGTH_LONG).show()
                                }
                                username != response.body()!! [0].username ->{
                                    Toast.makeText(requireContext(), "Email tidak terdaftar", Toast.LENGTH_LONG).show()
                                }
                                password != response.body()!! [0].password ->{
                                    Toast.makeText(requireContext(), "Password salah", Toast.LENGTH_LONG).show()
                                }
                                else -> {
                                    userlogin = response.body()!!
                                    detailuser(userlogin)
                                    Navigation.findNavController(view!!).navigate(R.id.action_loginFragment_to_homeFragment)
                                }
                            }
                        }
                    }else {
                        Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<GetAllUserResponseItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
                }

            })
    }

    fun detailuser(listLogin: List<GetAllUserResponseItem>){
        for (i in listLogin.indices){
            GlobalScope.launch {
                usermanager?.saveData(
                    listLogin[i].username,
                    listLogin[i].password,
                    listLogin[i].name,
                    listLogin[i].umur.toString(),
                    listLogin[i].image,
                    listLogin[i].address,
                    listLogin[i].id
                )
            }
        }
    }


}
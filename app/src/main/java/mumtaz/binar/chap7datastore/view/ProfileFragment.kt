package mumtaz.binar.chap7datastore.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import kotlinx.android.synthetic.main.fragment_profile.*
import mumtaz.binar.chap7datastore.R


class ProfileFragment : Fragment() {

    private lateinit var usermanager : UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usermanager = UserManager(requireContext())

        usermanager.userNama.asLiveData().observe(viewLifecycleOwner){
            tv_nama.setText(it)
        }
        usermanager.userUmur.asLiveData().observe(viewLifecycleOwner){
            tv_umur.setText(it)
        }
        usermanager.userAddress.asLiveData().observe(viewLifecycleOwner){
            tv_address.setText(it)
        }
    }


}
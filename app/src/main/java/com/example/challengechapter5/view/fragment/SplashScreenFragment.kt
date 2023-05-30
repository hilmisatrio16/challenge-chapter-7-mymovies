package com.example.challengechapter5.view.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.challengechapter5.R
import com.example.challengechapter5.databinding.FragmentSplashScreenBinding
import com.example.challengechapter5.dsprefs.DataStoreUser

@Suppress("all")
class SplashScreenFragment : Fragment() {


    private lateinit var binding : FragmentSplashScreenBinding
    private lateinit var dataStoreUser: DataStoreUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataStoreUser = DataStoreUser.getInstance(requireContext().applicationContext)

        @Suppress("DEPRECATION")
        Handler().postDelayed({
            dataStoreUser.statusUser.asLiveData().observe(viewLifecycleOwner) {
                if(it.isNotEmpty()){
                    val userActive = checkStatusUserActive(it)
                    if(userActive){
                        findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
                    }else{
                        findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                    }
                }else{
                    findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                }
            }
        },2000)
    }

    fun checkStatusUserActive(status : String) : Boolean {
        return status == "active"
    }


}
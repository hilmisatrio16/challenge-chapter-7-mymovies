package com.example.challengechapter5.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.challengechapter5.R
import com.example.challengechapter5.databinding.FragmentProfileBinding
import com.example.challengechapter5.dsprefs.DataStoreUser
import com.example.challengechapter5.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("unused", "unused")
@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dataStoreUser: DataStoreUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        dataStoreUser = DataStoreUser.getInstance(requireContext().applicationContext)


        getProfileUser(firebaseAuth.currentUser!!.email)

        binding.btnLogout.setOnClickListener {
            singOutAccount()
        }

        binding.btnUpdate.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_updateProfileFragment)
        }
    }

    private fun singOutAccount() {
        val dialogConfirmSignOut = AlertDialog.Builder(context)

        dialogConfirmSignOut.apply {
            setTitle("Sign Out Confirmation")
            setMessage("Do you want to sign out this account?")
            setNegativeButton("Cancel"){ option, _ ->
                option.dismiss()
            }

            setPositiveButton("Sign Out"){ _, _ ->


                lifecycleScope.launch {
                    dataStoreUser.deleteDataUser()
                }

                dataStoreUser.statusUser.asLiveData().observe(viewLifecycleOwner) {
                    if (it.isNullOrEmpty()) {
                        firebaseAuth.signOut()
                        val bundleData = Bundle().apply {
                            putBoolean("logout", true)
                        }
                        findNavController().navigate(
                            R.id.action_profileFragment_to_loginFragment,
                            bundleData
                        )
                    }
                    Log.d("OUTE", it.toString())
                }


            }
        }

        dialogConfirmSignOut.show()
    }

    private fun getProfileUser(email: String?) {

        if(email.isNullOrEmpty()){
            dataStoreUser.emailUser.asLiveData().observe(viewLifecycleOwner) {
                if (it != null) {
                    userViewModel.getDataUser(it)
                }
            }
        }else{
            userViewModel.getDataUser(email.toString())
        }

        userViewModel.getDataUserProfile().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.profile = it
            }
        }

    }

}
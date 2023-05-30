package com.example.challengechapter5.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.challengechapter5.R
import com.example.challengechapter5.databinding.FragmentLoginBinding
import com.example.challengechapter5.dsprefs.DataStoreUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("unused", "unused")
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dataStoreUser: DataStoreUser
    private var conditionUser : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        dataStoreUser = DataStoreUser.getInstance(requireContext().applicationContext)

        checkActiveAccount()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            loginAccount()
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        //get data bundle from profile fragment when signout
        arguments?.let{
            conditionUser = it.getBoolean("logout", false)
        }

        if (conditionUser){
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
                requireActivity().finish()
            }
        }

    }

    private fun checkActiveAccount(){
        dataStoreUser.statusUser.asLiveData().observe(viewLifecycleOwner) {
            Log.d("DS", it.toString())
            if (it.toString() == "active") {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }
     private fun loginAccount() {
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            signInFirebase(email, password)
        }else{
            Toast.makeText(context, R.string.input_cannot_empty, Toast.LENGTH_SHORT).show()
        }
    }


    @Suppress("all")
    private fun signInFirebase(email : String, password : String){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){

                lifecycleScope.launch {
                    dataStoreUser.saveDataUser("active", email)
                }
                Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }else{
                if(it.exception is FirebaseAuthInvalidUserException){
                    when((it.exception as FirebaseAuthInvalidUserException).errorCode){
                        "ERROR_USER_NOT_FOUND" -> Toast.makeText(context,"user not found", Toast.LENGTH_SHORT).show()
                        "ERROR_USER_DISABLED" -> Toast.makeText(context, "user has been disabled", Toast.LENGTH_SHORT).show()
                        "ERROR_INVALID_USER_TOKEN" -> Toast.makeText(context, "user has been disabled", Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(context, "email or password is failed", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                }
                //Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }



}
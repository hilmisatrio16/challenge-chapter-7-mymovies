package com.example.challengechapter5.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.challengechapter5.R
import com.example.challengechapter5.databinding.FragmentRegisterBinding
import com.example.challengechapter5.room.User
import com.example.challengechapter5.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("unused", "unused")
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.btnSignUp.setOnClickListener {
            registerAccount()
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    private fun registerAccount() {
        val username = binding.inputUsername.text.toString()
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()
        val passwordConfirm = binding.inputConfirmPassword.text.toString()

        if(username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty()){
            if(password == passwordConfirm){
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val getUserAuth = Firebase.auth.currentUser
                        getUserAuth?.let {it_auth->
                            insertDataUser(username, it_auth.email!!)
                        }
                        Toast.makeText(context, R.string.register_success, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }else{
                        Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(context, R.string.password_not_match, Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context, R.string.input_cannot_empty, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("All")
    private fun insertDataUser(username : String, email : String) {
        lifecycleScope.launch {
            userViewModel.insertDataUser(
                User(
                0,
                email,
                username,
                null,
                null,
                null
            )
            )
        }

    }

}
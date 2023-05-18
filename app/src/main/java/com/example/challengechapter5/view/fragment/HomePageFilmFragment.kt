package com.example.challengechapter5.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.challengechapter5.databinding.FragmentHomePageFilmBinding
import dagger.hilt.android.AndroidEntryPoint

@Suppress("unused")
@AndroidEntryPoint
class HomePageFilmFragment : Fragment() {

    private lateinit var binding: FragmentHomePageFilmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomePageFilmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getBundle = arguments?.getString("url")
        setWebView(getBundle.toString())
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView(url : String) {
        if (url.isNotEmpty()) {
            binding.webViewFilm.loadUrl(url)
            binding.webViewFilm.settings.javaScriptEnabled = true
        }
    }

}
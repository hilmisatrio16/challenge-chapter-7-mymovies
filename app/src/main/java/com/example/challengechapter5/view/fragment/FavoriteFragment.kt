package com.example.challengechapter5.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechapter5.R
import com.example.challengechapter5.databinding.FragmentFavoriteBinding
import com.example.challengechapter5.dsprefs.DataStoreUser
import com.example.challengechapter5.room.FilmFavorite
import com.example.challengechapter5.view.adapter.FilmFavoriteAdapter
import com.example.challengechapter5.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@Suppress("unused")
@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FilmFavoriteAdapter
    private lateinit var dataStoreUser : DataStoreUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = FilmFavoriteAdapter(ArrayList())
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        dataStoreUser = DataStoreUser.getInstance(requireContext().applicationContext)

        setRecycleView()

    }

    private fun setRecycleView() {

        binding.rvFavorite.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvFavorite.adapter = favoriteAdapter

        dataStoreUser.emailUser.asLiveData().observe(viewLifecycleOwner) { email ->

            if (email != null) {
                favoriteViewModel.getDataFilmFavorite(email)

                favoriteViewModel.getDataListFilmFavorit().observe(viewLifecycleOwner) {
                    if (it != emptyList<FilmFavorite>()) {
                        favoriteAdapter.setListFilmFavorite(it)
                    }
                }
            }

        }

        favoriteAdapter.onClickItemFilmFavorite = {
            val bundleIdFilm = Bundle().apply {
                putInt("movieid", it.idFilm)
            }
            findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment, bundleIdFilm)
        }

        favoriteAdapter.onClickRemoveFavorit = {
            val dialogConfirmRemoveFavorite = AlertDialog.Builder(context)

            dialogConfirmRemoveFavorite.apply {
                setTitle("Remove Confirmation")
                setMessage("Do you want to remove this film from list favorit?")
                setNegativeButton("Cancel") { option, _ ->
                    option.dismiss()
                }
                setPositiveButton("Yes") { _, _ ->
                    if(it.emailUser.isNotEmpty()){
                        deleteFilmFavorite(it)

                        favoriteViewModel.getDataFilmFavorite(it.emailUser)
                        favoriteViewModel.getDataListFilmFavorit().observe(viewLifecycleOwner
                        ) { list ->
                            if (list != emptyList<FilmFavorite>() && isRemoving) {
                                favoriteAdapter.setListFilmFavorite(list)
                                Toast.makeText(context, "CEK", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            dialogConfirmRemoveFavorite.show()
        }
    }

    private fun deleteFilmFavorite(dataFilmFavorite: FilmFavorite) {
        lifecycleScope.launch {
            favoriteViewModel.deleteDataFilmFavorite(
                dataFilmFavorite
            )
        }

    }


}
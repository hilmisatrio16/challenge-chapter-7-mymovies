@file:Suppress("KotlinConstantConditions", "KotlinConstantConditions")

package com.example.challengechapter5.view.fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.challengechapter5.BuildConfig
import com.example.challengechapter5.R
import com.example.challengechapter5.databinding.FragmentDetailBinding
import com.example.challengechapter5.dsprefs.DataStoreUser
import com.example.challengechapter5.model.DataComment
import com.example.challengechapter5.model.ResponseDetailFilm
import com.example.challengechapter5.room.FilmFavorite
import com.example.challengechapter5.view.adapter.CommentsAdapter
import com.example.challengechapter5.viewmodel.FavoriteViewModel
import com.example.challengechapter5.viewmodel.FilmViewModel
import com.example.challengechapter5.viewmodel.UserViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("unused")
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding : FragmentDetailBinding
    private lateinit var filmViewModel: FilmViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var dataStoreUser: DataStoreUser
    private lateinit var detailFilm : ResponseDetailFilm
    private lateinit var userViewModel: UserViewModel
    private lateinit var commentsAdapter: CommentsAdapter
    private var idFilmDetail : Int = 0
    private var username : String? = null
    @Inject
    lateinit var firebaseDatabaseReference : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getMovieId = arguments?.getInt("movieid")
        filmViewModel = ViewModelProvider(this)[FilmViewModel::class.java]
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]


        commentsAdapter = CommentsAdapter(ArrayList())

        dataStoreUser = DataStoreUser.getInstance(requireContext().applicationContext)

        getDataDetail(getMovieId!!)


        if(BuildConfig.FLAVOR == "mymoviespro"){

            binding.btnSend.setOnClickListener {
                val comment = binding.etComment.text.toString()
                setCommentFeaturesPro(comment)
            }

        }else{
            binding.layoutSendComments.visibility = View.GONE
        }


        binding.btnFavorite.setOnClickListener {
            dataStoreUser.emailUser.asLiveData().observe(viewLifecycleOwner) { email ->
                if (email != null) {
                    //masih salah karena belum bisa mengenalisa ketika data sudah masuk dan ingin mendelete
                    favoriteViewModel.checkListFavorite(
                        detailFilm.id, email, FilmFavorite(
                            0,
                            detailFilm.id,
                            email,
                            detailFilm.popularity,
                            detailFilm.posterPath,
                            detailFilm.releaseDate,
                            detailFilm.title,
                            detailFilm.voteAverage
                        )
                    )

                    favoriteViewModel.getDetailFilmFavorite(detailFilm.id, email)

                    favoriteViewModel.getDataDetailFilmFavorit().observe(viewLifecycleOwner) {

                        if (it != null) {
                            setColorBtnFavorit(it.idFilm)
                        }

                    }

                }
            }
        }


    }

    private fun checkIdFilm(idFilm: Int) : Boolean{
        return idFilm != 0
    }

    private fun setRecycleViewComment(idFilm: String) {

        val listComment = ArrayList<DataComment>()
        binding.rvComments.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = commentsAdapter
        }
        firebaseDatabaseReference.child(idFilm).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    listComment.clear()
                    for (commentSnapshot in snapshot.children){

//                        Log.d("Hasil_database", da)
                        val comment = commentSnapshot.getValue(DataComment::class.java)
                        Log.d("Hasil_comment", comment.toString())
                        listComment.add(comment!!)
                    }
                }
                commentsAdapter.setDataFilm(listComment)
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setCommentFeaturesPro(comment : String) {
        getUsername()
        if(comment.isNotEmpty() && idFilmDetail != 0 && username != null){
            val dataComment = DataComment(idFilmDetail, username, comment)
            firebaseDatabaseReference.child(idFilmDetail.toString()).child(firebaseDatabaseReference.push().key.toString()).setValue(dataComment).addOnSuccessListener {
                binding.etComment.setText("")
                Toast.makeText(context,"comment added", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context,"comment failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUsername(){
        dataStoreUser.emailUser.asLiveData().observe(viewLifecycleOwner) { email ->
            if(email != null){
                userViewModel.getDataUser(email)
                Log.d("email_user", email)
                userViewModel.userProfile.observe(viewLifecycleOwner){
                    if(it != null){
                        username = it.username
                    }
                }
            }
        }

    }

    @SuppressLint("ResourceAsColor")
    private fun setColorBtnFavorit(id: Int) {
        dataStoreUser.emailUser.asLiveData().observe(viewLifecycleOwner) { email ->
            if (email != null) {
                //masih salah karena belum bisa mengenalisa ketika data sudah masuk dan ingin mendelete
                favoriteViewModel.getDetailFilmFavorite(id, email)

                favoriteViewModel.dataFilmFavorite.observe(viewLifecycleOwner) {

                    if (it != null) {
                        binding.btnFavorite.imageTintList =
                            ColorStateList.valueOf(Color.rgb(255, 50, 50))
                    } else {
                        binding.btnFavorite.imageTintList =
                            ColorStateList.valueOf(R.color.blue_dark)
                    }
                }

            }
        }

    }

    @Suppress("unused")
    private fun getDataDetail(idFilm : Int){
        filmViewModel.callApiDetailFilm(idFilm)

        filmViewModel.getDetailFilm().observe(viewLifecycleOwner) {

            if (it != null) {
                binding.detail = it
                detailFilm = it
                setRecycleViewComment(it.id.toString())
                idFilmDetail = it.id
                setColorBtnFavorit(detailFilm.id)

                var genre = ""
                for (i in 0 until it.genres.size) {//4

                    genre += if ((i + 1) < it.genres.size) {
                        it.genres[i].name + ", "
                    } else {
                        it.genres[i].name
                    }
                }
                binding.genremovie = genre

                binding.progressRate.progress = (it.voteAverage * 10).toInt()

                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500${it.posterPath}")
                    .into(binding.imgFilm)

                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500${it.backdropPath}")
                    .into(binding.imgBackdrop)

                binding.btnHomePage.setOnClickListener { _ ->
                    //move to webview
                    if (it.homepage.isNotEmpty()) {
                        val bundleUrl = Bundle().apply {
                            putString("url", it.homepage)
                        }
                        findNavController().navigate(
                            R.id.action_detailFragment_to_homePageFilmFragment,
                            bundleUrl
                        )
                    } else {
                        Toast.makeText(context, R.string.homepage_information, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

}
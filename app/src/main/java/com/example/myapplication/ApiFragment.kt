package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.placeholder.PostRespoon
import com.example.myapplication.placeholder.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A fragment representing a list of Items.
 */
class ApiFragment : Fragment() {
    private var columnCount = 1
    private lateinit var adapterApi: MyItemRecyclerViewAdapter5
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_api, container, false)

        dbHelper = DatabaseHelper(requireContext())

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                adapterApi = MyItemRecyclerViewAdapter5(emptyList()) { post ->
                    val bundle = Bundle().apply {
                        putInt("id", post.id)
                        putString("title", post.title)
                        putString("body", post.body)
                    }
                    findNavController().navigate(R.id.action_ApiFragment_to_DetailFragment, bundle)
                }
                adapter = adapterApi
            }
        }

        fetchPosts()

        return view
    }

    private fun fetchPosts() {
        RetrofitClient.instance.getPosts().enqueue(object : Callback<ArrayList<PostRespoon>> {
            override fun onResponse(
                call: Call<ArrayList<PostRespoon>>,
                response: Response<ArrayList<PostRespoon>>
            ) {
                if (!isAdded) return

                if (response.isSuccessful) {
                    val posts = response.body()
                    if (posts != null) {
                        // Save to database
                        posts.forEach { dbHelper.insertPost(it) }
                        // Update UI
                        adapterApi.setData(posts)
                    }
                } else {
                    Log.e("ApiFragment", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<PostRespoon>>, t: Throwable) {
                if (!isAdded) return
                Log.e("ApiFragment", "API call failed", t)
            }
        })
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            ApiFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}

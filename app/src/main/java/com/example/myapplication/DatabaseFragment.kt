package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DatabaseFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapterDb: MyItemRecyclerViewAdapter5

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_api, container, false)

        dbHelper = DatabaseHelper(requireContext())
        
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapterDb = MyItemRecyclerViewAdapter5(dbHelper.getAllPosts()) { post ->
                    val bundle = Bundle().apply {
                        putInt("id", post.id)
                        putString("title", post.title)
                        putString("body", post.body)
                    }
                    // Kita bisa menggunakan action yang sama jika tujuannya sama
                    findNavController().navigate(R.id.DetailFragment, bundle)
                }
                adapter = adapterDb
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        // Refresh data from database
        if (::adapterDb.isInitialized) {
            adapterDb.setData(dbHelper.getAllPosts())
        }
    }
}

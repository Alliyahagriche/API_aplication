package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val id = arguments?.getInt("id") ?: 0
        val title = arguments?.getString("title") ?: ""
        val body = arguments?.getString("body") ?: ""

        view.findViewById<TextView>(R.id.detail_id).text = "ID: $id"
        view.findViewById<TextView>(R.id.detail_title).text = title
        view.findViewById<TextView>(R.id.detail_body).text = body

        return view
    }
}

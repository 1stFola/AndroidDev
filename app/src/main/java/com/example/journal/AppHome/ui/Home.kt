package com.example.journal.AppHome.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journal.databinding.FragmentHomeBinding
import com.example.journal.adapters.JournalAdapter

class Home : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var journalAdapter: JournalAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding.journalRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity())

        journalAdapter = JournalAdapter(this@Home)
        homeBinding.journalRecyclerView.adapter = journalAdapter
    }

}
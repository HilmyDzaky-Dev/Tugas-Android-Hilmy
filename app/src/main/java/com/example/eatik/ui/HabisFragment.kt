package com.example.eatik.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatik.R
import com.example.eatik.data.Result
import com.example.eatik.databinding.FragmentHabisBinding

class HabisFragment : Fragment(R.layout.fragment_habis) {
    private var _binding: FragmentHabisBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHabisBinding.bind(view)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.rvHabis.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        viewModel.menuResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                     binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val menuList = result.data
                    val listFiltered = menuList.filter { it.status.equals("Habis", ignoreCase = true) }
                    
                    val adapter = MenuAdapter(listFiltered) { item ->
                        // Handle edit kalau perlu
                    }
                    binding.rvHabis.adapter = adapter
                }
                is Result.Error -> {
                     binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
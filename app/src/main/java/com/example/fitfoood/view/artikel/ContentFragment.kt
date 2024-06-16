package com.example.fitfoood.view.artikel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.ArtikelAdapter
import com.example.fitfoood.data.response.ArtikelResponseItem
import com.example.fitfoood.databinding.FragmentContentBinding

class ContentFragment : Fragment() {
    private var _binding: FragmentContentBinding? = null
    private val binding get() = _binding!!
    private lateinit var artikelAdapter: Artikel2Adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articles = arguments?.getParcelableArrayList<ArtikelResponseItem>("articles") ?: listOf()
        artikelAdapter = Artikel2Adapter(articles)

        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = artikelAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(articles: List<ArtikelResponseItem>) = ContentFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("articles", ArrayList(articles))
            }
        }
    }
}

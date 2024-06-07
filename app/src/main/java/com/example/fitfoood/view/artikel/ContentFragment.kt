package com.example.fitfoood.view.artikel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfoood.Artikel
import com.example.fitfoood.Artikel2Adapter
import com.example.fitfoood.R

class ContentFragment : Fragment() {

    companion object {
        private const val ARG_SECTION = "section"

        fun newInstance(section: String): ContentFragment {
            val fragment = ContentFragment()
            val args = Bundle()
            args.putString(ARG_SECTION, section)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = Artikel2Adapter(getArtikelList())
    }

    private fun getArtikelList(): List<Artikel> {
        // Provide the list of articles
        return listOf(
            Artikel("Artikel 1", R.drawable.dummy_img_artikel,1),
            Artikel("Artikel 2", R.drawable.dummy_img_artikel,2),
            // Add more articles as needed
        )
    }
}

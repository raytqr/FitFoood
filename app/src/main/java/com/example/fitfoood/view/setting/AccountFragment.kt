package com.example.fitfoood.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.fitfoood.R

class AccountFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnEditAccount: Button = view.findViewById(R.id.btnEdit)
        btnEditAccount.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
       if (v?.id == R.id.btnEdit) {
           val editAccountFragment = EditAccountFragment()
           val fragmenManager = parentFragmentManager
           fragmenManager.commit {
               addToBackStack(null)
               replace(R.id.frame_container, editAccountFragment, EditAccountFragment::class.java.simpleName)
           }
       }
    }

}
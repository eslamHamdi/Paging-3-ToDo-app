package com.eslamhamdi.paging3_todo.ui.rx

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eslamhamdi.paging3_todo.databinding.FragmentRxRemoteMediatorBinding




class RxRemoteMediatorFragment: Fragment() {
    private lateinit var binding: FragmentRxRemoteMediatorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRxRemoteMediatorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
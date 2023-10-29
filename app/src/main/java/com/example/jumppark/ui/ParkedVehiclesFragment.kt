package com.example.jumppark.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jumppark.R
import com.example.jumppark.databinding.FragmentParkedVehiclesFragmentsBinding
import com.example.jumppark.ui.adapters.ParkedRecyclerViewAdapter

class ParkedVehiclesFragment : BaseFragment() {
    lateinit var parkedAdapter: ParkedRecyclerViewAdapter
    lateinit var binding: FragmentParkedVehiclesFragmentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBarTitle(getString(R.string.parked_vehicles_toolbar_title))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_parked_vehicles_fragments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentParkedVehiclesFragmentsBinding.bind(view)
        loadParkedLocalData()
        baseViewModel.loadedDataParkedList.observe(viewLifecycleOwner, Observer { loaded ->
            if (loaded) {
                configRecyclerView()
                popularRecyclerView()
                hideProgressbar()
            }
        })

    }

    private fun popularRecyclerView() {
        baseViewModel.parkedVouchers.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                parkedAdapter.differ.submitList(list)
            }
        })
    }


    private fun configRecyclerView() {
        parkedAdapter = ParkedRecyclerViewAdapter()
        binding.rvParkedVehicles.apply {
            adapter = parkedAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        parkedAdapter.setOnClickListener { voucher ->
            findNavController().navigate(
                ParkedVehiclesFragmentDirections.actionParkedVehiclesFragmentsToVehicleDetailsFragment(
                    voucher
                )
            )
        }
    }


}
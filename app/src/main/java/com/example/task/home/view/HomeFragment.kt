package com.example.task.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.task.R
import com.example.task.databinding.FragmentHomeBinding
import com.example.task.home.viewmodel.HomeViewModel
import com.example.task.model.TrainingSeries
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso


class HomeFragment : Fragment() {


    private lateinit var viewModel: HomeViewModel

    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.catTabLayout.addTab(binding.catTabLayout.newTab().setText("OVERVIEW"))
        binding.catTabLayout.addTab(binding.catTabLayout.newTab().setText("CLASSES"))
        binding.catTabLayout.addTab(binding.catTabLayout.newTab().setText("COMMUNITY"))

        viewModel.trainingSeriesLiveData.observe(viewLifecycleOwner, { trainingSeries ->
            trainingSeries?.let {
                // Set data to views
                Picasso.get().load(trainingSeries.coverPhoto).into(binding.imgSeries)
                binding.seriesName.text = trainingSeries.seriesName

                val coaches = trainingSeries.coaches.joinToString { it.coachName }
                binding.coachName.text = coaches

                // Set initial content based on the selected tab
                updateContentForSelectedTab(binding.catTabLayout.selectedTabPosition, trainingSeries)
            }
        })

        viewModel.fetchTrainingSeries(requireContext())

        binding.catTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { position ->
                    viewModel.trainingSeriesLiveData.value?.let { trainingSeries ->
                        updateContentForSelectedTab(position, trainingSeries)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Not needed for this example
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Not needed for this example
            }
        })
    }

    private fun updateContentForSelectedTab(position: Int, trainingSeries: TrainingSeries) {
        when (position) {
            0 -> binding.desc.text = trainingSeries.overviewSection.description
            1 -> binding.desc.text = "Classes content"
            2 -> binding.desc.text = "Community content"
        }
    }
}

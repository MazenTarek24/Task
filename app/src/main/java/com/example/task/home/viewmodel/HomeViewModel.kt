package com.example.task.home.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task.model.Repository.Repository
import com.example.task.model.TrainingSeries


class HomeViewModel : ViewModel() {

    private val repository = Repository()

    private val _trainingSeriesLiveData = MutableLiveData<TrainingSeries>()
    val trainingSeriesLiveData: LiveData<TrainingSeries> = _trainingSeriesLiveData

    fun fetchTrainingSeries(context: Context) {
        val trainingSeries = repository.getTrainingSeriesData(context)
        _trainingSeriesLiveData.value = trainingSeries
    }
}
package com.example.myapplication.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.Constants
import com.example.myapplication.network.response.GetLocationByIdResponse


class LocationViewModel: ViewModel() {

    private val repository = LocationsRepository()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(Constants.PAGE_SIZE)
        .setPrefetchDistance(Constants.PREFETCH_DISTANCE)
        .build()

    private val dataSourceFactory = LocationsDataSourceFactory(viewModelScope, repository)

    val locationsPagedListLiveData: LiveData<PagedList<GetLocationByIdResponse>> = LivePagedListBuilder(
        dataSourceFactory, pageListConfig
    ).build()



}

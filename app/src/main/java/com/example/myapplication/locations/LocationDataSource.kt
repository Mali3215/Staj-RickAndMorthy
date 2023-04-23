package com.example.myapplication.locations

import androidx.paging.PageKeyedDataSource
import com.example.myapplication.Constants
import com.example.myapplication.epoxy.LoadingEpoxyModel
import com.example.myapplication.network.response.GetLocationByIdResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class LocationDataSource (
    private val coroutineScope: CoroutineScope,
    private val repository: LocationsRepository
): PageKeyedDataSource<Int, GetLocationByIdResponse>() {



    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetLocationByIdResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getLocationsPage(1)

            if (page == null) {
                callback.onResult(emptyList(), null, null)
                return@launch
            }

            callback.onResult(page.results, null,getPageIndexFromNext(page.info.next))
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetLocationByIdResponse>
    ) {
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetLocationByIdResponse>
    ) {

        coroutineScope.launch {

            val page = repository.getLocationsPage(params.key)

            if (page == null ){
                callback.onResult(emptyList(),null)
                return@launch
            }

            callback.onResult(page.results , getPageIndexFromNext(page.info.next))
        }
    }

    private fun getPageIndexFromNext(next: String?): Int?{
        return next?.split("?page=")?.get(1)?.toInt()



    }


}
package com.shakiv.animevault.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shakiv.animevault.data.local.AnimeEntity
import com.shakiv.animevault.domain.usecase.GetTopAnimeUseCase
import com.shakiv.animevault.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class AnimeListUiState(
    val isOnline: Boolean = true,
)

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    getTopAnimeUseCase: GetTopAnimeUseCase,
    networkMonitor: NetworkMonitor
) : ViewModel() {

    val animeFlow: Flow<PagingData<AnimeEntity>> =
        getTopAnimeUseCase()
            .cachedIn(viewModelScope)

    val uiState: StateFlow<AnimeListUiState> = networkMonitor.isConnected
        .map { AnimeListUiState(isOnline = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AnimeListUiState()
        )
}


package com.shakiv.animevault.presentation.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakiv.animevault.data.common.Resource
import com.shakiv.animevault.data.mapper.toEntity
import com.shakiv.animevault.domain.usecase.GetAnimeDetailUseCase
import com.shakiv.animevault.presentation.common.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AnimeDetailState())
    val state: StateFlow<AnimeDetailState> = _state.asStateFlow()

    init {
        val animeId: Int? = savedStateHandle[Screen.ARG_ANIME_ID]

        if (animeId != null) {
            Log.d("TAG", "Anime Id : $animeId ")
            getAnimeDetail(animeId)
        } else {
            _state.value = AnimeDetailState(error = "Invalid Anime ID")
        }
    }

    private fun getAnimeDetail(id: Int) {
        getAnimeDetailUseCase(id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("TAG", "getAnimeDetail: Loading...")
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {

                    Log.d("TAG", "getAnimeDetail: Success...")

                    _state.value = AnimeDetailState(
                        anime = result.data?.toEntity(),
                        isLoading = false
                    )


                }
                is Resource.Error -> {
                    Log.d("TAG", "getAnimeDetail: Error...")

                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun retry() {
        val animeId = savedStateHandle.get<Int>("animeId")
        if (animeId != null) {
            getAnimeDetail(animeId)
        }
    }
}
package com.shakiv.animevault.domain.usecase

import com.shakiv.animevault.domain.repository.AnimeRepository
import javax.inject.Inject

class GetTopAnimeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
     operator fun invoke() = repository.getAnimeStream()
}
package com.shakiv.animevault.domain.usecase



import com.shakiv.animevault.data.common.Resource
import com.shakiv.animevault.data.model.Anime
import com.shakiv.animevault.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(animeId: Int): Flow<Resource<Anime?>> {
        return repository.getAnimeDetails(animeId)
    }
}
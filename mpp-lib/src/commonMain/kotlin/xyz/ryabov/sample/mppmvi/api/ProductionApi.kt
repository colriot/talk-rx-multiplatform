package xyz.ryabov.sample.mppmvi.api

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.ryabov.sample.data.movies

class ProductionApi : Api {
  private suspend fun searchSuspend(query: String): Movie {
    delay(1_000)
    return movies.first { it.title.contains(query, ignoreCase = true) }
  }

  private suspend fun suggestionsSuspend(query: String): List<String> {
    delay(200)
    return if (query.isBlank()) {
      emptyList()
    } else {
      movies.filter { it.title.contains(query, ignoreCase = true) }.map { it.title }
    }
  }

  override fun search(query: String): Flow<Movie> {
    return flow { emit(searchSuspend(query)) }
  }

  override fun suggestions(query: String): Flow<List<String>> {
    return flow { emit(suggestionsSuspend(query)) }
  }
}

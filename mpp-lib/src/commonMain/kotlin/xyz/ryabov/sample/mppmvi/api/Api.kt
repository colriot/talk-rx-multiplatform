package xyz.ryabov.sample.mppmvi.api

import kotlinx.coroutines.flow.Flow

interface Api {
  fun search(query: String): Flow<Movie>
  fun suggestions(query: String): Flow<List<String>>
}

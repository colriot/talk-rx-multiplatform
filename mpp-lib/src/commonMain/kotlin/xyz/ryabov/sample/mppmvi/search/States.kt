package xyz.ryabov.sample.mppmvi.search

import xyz.ryabov.sample.mppmvi.api.Movie
import xyz.ryabov.sample.mppmvi.redux.State

data class UiState(
  val loading: Boolean = false,
  val data: Movie? = null,
  val error: Throwable? = null,
  val suggestions: List<String>? = null
) : State

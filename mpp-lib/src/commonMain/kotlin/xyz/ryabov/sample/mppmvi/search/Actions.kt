package xyz.ryabov.sample.mppmvi.search

import xyz.ryabov.sample.mppmvi.api.Movie
import xyz.ryabov.sample.mppmvi.redux.Action

sealed class UiAction : Action {
  data class SearchAction(val query: String) : UiAction()
  data class LoadSuggestionsAction(val query: String) : UiAction()
}

sealed class InternalAction : Action {
  object SearchLoadingAction : InternalAction()
  data class SearchSuccessAction(val data: Movie) : InternalAction()
  data class SearchFailureAction(val error: Throwable) : InternalAction()
  data class SuggestionsLoadedAction(val suggestions: List<String>) : InternalAction()
}

package xyz.ryabov.sample.mppmvi.search

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import xyz.ryabov.sample.mppmvi.api.Api
import xyz.ryabov.sample.mppmvi.api.Movie
import xyz.ryabov.sample.mppmvi.flow.ofType
import xyz.ryabov.sample.mppmvi.flow.withLatestFrom
import xyz.ryabov.sample.mppmvi.redux.Action
import xyz.ryabov.sample.mppmvi.redux.Middleware

@ExperimentalCoroutinesApi
class SearchMiddleware(
  private val api: Api,
  private val dispatcher: CoroutineDispatcher
) : Middleware<Action, UiState> {
  override fun bind(actions: Flow<Action>, state: Flow<UiState>): Flow<Action> {
    return actions.ofType<UiAction.SearchAction>()
      .withLatestFrom(state) { action, currentState ->
        action to currentState
      }
      .flatMapLatest { (action, _) ->
        api.search(action.query)
          .map<Movie, InternalAction> { result ->
            InternalAction.SearchSuccessAction(result)
          }
          .catch { emit(InternalAction.SearchFailureAction(it)) }
          .flowOn(dispatcher)
          .onStart { emit(InternalAction.SearchLoadingAction) }
      }
  }
}

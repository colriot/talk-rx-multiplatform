package xyz.ryabov.sample.mppmvi.search

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import xyz.ryabov.sample.mppmvi.api.Api
import xyz.ryabov.sample.mppmvi.api.ProductionApi
import xyz.ryabov.sample.mppmvi.redux.Action
import xyz.ryabov.sample.mppmvi.redux.Store

private val api: Api = ProductionApi()

@FlowPreview
@ExperimentalCoroutinesApi
class SearchStore(dispatcher: CoroutineDispatcher) : Store<Action, UiState>(
  SearchReducer(),
  listOf(
    SearchMiddleware(api, dispatcher),
    SuggestionsMiddleware(api, dispatcher)
  ),
  UiState()
)

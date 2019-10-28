package xyz.ryabov.sample.mppmvi.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import xyz.ryabov.sample.mppmvi.redux.Action
import xyz.ryabov.sample.mppmvi.redux.MviView

@FlowPreview
@ExperimentalCoroutinesApi
class SearchBinder : ViewModel() {

  private val store = SearchStore(Dispatchers.IO)

  init {
    store.wire(scope = viewModelScope)
  }

  fun bind(view: MviView<Action, UiState>, uiScope: CoroutineScope) {
    store.bind(view, uiScope)
  }
}

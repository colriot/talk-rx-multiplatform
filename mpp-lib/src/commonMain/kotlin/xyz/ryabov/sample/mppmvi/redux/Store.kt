package xyz.ryabov.sample.mppmvi.redux

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import xyz.ryabov.sample.mppmvi.flow.withLatestFrom

@FlowPreview
@ExperimentalCoroutinesApi
open class Store<A : Action, S : State>(
  private val reducer: Reducer<S, A>,
  private val middlewares: List<Middleware<A, S>>,
  initialState: S
) {
  private val stateRelay = ConflatedBroadcastChannel<S>(initialState)
  private val stateRelayFlow = stateRelay.asFlow()
  private val actionsRelay = ConflatedBroadcastChannel<A>()
  private val actionsRelayFlow = actionsRelay.asFlow()

  fun wire(scope: CoroutineScope) {
    actionsRelayFlow
      .withLatestFrom(stateRelayFlow) { action, state ->
        reducer.reduce(state, action)
      }
      .distinctUntilChanged()
      .onEach {
        stateRelay.offer(it)
      }
      .launchIn(scope)

    middlewares.map { it.bind(actionsRelayFlow, stateRelayFlow) }
      .asFlow()
      .flattenMerge(middlewares.size)
      .onEach { actionsRelay.offer(it) }
      .launchIn(scope)
  }

  fun bind(view: MviView<A, S>, uiScope: CoroutineScope) {
    stateRelayFlow
      .onEach {
        view.render(it)
      }
      .launchIn(uiScope)

    view.actions
      .onEach { actionsRelay.offer(it) }
      .launchIn(uiScope)
  }
}

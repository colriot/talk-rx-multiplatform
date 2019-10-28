package xyz.ryabov.sample.mppmvi.redux

import kotlinx.coroutines.flow.Flow

interface Action
interface State

interface Reducer<S : State, A : Action> {
  fun reduce(state: S, action: A): S
}

interface Middleware<A : Action, S : State> {
  fun bind(actions: Flow<A>, state: Flow<S>): Flow<A>
}

interface MviView<A : Action, S : State> {
  val actions: Flow<A>
  fun render(state: S)
}

package xyz.ryabov.sample.mppmvi.flow

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext

internal actual val MainDispatcher: CoroutineDispatcher =
  NsQueueDispatcher(dispatch_get_main_queue())

internal class NsQueueDispatcher(private val dispatchQueue: dispatch_queue_t) :
  CoroutineDispatcher() {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    dispatch_async(dispatchQueue) {
      block.run()
    }
  }
}

class MainScope() : CoroutineScope {
  override val coroutineContext: CoroutineContext = MainDispatcher + SupervisorJob()

  fun close() {
    coroutineContext.cancel()
  }
}

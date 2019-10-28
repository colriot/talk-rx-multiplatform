package xyz.ryabov.sample.mppmvi.flow

import java.util.concurrent.atomic.AtomicReference

actual class AtomicRef<T> actual constructor(value: T) {
  private val delegate = AtomicReference<T>(value)

  actual fun set(value: T) {
    delegate.set(value)
  }

  actual fun get(): T {
    return delegate.get()
  }
}

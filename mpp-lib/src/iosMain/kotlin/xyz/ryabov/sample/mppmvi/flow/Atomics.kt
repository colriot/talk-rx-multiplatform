package xyz.ryabov.sample.mppmvi.flow

import kotlin.native.concurrent.AtomicReference

actual class AtomicRef<T> actual constructor(value: T) {
  private val delegate = AtomicReference<T>(value)

  actual fun set(value: T) {
    delegate.value = value
  }

  actual fun get(): T {
    return delegate.value
  }
}

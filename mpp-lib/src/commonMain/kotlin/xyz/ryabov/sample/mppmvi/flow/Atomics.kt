package xyz.ryabov.sample.mppmvi.flow

expect class AtomicRef<T>(value: T) {
  fun set(value: T)
  fun get(): T
}

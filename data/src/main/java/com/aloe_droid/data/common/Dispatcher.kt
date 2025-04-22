package com.aloe_droid.data.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: DispatcherType)

enum class DispatcherType {
    IO, DEFAULT,
}

package com.shreyas.squaretakehomeapp.di.annotations

import javax.inject.Scope

/**
 *  Custom scope for Activity retention
 */
@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ActivityScope()

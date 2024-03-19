package com.kuvalin.brainstorm.extentions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

// Эта хрень нужна, чтобы слепить 2 потока
fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
    return merge(this, another)
}
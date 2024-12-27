package raf.console.chitalka.presentation.core.util

fun <T> MutableList<T>.addAll(calculation: () -> List<T>) {
    addAll(calculation())
}
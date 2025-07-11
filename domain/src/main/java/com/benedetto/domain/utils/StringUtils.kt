package com.benedetto.domain.utils

fun String.removeNewLines(): String{
    return this.replace(Regex("[\n\r]"), "")
}
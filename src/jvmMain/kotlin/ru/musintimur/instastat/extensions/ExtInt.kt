package ru.musintimur.instastat.extensions

fun Int.padZeros(length: Int): String =
    this.toString().padStart(length, '0')
package es.iessaladillo.rafamartinez.starwarsapp.utils

fun String.extractId(): String = trimEnd('/').substringAfterLast("/")

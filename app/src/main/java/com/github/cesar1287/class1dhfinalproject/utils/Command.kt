package com.github.cesar1287.class1dhfinalproject.utils

sealed class Command {
    class Loading(val value: Boolean): Command()
    class Error(val error: Int): Command()
}
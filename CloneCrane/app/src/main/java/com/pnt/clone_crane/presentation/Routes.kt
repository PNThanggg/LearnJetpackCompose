package com.pnt.clone_crane.presentation

sealed class Routes(val route: String) {
    object Main : Routes("main")
    object Calendar : Routes("calendar")
}
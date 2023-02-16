package com.app.ecitizen.data.network.exception

import java.io.IOException

class NetworkNotConnectedException : IOException() {
    // You can send any message whatever you want from here.
    override val message: String
        get() = "Please check your internet connection. Please try again."
}

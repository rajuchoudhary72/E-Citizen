package com.app.ecitizen.utils

import kotlinx.coroutines.flow.Flow

/**
 * Utility for reporting app connectivity status
 */
interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}

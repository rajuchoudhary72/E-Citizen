package com.app.ecitizen.model

enum class UserType(val id: String) {
    CITIZEN("1"),
    SERVICE_PROVIDER("3");

    companion object {
        fun getUserType(id: String?) = values().firstOrNull { it.id == id } ?: CITIZEN
    }
}
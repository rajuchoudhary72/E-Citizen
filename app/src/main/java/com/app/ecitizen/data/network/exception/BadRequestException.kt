package com.app.ecitizen.data.network.exception

import com.app.ecitizen.data.network.dto.ApiError


class BadRequestException(val apiError: ApiError) : Throwable(apiError.message)

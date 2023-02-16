package com.app.ecitizen.data.network.exception

import com.app.ecitizen.data.network.dto.ApiError
import java.io.IOException


class BadRequestException(val apiError: ApiError) : IOException(apiError.message)

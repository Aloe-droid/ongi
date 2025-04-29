package com.aloe_droid.domain.exception

class LocationPermissionException(message: String = ERROR_MESSAGE) : RuntimeException(message) {
    companion object {
        const val ERROR_MESSAGE: String = "위치 권한을 확인해 주세요."
    }
}

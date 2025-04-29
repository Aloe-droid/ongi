package com.aloe_droid.domain.exception

class LocationUnavailableException(message: String = ERROR_MESSAGE) : RuntimeException(message) {
    companion object {
        private const val ERROR_MESSAGE: String = "위치 정보를 사용할 수 없습니다."
    }
}

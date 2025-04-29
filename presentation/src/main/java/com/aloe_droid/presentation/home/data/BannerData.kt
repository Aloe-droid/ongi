package com.aloe_droid.presentation.home.data

import androidx.compose.runtime.Stable
import com.aloe_droid.domain.entity.Banner

@Stable
data class BannerData(val url: String, val imageUrl: String) {
    companion object {
        fun List<Banner>.toBannerDataList(): List<BannerData> = map { banner: Banner ->
            BannerData(
                url = banner.url,
                imageUrl = banner.imageUrl
            )
        }
    }
}

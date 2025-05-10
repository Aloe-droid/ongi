package com.aloe_droid.presentation.map.component

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.aloe_droid.presentation.map.data.StoreMapData
import com.naver.maps.map.NaverMap
import com.naver.maps.map.compose.DisposableMapEffect
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import ted.gun0912.clustering.naver.TedNaverClustering

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun OnGiStoreMapDataEffect(
    storeItems: List<StoreMapData>,
    onMarkerClick: (StoreMapData) -> Unit
) {
    val context: Context = LocalContext.current
    val (clusterManager, setClusterManager) = remember {
        mutableStateOf<TedNaverClustering<StoreMapData>?>(null)
    }

    DisposableMapEffect(key1 = storeItems) { map: NaverMap ->
        if (clusterManager == null) {
            TedNaverClustering
                .with<StoreMapData>(context = context, map = map)
                .markerClickListener(listener = onMarkerClick)
                .make()
                .also { setClusterManager(it) }
                .apply { addItems(storeItems) }
        }

        clusterManager?.clearItems()
        clusterManager?.addItems(storeItems)

        onDispose {
            clusterManager?.clearItems()
        }
    }
}

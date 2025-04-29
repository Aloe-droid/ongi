package com.aloe_droid.presentation.home.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.theme.DefaultDelayTime
import com.aloe_droid.presentation.base.theme.DefaultDurationTime
import com.aloe_droid.presentation.base.theme.DefaultImageRatio
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.SelectedDotColor
import com.aloe_droid.presentation.base.theme.SmallPadding
import com.aloe_droid.presentation.base.theme.UnSelectedDotColor
import com.aloe_droid.presentation.home.data.BannerData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BannerPager(
    modifier: Modifier = Modifier,
    imageRatio: Float = DefaultImageRatio,
    bannerAdvanceTime: Long = DefaultDelayTime,
    bannerDurationTime: Int = DefaultDurationTime,
    dotSize: Dp = DefaultPadding,
    dotSpacing: Dp = SmallPadding,
    selectedColor: Color = SelectedDotColor,
    unselectedColor: Color = UnSelectedDotColor,
    bannerList: List<BannerData>,
    selectBanner: (BannerData) -> Unit
) {
    val pagerState: PagerState = rememberPagerState { bannerList.size }
    val pageInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    AutoScrollPager(
        pagerState = pagerState,
        pageInteractionSource = pageInteractionSource,
        bannerSize = bannerList.size,
        bannerAdvanceTime = bannerAdvanceTime,
        bannerDurationTime = bannerDurationTime
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BannerHorizontalPager(
            pagerState = pagerState,
            pageInteractionSource = pageInteractionSource,
            imageRatio = imageRatio,
            bannerList = bannerList,
            selectBanner = selectBanner
        )

        Spacer(modifier = Modifier.height(height = DefaultPadding))

        BannerPageIndicator(
            bannerSize = bannerList.size,
            focusedBannerIndex = pagerState.currentPage,
            dotSize = dotSize,
            dotSpacing = dotSpacing,
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            animateToPage = { page: Int ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(page = page)
                }
            }
        )
    }
}

@Composable
private fun AutoScrollPager(
    pagerState: PagerState,
    pageInteractionSource: MutableInteractionSource,
    bannerSize: Int,
    bannerAdvanceTime: Long,
    bannerDurationTime: Int
) {
    val pagerIsDragged: Boolean by pagerState.interactionSource.collectIsDraggedAsState()
    val pageIsPressed: Boolean by pageInteractionSource.collectIsPressedAsState()
    val autoAdvance: Boolean = !pagerIsDragged && !pageIsPressed

    if (autoAdvance) {
        LaunchedEffect(key1 = pagerState, key2 = pageInteractionSource) {
            while (true) {
                delay(timeMillis = bannerAdvanceTime)
                val size: Int = bannerSize.coerceAtLeast(1)
                val nextPage: Int = (pagerState.currentPage + 1) % size
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(durationMillis = bannerDurationTime)
                )
            }
        }
    }
}

@Composable
private fun BannerHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pageInteractionSource: MutableInteractionSource,
    imageRatio: Float,
    bannerList: List<BannerData>,
    selectBanner: (BannerData) -> Unit
) {
    HorizontalPager(state = pagerState) { page: Int ->
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(ratio = imageRatio)
                .clickable(
                    onClick = { selectBanner(bannerList[page]) },
                    interactionSource = pageInteractionSource,
                    indication = null
                ),
            model = bannerList[page].imageUrl,
            contentDescription = bannerList[page].url,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.default_banner)
        )
    }
}

@Composable
private fun BannerPageIndicator(
    modifier: Modifier = Modifier,
    bannerSize: Int,
    focusedBannerIndex: Int,
    animateToPage: (Int) -> Unit,
    dotSize: Dp,
    dotSpacing: Dp,
    selectedColor: Color,
    unselectedColor: Color
) {
    Row(
        modifier = modifier.padding(horizontal = DefaultPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(times = bannerSize) { index: Int ->
            val color: Color = if (index == focusedBannerIndex) selectedColor else unselectedColor
            Box(
                modifier = Modifier
                    .padding(horizontal = dotSpacing)
                    .size(size = dotSize)
                    .clip(shape = CircleShape)
                    .background(color = color)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { animateToPage(index) }
                    )
            )
        }
    }
}

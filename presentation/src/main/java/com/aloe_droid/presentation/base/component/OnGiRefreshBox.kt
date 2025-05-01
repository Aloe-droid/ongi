package com.aloe_droid.presentation.base.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefreshIndicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnGiRefreshBox(
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = state,
        content = content,
        indicator = {
            CustomPulsingIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                state = state
            )
        }
    )
}

// 상수 정의
private val SpinnerSize = 40.dp
private val StrokeWidth = 3.dp
private const val CrossfadeDurationMs = 100

/**
 * 펄싱 효과가 있는 커스텀 PullToRefresh 인디케이터
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPulsingIndicator(
    state: PullToRefreshState,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    containerColor: Color = PullToRefreshDefaults.containerColor,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    secondaryColor: Color = MaterialTheme.colorScheme.secondary,
    strokeWidth: Float = StrokeWidth.value,
) {
    Box(
        modifier = modifier.pullToRefreshIndicator(
            state = state,
            isRefreshing = isRefreshing,
            containerColor = containerColor,
        ),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            targetState = isRefreshing,
            animationSpec = tween(durationMillis = CrossfadeDurationMs)
        ) { refreshing ->
            if (refreshing) {
                PulsingCircularIndicator(
                    primaryColor = primaryColor,
                    secondaryColor = secondaryColor,
                    strokeWidth = strokeWidth
                )
            } else {
                PulsingArrowIndicator(
                    progress = { state.distanceFraction },
                    primaryColor = primaryColor,
                    secondaryColor = secondaryColor,
                    strokeWidth = strokeWidth
                )
            }
        }
    }
}

/**
 * 펄싱 효과가 있는 원형 인디케이터 컴포넌트
 */
@Composable
fun PulsingCircularIndicator(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    secondaryColor: Color,
    strokeWidth: Float = StrokeWidth.value
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulsing_indicator")

    // 크기 애니메이션
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // 투명도 애니메이션
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier
            .size(SpinnerSize)
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        // 배경 원
        Canvas(modifier = Modifier.size(SpinnerSize)) {
            drawCircle(
                color = secondaryColor,
                style = Stroke(width = strokeWidth)
            )
        }

        // 펄스 효과가 있는 원
        Canvas(modifier = Modifier.size(SpinnerSize * scale)) {
            drawCircle(
                color = primaryColor.copy(alpha = alpha),
                style = Stroke(width = strokeWidth * scale)
            )
        }

        // 중앙 점
        Canvas(modifier = Modifier.size(SpinnerSize / 3 * scale)) {
            drawCircle(
                color = primaryColor.copy(alpha = alpha),
            )
        }
    }
}

/**
 * 당기는 정도에 따라 변화하는 펄싱 화살표 인디케이터
 */
@Composable
fun PulsingArrowIndicator(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    primaryColor: Color,
    secondaryColor: Color,
    strokeWidth: Float = StrokeWidth.value
) {
    // 당기는 정도에 따라 스케일 및 알파값 조정
    val scale = (progress().coerceIn(0f, 1f) * 0.4f + 0.6f).coerceIn(0.6f, 1f)
    val alpha = progress().coerceIn(0f, 1f)

    // 당기는 정도에 따라 회전 조정
    val rotation = progress().coerceIn(0f, 1f) * 360f

    // 화살표 경로
    val arrowPath = remember { Path().apply { fillType = PathFillType.EvenOdd } }

    Canvas(
        modifier = modifier
            .size(SpinnerSize)
            .semantics(mergeDescendants = true) {
                progressBarRangeInfo = ProgressBarRangeInfo(progress(), 0f..1f, 0)
            }
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        // 배경 원
        drawCircle(
            color = secondaryColor,
            radius = size.minDimension / 2 * scale,
            style = Stroke(width = strokeWidth)
        )

        // 회전 적용
        rotate(degrees = rotation) {
            // 원호 그리기
            val arcRadius = size.minDimension / 2 - strokeWidth / 2

            // 당기는 정도에 따라 원호의 각도 증가
            val sweepAngle = progress().coerceIn(0f, 1f) * 270f

            drawArc(
                color = primaryColor.copy(alpha = alpha),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // 화살표 그리기 (당기는 정도가 일정 수준 이상일 때만)
            if (progress() > 0.3f) {
                val arrowSize = size.minDimension * 0.2f * alpha

                // 화살표 위치 조정 - 원 안쪽으로 충분히 들어오도록 설정
                // 원의 가장자리에서 화살표 크기의 절반만큼 더 안쪽으로 이동
                val insetDistance = arrowSize * 0.7f
                val arrowCenter = Offset(
                    x = size.center.x,
                    y = size.center.y - arcRadius + insetDistance
                )

                // 화살표 경로 업데이트
                arrowPath.reset()
                arrowPath.moveTo(arrowCenter.x, arrowCenter.y - arrowSize / 2)
                arrowPath.lineTo(arrowCenter.x - arrowSize / 2, arrowCenter.y + arrowSize / 2)
                arrowPath.lineTo(arrowCenter.x + arrowSize / 2, arrowCenter.y + arrowSize / 2)
                arrowPath.close()

                // 화살표 그리기
                drawPath(
                    path = arrowPath,
                    color = primaryColor.copy(alpha = alpha),
                )
            }
        }

        // 중앙 점
        if (progress() >= 0.8f) {
            drawCircle(
                color = primaryColor.copy(alpha = alpha),
                radius = size.minDimension / 8 * scale
            )
        }
    }
}

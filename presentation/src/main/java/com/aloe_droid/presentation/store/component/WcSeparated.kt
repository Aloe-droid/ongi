package com.aloe_droid.presentation.store.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val Icons.Outlined.WcSeparated: ImageVector
    get() {
        if (_wcSeparated != null) {
            return _wcSeparated!!
        }
        _wcSeparated = materialIcon(name = "Outlined.WcSeparated") {
            materialPath {
                moveTo(3.5f, 22.0f)
                verticalLineToRelative(-7.5f)
                lineTo(2.0f, 14.5f)
                lineTo(2.0f, 9.0f)
                curveToRelative(0.0f, -1.1f, 0.9f, -2.0f, 2.0f, -2.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, 0.9f, 2.0f, 2.0f)
                verticalLineToRelative(5.5f)
                lineTo(7.5f, 14.5f)
                lineTo(7.5f, 22.0f)
                horizontalLineToRelative(-4.0f)
                close()

                moveTo(5.5f, 6.0f)
                curveToRelative(1.11f, 0.0f, 2.0f, -0.89f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.89f, -2.0f, -2.0f, -2.0f)
                reflectiveCurveToRelative(-2.0f, 0.89f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.89f, 2.0f, 2.0f, 2.0f)
                close()

                moveTo(12.0f, 7.0f)
                verticalLineTo(22.0f)
                horizontalLineToRelative(-1f)
                verticalLineTo(7.0f)
                horizontalLineToRelative(1f)
                close()

                moveTo(20.0f, 22.0f)
                verticalLineToRelative(-6.0f)
                horizontalLineToRelative(2.5f)
                lineToRelative(-2.04f, -7.63f)
                curveTo(20.18f, 7.55f, 19.42f, 7.0f, 18.56f, 7.0f)
                horizontalLineToRelative(-0.12f)
                curveToRelative(-0.86f, 0.0f, -1.63f, 0.55f, -1.9f, 1.37f)
                lineTo(14.5f, 16.0f)
                horizontalLineToRelative(2.5f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(3.0f)
                close()

                moveTo(18.5f, 6.0f)
                curveToRelative(1.11f, 0.0f, 2.0f, -0.89f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.89f, -2.0f, -2.0f, -2.0f)
                reflectiveCurveToRelative(-2.0f, 0.89f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.89f, 2.0f, 2.0f, 2.0f)
                close()
            }
        }
        return _wcSeparated!!
    }

private var _wcSeparated: ImageVector? = null

package com.aloe_droid.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.aloe_droid.presentation.base.component.Category
import com.aloe_droid.presentation.base.theme.DefaultGridCellSize
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.LargePadding
import com.aloe_droid.presentation.base.theme.MaxGridHeight
import com.aloe_droid.presentation.home.data.CategoryData
import com.aloe_droid.presentation.home.data.CategoryData.Companion.CategoryList

@Composable
fun CategoryGrid(
    modifier: Modifier = Modifier,
    maxGridHeight: Dp = MaxGridHeight,
    spaceSize: Dp = LargePadding,
    categoryList: List<CategoryData>,
    selectCategory: (CategoryData) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier
            .padding(horizontal = DefaultPadding)
            .heightIn(max = maxGridHeight),
        columns = GridCells.Fixed(count = DefaultGridCellSize),
        verticalArrangement = Arrangement.spacedBy(space = spaceSize)
    ) {
        items(
            items = categoryList,
            key = CategoryData::hashCode
        ) { categoryData: CategoryData ->
            Category(
                imageRes = categoryData.imageRes,
                name = stringResource(id = categoryData.nameRes),
                onClick = { selectCategory(categoryData) }
            )
        }
    }
}

@Composable
@Preview
fun CategoryGridPreview() {
    CategoryGrid(categoryList = CategoryList, selectCategory = {})
}
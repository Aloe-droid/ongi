package com.aloe_droid.presentation.search.contract

import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.search.data.SearchedStore

sealed class SearchEvent : UiContract.Event {
    data object LoadEvent : SearchEvent()
    data class ChangeQuery(val query: String) : SearchEvent()
    data class SearchQuery(val query: String) : SearchEvent()
    data class DeleteQuery(val id: Long) : SearchEvent()
    data object DeleteAllQuery : SearchEvent()
    data class SelectStore(val store: SearchedStore) : SearchEvent()
    data object NavigateUpEvent : SearchEvent()
}

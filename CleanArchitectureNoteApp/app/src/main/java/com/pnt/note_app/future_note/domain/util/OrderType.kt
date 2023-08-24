package com.pnt.note_app.future_note.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
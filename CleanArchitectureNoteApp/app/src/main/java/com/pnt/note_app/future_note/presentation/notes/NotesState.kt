package com.pnt.note_app.future_note.presentation.notes

import com.pnt.note_app.future_note.domain.model.Note
import com.pnt.note_app.future_note.domain.util.NoteOrder
import com.pnt.note_app.future_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
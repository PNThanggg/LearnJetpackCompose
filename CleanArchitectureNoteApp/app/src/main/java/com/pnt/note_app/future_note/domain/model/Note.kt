package com.pnt.note_app.future_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pnt.note_app.ui.theme.Pink80
import com.pnt.note_app.ui.theme.Purple40
import com.pnt.note_app.ui.theme.Purple80
import com.pnt.note_app.ui.theme.PurpleGrey80

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(Purple80, PurpleGrey80, Pink80, Purple40)
    }
}

class InvalidNoteException(message: String) : Exception(message)
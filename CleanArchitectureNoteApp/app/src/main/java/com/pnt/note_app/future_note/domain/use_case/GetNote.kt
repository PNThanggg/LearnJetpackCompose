package com.pnt.note_app.future_note.domain.use_case

import com.pnt.note_app.future_note.domain.model.Note
import com.pnt.note_app.future_note.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}
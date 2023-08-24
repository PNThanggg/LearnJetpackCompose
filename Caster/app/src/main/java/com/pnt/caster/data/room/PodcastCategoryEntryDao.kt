package com.pnt.caster.data.room

import androidx.room.Dao
import com.pnt.caster.data.model.PodcastCategoryEntry

/**
 * [Room] DAO for [PodcastCategoryEntry] related operations.
 */
@Dao
abstract class PodcastCategoryEntryDao : BaseDao<PodcastCategoryEntry>

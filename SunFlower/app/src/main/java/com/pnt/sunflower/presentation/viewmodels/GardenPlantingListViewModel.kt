package com.pnt.sunflower.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.pnt.sunflower.data.local.model.PlantAndGardenPlantings
import com.pnt.sunflower.data.local.repository.GardenPlantingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GardenPlantingListViewModel(
    gardenPlantingRepository: GardenPlantingRepository
) : ViewModel() {
    val plantAndGardenPlantings: Flow<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens()
}
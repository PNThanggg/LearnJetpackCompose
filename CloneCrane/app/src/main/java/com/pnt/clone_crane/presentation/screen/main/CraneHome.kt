package com.pnt.clone_crane.presentation.screen.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.runtime.livedata.observeAsState
import com.pnt.clone_crane.data.ExploreModel
import com.pnt.clone_crane.presentation.screen.main.compose.CraneDrawer

typealias OnExploreItemClicked = (ExploreModel) -> Unit

enum class CraneScreen {
    Fly, Sleep, Eat
}

@Composable
fun CraneHome(
    widthSize: WindowWidthSizeClass,
    onExploreItemClicked: OnExploreItemClicked,
    onDateSelectionClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.statusBarsPadding(),
        drawerContent = {
            CraneDrawer()
        }
    ) { contentPadding ->
        val scope = rememberCoroutineScope()

        CraneHomeContent(
            modifier = modifier.padding(contentPadding),
            widthSize = widthSize,
//            onExploreItemClicked = onExploreItemClicked,
//            onDateSelectionClicked = onDateSelectionClicked,
//            openDrawer = {
//                scope.launch {
//                    scaffoldState.drawerState.open()
//                }
//            },
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CraneHomeContent(
    widthSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val suggestedDestinations by viewModel.suggestedDestinations.observeAsState()

    val onPeopleChanged: (Int) -> Unit = { viewModel.updatePeople(it) }
    val craneScreenValues = CraneScreen.values()
    val pagerState =
        rememberPagerState(initialPage = CraneScreen.Fly.ordinal) { craneScreenValues.size }

    val coroutineScope = rememberCoroutineScope()
}
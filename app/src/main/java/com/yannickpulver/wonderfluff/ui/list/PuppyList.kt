/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yannickpulver.wonderfluff.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.yannickpulver.wonderfluff.R
import com.yannickpulver.wonderfluff.domain.Puppy
import com.yannickpulver.wonderfluff.ui.core.VerticalGrid
import com.yannickpulver.wonderfluff.ui.theme.Blue500
import com.yannickpulver.wonderfluff.ui.theme.Purple500
import com.yannickpulver.wonderfluff.ui.theme.White
import com.yannickpulver.wonderfluff.ui.theme.WonderfluffTheme
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch

@Composable
fun PuppyList(viewModel: PuppyListViewModel, navController: NavHostController) {
    val puppies: List<Puppy> by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    val showExpandedButton = scrollState.value == scrollState.maxValue

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        topBar = { PuppyAppBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        when (scaffoldState.bottomSheetState.currentValue) {
                            BottomSheetValue.Collapsed -> scaffoldState.bottomSheetState.expand()
                            BottomSheetValue.Expanded -> scaffoldState.bottomSheetState.collapse()
                        }
                    }
                },
                modifier = Modifier
                    .navigationBarsPadding()
            ) {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    when (scaffoldState.bottomSheetState.currentValue) {
                        BottomSheetValue.Collapsed -> Icon(imageVector = Icons.Filled.FilterList, contentDescription = "Open Filters")
                        BottomSheetValue.Expanded -> Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                    }

                    AnimatedVisibility(visible = showExpandedButton) {
                        Text(
                            text = "Filters",
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        sheetContent = {
            Column(Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(100.dp))
                Text(text = "Hi")
                Spacer(modifier = Modifier.height(100.dp))
            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 56.dp,

        ) {
        Box {
            PuppyList(puppies, navController, scrollState)
        }
    }
}

@Composable
private fun PuppyAppBar() {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Wonderfluff", style = MaterialTheme.typography.h4)
            }
        },
        modifier = Modifier.statusBarsPadding(),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
private fun PuppyList(
    puppies: List<Puppy>,
    navController: NavHostController,
    scrollState: ScrollState = rememberScrollState()
) {
    VerticalGrid(
        Modifier
            .verticalScroll(scrollState)
            .padding(bottom = 88.dp)
    ) {
        puppies.forEachIndexed { index, puppy ->
            val even = index % 2 == 0
            val startPadding = if (even) 16.dp else 8.dp
            val endPadding = if (even) 8.dp else 16.dp

            PuppyItem(
                puppy,
                modifier = Modifier
                    .padding(start = startPadding, end = endPadding, bottom = 24.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("puppyDetail/${puppy.id}")
                    }
            )
        }
    }
}

@Composable
fun PuppyItem(puppy: Puppy, modifier: Modifier = Modifier) {
    val sound by remember {
        mutableStateOf(
            listOf("Bark", "Woff", "Rawr", "Moompf").shuffled().first()
        )
    }

    Card(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painterResource(puppy.imageRes),
                contentDescription = "Image of ${puppy.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    puppy.name,
                    style = MaterialTheme.typography.h4,
                )
                Text(
                    sound,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .alpha(0.7f)
                        .padding(bottom = 8.dp)
                )
                GenderTag(puppy.gender)
            }
        }
    }
}

@Composable
private fun GenderTag(gender: String) {
    Text(
        text = gender,
        style = MaterialTheme.typography.caption,
        color = White,
        modifier = Modifier
            .background(
                if (gender == "Female") Purple500 else Blue500,
                RoundedCornerShape(12.dp)
            )
            .padding(start = 8.dp, end = 8.dp, top = 3.dp, bottom = 4.dp)

    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPuppyItem() {
    WonderfluffTheme {
        PuppyItem(
            Puppy(
                7,
                R.drawable.puppy7,
                "Yodaag",
                0.2f,
                0.2f,
                1.0f,
                "When you look at the bark side, careful you must be. For the bark side looks back.",
                "Male"
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPuppyItemDark() {
    WonderfluffTheme(darkTheme = true) {
        PuppyItem(
            Puppy(
                3,
                R.drawable.puppy3,
                "Fat Plush",
                0.8f,
                0.9f,
                0.5f,
                "Legends say, his grand-dad was a lion. Fat plush has the fines furr, making him a perfect cuddle partner. But he can get wild — like a lion!",
                "Female"
            )
        )
    }
}

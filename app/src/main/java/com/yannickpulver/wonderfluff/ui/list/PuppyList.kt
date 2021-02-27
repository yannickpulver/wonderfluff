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

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.yannickpulver.wonderfluff.ui.theme.WonderfluffTheme
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun PuppyList(viewModel: PuppyListViewModel, navController: NavHostController) {
    val puppies: List<Puppy> by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
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
    ) {
        PuppyList(puppies, navController)
    }
}

@Composable
private fun PuppyList(
    puppies: List<Puppy>,
    navController: NavHostController
) {
    VerticalGrid(
        Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        puppies.forEachIndexed { index, puppy ->
            val even = index % 2 == 0
            val startPadding = if (even) 0.dp else 8.dp
            val endPadding = if (even) 8.dp else 0.dp

            PuppyItem(
                puppy,
                modifier = Modifier
                    .padding(start = startPadding, end = endPadding, bottom = 24.dp)
                    .clickable {
                        navController.navigate("puppyDetail/${puppy.id}")
                    }
                    .fillMaxWidth()
                    .animateContentSize()
            )
        }
    }
}

@Composable
fun PuppyItem(puppy: Puppy, modifier: Modifier = Modifier) {
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
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                Icon(
                    Icons.Filled.Pets,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .alpha(0.4f)
                )
                Text(
                    puppy.name,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
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
                "When you look at the bark side, careful you must be. For the bark side looks back."
            )
        )
    }
}

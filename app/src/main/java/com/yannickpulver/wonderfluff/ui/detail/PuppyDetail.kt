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
package com.yannickpulver.wonderfluff.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.yannickpulver.wonderfluff.R
import com.yannickpulver.wonderfluff.domain.Puppy
import com.yannickpulver.wonderfluff.ui.theme.White
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding


@Composable
fun PuppyDetail(id: Int?, viewModel: PuppyDetailViewModel, navController: NavHostController) {
    if (id != null) {
        viewModel.getPuppy(id)
    } else {
        navController.navigateUp()
    }

    val puppy: Puppy? by viewModel.state.collectAsState()
    val adopted: Boolean by viewModel.adopted.collectAsState()
    val reset: Boolean by viewModel.reset.collectAsState()

    if (reset) {
        navController.navigateUp()
    }

    puppy?.let {
        PuppyContent(viewModel, navController, it)
        if (adopted) {
            AdoptionDialog(viewModel, it)
        }
    } ?: Loader()
}

@Composable
fun PuppyContent(viewModel: PuppyDetailViewModel, navController: NavHostController, puppy: Puppy) {
    val scrollState = rememberScrollState()
    val showExpandedButton = scrollState.value == scrollState.maxValue

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            puppy.name,
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        // below line is use to
                        // specify navigation icon.
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                modifier = Modifier.statusBarsPadding(),
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.background
            )
        },
        floatingActionButton = {
            AdoptButton(puppy, showExpandedButton) { viewModel.adopt(puppy.id) }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
                .verticalScroll(scrollState)
        ) {
            PuppyImage(puppy)
            Spacer(modifier = Modifier.height(16.dp))
            LookingForAPlace()
            Spacer(modifier = Modifier.height(16.dp))
            PawerfulFact(puppy)
            Spacer(modifier = Modifier.height(16.dp))
            Pupistics(puppy)
            Spacer(modifier = Modifier.height(102.dp))
        }
    }
}

@Composable
private fun AdoptButton(
    puppy: Puppy,
    expanded: Boolean,
    onClick: () -> Unit
) {
    FloatingActionButton(contentColor = White,
        modifier = Modifier
            .navigationBarsPadding(), onClick = { onClick() }) {
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Icon(Icons.Filled.Home, "Adopt")
            AnimatedVisibility(visible = expanded) {
                Text(
                    text = "Adobt ${puppy.name}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun Pupistics(puppy: Puppy) {
    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.pupistics_title),
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(R.string.barkability))
            LinearProgressIndicator(
                progress = puppy.barkability,
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 8.dp)
            )
            Text(text = stringResource(R.string.cuddliness))
            LinearProgressIndicator(
                progress = puppy.cuddliness,
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 8.dp)
            )
            Text(text = stringResource(R.string.guardability))
            LinearProgressIndicator(
                progress = puppy.guardability,
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
fun AdoptionDialog(viewModel: PuppyDetailViewModel, puppy: Puppy) {
    AlertDialog(
        onDismissRequest = { viewModel.reset() },
        title = { Text(text = "Congratulations.", style = MaterialTheme.typography.h4) },
        text = { Text(text = "You successfully adopted ${puppy.name} in a parallel universe!") },
        confirmButton = {
            TextButton(onClick = { viewModel.reset() }) {
                Text(text = "Cool!")
            }
        }
    )
}

@Composable
private fun LookingForAPlace() {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp)) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .alpha(0.4f)
            )
            Column {
                Text(stringResource(R.string.looking_4_place), style = MaterialTheme.typography.h4)
            }
        }
    }
}

@Composable
private fun PawerfulFact(puppy: Puppy) {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp)) {
            Icon(
                Icons.Filled.Pets,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .alpha(0.4f)
            )
            Column {
                Text(stringResource(R.string.pawerful_fact), style = MaterialTheme.typography.h4)
                Text(text = puppy.pawfulFact)
            }
        }
    }
}

@Composable
private fun PuppyImage(puppy: Puppy) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painterResource(puppy.imageRes),
            contentDescription = "Image of ${puppy.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
    }
}

@Composable
private fun Loader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

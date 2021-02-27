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

import com.yannickpulver.wonderfluff.domain.Puppy
import com.yannickpulver.wonderfluff.domain.PuppyRepository
import com.yannickpulver.wonderfluff.ui.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PuppyListViewModel @Inject constructor(val repo: PuppyRepository) : BaseViewModel() {

    private val _state = MutableStateFlow<List<Puppy>>(listOf())
    val state: StateFlow<List<Puppy>> = _state

    init {
        ioScope.launch { _state.emit(repo.getPuppies().shuffled()) }
    }
}

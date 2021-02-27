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
package com.yannickpulver.wonderfluff.data

import com.yannickpulver.wonderfluff.R
import com.yannickpulver.wonderfluff.domain.Puppy
import com.yannickpulver.wonderfluff.domain.PuppyRepository
import javax.inject.Inject

class PuppyRepositoryImpl @Inject constructor() : PuppyRepository {
    override fun getPuppies(): List<Puppy> {
        return puppies
    }

    override fun getPuppy(id: Int): Puppy? {
        val puppy = puppies.firstOrNull { it.id == id }
        return puppy
    }
}

private val puppies = listOf(
    Puppy(1, R.drawable.puppy1, "Mr. Jacksen", 0.9f, 0.5f, 0.3f, "A very classy, yet chilled pup. However, if his shirts are not perfectly made, he will back louder than you think. But other than that — the classiest of them all!"),
    Puppy(2, R.drawable.puppy2, "Monica", 0.1f, 1.0f, 0.1f, "No single person that saw Monica in real life ever said something different than \"Awww...\". She's very cuddleable, and never screams."),
    Puppy(3, R.drawable.puppy3, "Fat Plush", 0.8f, 0.9f, 0.5f, "Legends say, his grand-dad was a lion. Fat plush has the fines furr, making him a perfect cuddle partner. But he can get wild — like a lion!"),
    Puppy(4, R.drawable.puppy4, "Ice", 1.0f, 0.2f, 0.2f, "If Ice sees the postman, you will better be in some other place! It is very other-worldly that a dog can bark this loud!"),
    Puppy(5, R.drawable.puppy5, "Puppia", 0.2f, 0.8f, 0.2f, "She likes to wear a dress with her name on it. Puppia, a small yet very-cuddleable tiny monster. You can not not like her!"),
    Puppy(6, R.drawable.puppy6, "Frank", 0.9f, 0.6f, 0.3f, "A cousin of Mr. Jackson. Similar to his cousin, he's a classy pup. But he rather likes to stay at home than run around with the other dogs in the park."),
    Puppy(7, R.drawable.puppy7, "Yodaag", 0.2f, 0.2f, 1.0f, "When you look at the bark side, careful you must be. For the bark side looks back."),
    Puppy(8, R.drawable.puppy8, "Ice 2", 0.5f, 0.2f, 1.0f, "Grown up in the wilderness, Ice 2 knows how to defend himself. We're still trying to make him people-friendly — if you are up for a challenge — Ice 2 is your best go."),
    Puppy(9, R.drawable.puppy9, "Mr. Chester", 0.3f, 0.8f, 0.3f, "He likes to joke around from time to time. Stealing the food of his mates and always finding a way to get himself out of trouble."),
    Puppy(10, R.drawable.puppy10, "Dr. Snuffles", 0.1f, 0.2f, 0.2f, "After studying countless owners, Dr. Snuffles likes to live on his own. However, he likes to be scratched and therefore looks for a new owner."),
    Puppy(11, R.drawable.puppy11, "T.J. Rash", 0.5f, 0.2f, 0.6f, "If there is a party, T.J Rash is in the house. Some of the barkiest events turned out to be only because he was there."),
    Puppy(12, R.drawable.puppy12, "Bobby", 0.1f, 1.0f, 0.1f, "Likes to sleep in, be cuddled, cuddles back, jumps around, awww. This dog is just too cute."),
)

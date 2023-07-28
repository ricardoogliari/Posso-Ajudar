package com.atitus.possoajudar.util

import com.atitus.possoajudar.model.History

internal fun createNewHistory(
    newTitle: String,
    newDescription: String): History {

    return History(
        title = newTitle,
        description = newDescription,
        latitude = 0.0,
        longitude = 0.0
    )

}
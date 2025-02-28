package com.benedetto.data.repository.remote.mapper

import com.benedetto.data.LaunchListQuery
import com.benedetto.data.repository.remote.model.UserResponse
import com.benedetto.core.model.LaunchWrapper
import com.benedetto.core.model.User

fun UserResponse.toDomain(): User {
    return User(
        userId = this.userId,
        id = this.id,
        title = this.title,
        body = this.body
    )
}

fun LaunchListQuery.Launch.toDomain(): LaunchWrapper {
    return LaunchWrapper(
        id = this.id,
        site = this.site ?: ""
    )
}
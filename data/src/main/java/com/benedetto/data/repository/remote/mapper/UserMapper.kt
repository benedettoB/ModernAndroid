package com.benedetto.data.repository.remote.mapper

import com.benedetto.data.repository.remote.model.UserResponse
import com.benedetto.domain.model.User

fun UserResponse.toDomain(): User {
    return User(
        userId = this.userId,
        id = this.id,
        title = this.title,
        body = this.body
    )
}
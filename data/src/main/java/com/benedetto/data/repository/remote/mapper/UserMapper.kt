package com.benedetto.data.repository.remote.mapper

import com.benedetto.data.repository.remote.model.UserResponse
import com.benedetto.domain.model.User

fun UserResponse.toDomain(): User {
    return User(
        id = this.id,
        name = this.name,
        email = this.email
    )
}
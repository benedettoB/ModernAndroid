package com.benedetto.data.repository.remote.mapper

import com.benedetto.data.repository.remote.model.ProfileResponse
import com.benedetto.domain.model.Profile
import com.benedetto.domain.utils.removeNewLines

fun ProfileResponse.toDomain(): Profile {
    return Profile(
        postId = this.postId,
        id = this.id,
        name = this.name,
        email = this.email,
        body = this.body.removeNewLines()
    )
}
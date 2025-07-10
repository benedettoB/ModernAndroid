package com.benedetto.data.repository.remote.model

import com.google.gson.annotations.SerializedName

/*if any value is null in the response then the application will not crash, rather an empty list will be returned.
kotlin has made it easier to manage shared mutable state by making state immutable by default.
Apis should not return null values. They should return default values of empty strings or 0 or false for boolean types.
Developing data classes that allow null values requires adding extra code all the way down to the UI and is quite ugly.
Mobile devs should push back on services team members that are sending null values.
It shouldn't happen and it's too easy to fix by adding default values instead of null.
Regardless, this design still takes into account null values without app crashes. If they send null, then the app will gracefully move on.
*/
data class ProfileResponse(
    @SerializedName("postId")
    val postId: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("body")
    val body: String = ""
)



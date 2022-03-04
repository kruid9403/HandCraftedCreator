package com.handcraftedcreator.handcraftedcreator.repos

data class RepoResource<T>(
    val data: T? = null
) {
    val isSuccessful = data != null
}
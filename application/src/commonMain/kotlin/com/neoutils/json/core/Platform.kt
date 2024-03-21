package com.neoutils.json.core

enum class Platform() {
    DESKTOP,
    ANDROID;
}

expect fun getPlatform(): Platform
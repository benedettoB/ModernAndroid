// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.application.plug) apply false
    alias(libs.plugins.kotlin.plug) apply false
    alias(libs.plugins.library.plug) apply false
    alias(libs.plugins.hilt.plug) apply false
    alias(libs.plugins.apollo.plug) apply false
}
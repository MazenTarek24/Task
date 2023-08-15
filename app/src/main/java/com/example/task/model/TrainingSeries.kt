package com.example.task.model

data class TrainingSeries(
    val coverPhoto: String,
    val seriesName: String,
    val coaches: List<Coach>,
    val overviewSection: OverviewSection,
    val classes: List<TrainingClass>,
    val communitySocialFeed: List<SocialPost>
)
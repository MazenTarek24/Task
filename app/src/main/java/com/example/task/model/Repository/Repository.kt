package com.example.task.model.Repository

import android.content.Context
import androidx.core.view.ViewCompat.FocusRealDirection
import com.example.task.R
import com.example.task.model.*

import org.json.JSONObject

class Repository {



    fun getTrainingSeriesData(context: Context): TrainingSeries {
        val jsonString = context.resources.openRawResource(R.raw.training).bufferedReader().use {
            it.readText()
        }
        val jsonObject = JSONObject(jsonString)
        val trainingSeriesJson = jsonObject.getJSONObject("trainingSeries")
        val trainingSeries = parseTrainingSeries(trainingSeriesJson)
        return trainingSeries
    }

    private fun parseTrainingSeries(jsonObject: JSONObject): TrainingSeries {
        val coverPhoto = jsonObject.getString("coverPhoto")
        val seriesName = jsonObject.getString("seriesName")

        val coachesArray = jsonObject.getJSONArray("coaches")
        val coachesList = mutableListOf<Coach>()
        for (i in 0 until coachesArray.length()) {
            val coachJson = coachesArray.getJSONObject(i)
            val coach = parseCoach(coachJson)
            coachesList.add(coach)
        }

        val overviewSectionJson = jsonObject.getJSONObject("overviewSection")
        val overviewSection = parseOverviewSection(overviewSectionJson)

        val classesArray = jsonObject.getJSONArray("classes")
        val classesList = mutableListOf<TrainingClass>()
        for (i in 0 until classesArray.length()) {
            val classJson = classesArray.getJSONObject(i)
            val trainingClass = parseTrainingClass(classJson)
            classesList.add(trainingClass)
        }

        val communitySocialFeedArray = jsonObject.getJSONArray("communitySocialFeed")
        val communitySocialFeedList = mutableListOf<SocialPost>()
        for (i in 0 until communitySocialFeedArray.length()) {
            val postJson = communitySocialFeedArray.getJSONObject(i)
            val socialPost = parseSocialPost(postJson)
            communitySocialFeedList.add(socialPost)
        }

        return TrainingSeries(
            coverPhoto,
            seriesName,
            coachesList,
            overviewSection,
            classesList,
            communitySocialFeedList
        )
    }

    private fun parseCoach(jsonObject: JSONObject): Coach {
        val coachName = jsonObject.getString("coachName")
        val coachBio = jsonObject.getString("coachBio")
        val coachPhoto = jsonObject.getString("coachPhoto")
        return Coach(coachName, coachBio, coachPhoto)
    }

    private fun parseOverviewSection(jsonObject: JSONObject): OverviewSection {
        val description = jsonObject.getString("description")
        val overviewVideo = jsonObject.getString("overviewVideo")
        return OverviewSection(description, overviewVideo)
    }

    private fun parseTrainingClass(jsonObject: JSONObject): TrainingClass {
        val className = jsonObject.getString("className")
        val classDescription = jsonObject.getString("classDescription")
        val classVideo = jsonObject.getString("classVideo")
        return TrainingClass(className, classDescription, classVideo)
    }

    private fun parseSocialPost(jsonObject: JSONObject): SocialPost {
        val postText = jsonObject.getString("postText")
        val userAvatar = jsonObject.getString("userAvatar")
        val timestamp = jsonObject.getString("timestamp")
        return SocialPost(postText, userAvatar, timestamp)
    }
}


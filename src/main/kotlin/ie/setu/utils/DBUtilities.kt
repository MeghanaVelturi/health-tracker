package ie.setu.utils

import ie.setu.domain.*
import ie.setu.domain.db.*
import org.jetbrains.exposed.sql.ResultRow

fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    name = it[Users.name],
    email = it[Users.email],
    password = it[Users.password]
)

fun mapToNutrition(it: ResultRow) = Nutrition(
    id = it[Nutritions.id],
    noofcaloriestaken = it[Nutritions.noofcaloriestaken],
    foodname = it[Nutritions.foodname],
    datetime = it[Nutritions.datetime],
    useremail = it[Nutritions.useremail]
)

fun mapToHydration(it: ResultRow) = Hydration(
    id = it[Hydrations.id],
    mltaken = it[Hydrations.mltaken],
    liquidname = it[Hydrations.liquidname],
    datetime = it[Hydrations.datetime],
    useremail = it[Hydrations.useremail]
)

fun mapToWeightGoal(it: ResultRow) = WeightGoal(
    id = it[WeightGoals.id],
    goaltype = it[WeightGoals.goaltype],
    targetweight = it[WeightGoals.targetweight],
    started = it[WeightGoals.started],
    deadline = it[WeightGoals.deadline],
    useremail = it[WeightGoals.useremail]
)

fun mapToActivity(it: ResultRow) = Activity(
    id = it[Activities.id],
    description = it[Activities.description],
    duration = it[Activities.duration],
    started = it[Activities.started],
    stepcount = it[Activities.stepcount],
    calories = it[Activities.calories],
    useremail = it[Activities.useremail]
)

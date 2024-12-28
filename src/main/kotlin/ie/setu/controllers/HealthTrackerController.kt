package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.*
import ie.setu.domain.repository.*
import io.javalin.http.Context


object HealthTrackerController {

    private val userDao = UserDAO()
    private val activityDAO = ActivityDAO()
    private val nutritionDAO = NutritionDAO()
    private val hydrationDAO = HydrationDAO()
    private val weightGoalDAO = WeightGoalDAO()

    fun getAllUsers(ctx: Context) {
        ctx.json(userDao.getAll())
    }

    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
        }
    }

    fun loginUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val userbody = mapper.readValue<User>(ctx.body())
        val user = userDao.authenticateUser(userbody)
        if (user != null) {
            ctx.json(user)
        } else {
            ctx.json({
                "invalid credentials"
            })
        }
    }

    fun addUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<User>(ctx.body())
        userDao.save(user)
        ctx.json(user)
    }

    fun getUserByEmail(ctx: Context){
        val user = userDao.findByEmail(ctx.pathParam("email"))
        if (user != null) {
            ctx.json(user)
        }
    }

    fun deleteUser(ctx: Context){
        userDao.delete(ctx.pathParam("user-id").toInt())
    }

    fun updateUser(ctx: Context){
        val mapper = jacksonObjectMapper()
        val userUpdates = mapper.readValue<User>(ctx.body())
        userDao.update(
            id = ctx.pathParam("user-id").toInt(),
            user=userUpdates)
    }

    //--------------------------------------------------------------
    // ActivityDAO specifics
    //-------------------------------------------------------------

    fun getAllActivities(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString( activityDAO.getAll() ))
    }

    fun getActivityByActivityId(ctx: Context) {
        val act = activityDAO.findByActivityId(ctx.pathParam("act-id").toInt())
        if (act != null) {
            ctx.json(act)
        }
    }

//    fun getActivitiesByUserId(ctx: Context) {
//        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
//            val activities = activityDAO.findByUserId(ctx.pathParam("user-id").toInt())
//            if (activities.isNotEmpty()) {
//                //mapper handles the deserialization of Joda date into a String.
//                val mapper = jacksonObjectMapper()
//                    .registerModule(JodaModule())
//                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
//                ctx.json(mapper.writeValueAsString(activities))
//            }
//        }
//    }

    fun addActivity(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val activity = mapper.readValue<Activity>(ctx.body())
        activityDAO.save(activity)
        ctx.json(activity)
    }

    fun getActivityByEmail(ctx: Context){
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString( activityDAO.findByEmail(ctx.pathParam("email")) ))
    }

    fun deleteActivity(ctx: Context): Context {
        activityDAO.delete(ctx.pathParam("act-id").toInt())
        return ctx.json("Deleted Successfully")
    }

    fun updateActivity(ctx: Context){
        val mapper = jacksonObjectMapper().registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val actUpdates = mapper.readValue<Activity>(ctx.body())
        ctx.json(mapper.writeValueAsString(activityDAO.update(
            id = ctx.pathParam("act-id").toInt(),
            act = actUpdates)))


    }


    //--------------------------------------------------------------
    // NutritionDAO specifics
    //-------------------------------------------------------------

    fun getAllNutritions(ctx: Context) {
        ctx.json(nutritionDAO.getAllNutritions())
    }

    fun getNutritionsByNutritionId(ctx: Context) {
        val nut = nutritionDAO.findById(ctx.pathParam("nut-id").toInt())
        if (nut != null) {
            ctx.json(nut)
        }
    }

    fun addNutrition(ctx: Context) {
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
        val nut = mapper.readValue<Nutrition>(ctx.body())
        nutritionDAO.save(nut)
        ctx.json(nut)
    }

    fun getNutritionsByEmail(ctx: Context){
        val nut = nutritionDAO.findByEmail(ctx.pathParam("email"))
        ctx.json(nut)

    }

    fun deleteNutrition(ctx: Context): Context {
        nutritionDAO.delete(ctx.pathParam("nut-id").toInt())
        return ctx.json("Deleted Successfully")
    }

    fun updateNutrition(ctx: Context): Context {
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
        val nutritionUpdates = mapper.readValue<Nutrition>(ctx.body())
        nutritionDAO.update(
            id = ctx.pathParam("nut-id").toInt(),
            nut = nutritionUpdates)
        return ctx.json("Updated Successfully")
    }

    //--------------------------------------------------------------
    // HydrationDAO specifics
    //-------------------------------------------------------------

    fun getAllHydrations(ctx: Context) {
        ctx.json(hydrationDAO.getAllHydration())
    }

    fun getHydrationsByHydrationId(ctx: Context) {
        val hyd = hydrationDAO.findById(ctx.pathParam("hyd-id").toInt())
        if (hyd != null) {
            ctx.json(hyd)
        }
    }

    fun addHydration(ctx: Context) {
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
        val hyd = mapper.readValue<Hydration>(ctx.body())
        hydrationDAO.save(hyd)
        ctx.json(hyd)
    }

    fun getHydrationsByEmail(ctx: Context){
        val hyd = hydrationDAO.findByEmail(ctx.pathParam("email"))
        ctx.json(hyd)

    }

    fun deleteHydration(ctx: Context): Context {
        hydrationDAO.delete(ctx.pathParam("hyd-id").toInt())
        return ctx.json("Deleted Successfully")
    }

    fun updateHydration(ctx: Context): Context {
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
        val hydrationUpdates = mapper.readValue<Hydration>(ctx.body())
        hydrationDAO.update(
            id = ctx.pathParam("hyd-id").toInt(),
            hyd = hydrationUpdates)
        return ctx.json("Updated Successfully")
    }

    //--------------------------------------------------------------
    // WeightGoalDAO specifics
    //-------------------------------------------------------------

    fun getAllWeightGoal(ctx: Context) {
        ctx.json(weightGoalDAO.getAllWeightGoals())
    }

    fun getWeightGoalByWeightGoalId(ctx: Context) {
        val wg = weightGoalDAO.findById(ctx.pathParam("wg-id").toInt())
        if (wg != null) {
            ctx.json(wg)
        }
    }

    fun addWeightGoal(ctx: Context) {
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
        val wg = mapper.readValue<WeightGoal>(ctx.body())
        weightGoalDAO.save(wg)
        ctx.json(wg)
    }

    fun getWeightGoalsByEmail(ctx: Context){
        val wg = weightGoalDAO.findByEmail(ctx.pathParam("email"))
        ctx.json(wg)

    }

    fun deleteWeightGoal(ctx: Context): Context {
        weightGoalDAO.delete(ctx.pathParam("wg-id").toInt())
        return ctx.json("Deleted Successfully")
    }

    fun updateWeightGoal(ctx: Context): Context {
        val mapper = jacksonObjectMapper().registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
        val weightgoalUpdates = mapper.readValue<WeightGoal>(ctx.body())
        weightGoalDAO.update(
            id = ctx.pathParam("wg-id").toInt(),
            wg = weightgoalUpdates)
        return ctx.json("Updated Successfully")
    }


}
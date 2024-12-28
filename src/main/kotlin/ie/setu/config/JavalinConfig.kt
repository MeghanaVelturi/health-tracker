package ie.setu.config

import ie.setu.controllers.HealthTrackerController
import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.json.JavalinJackson

class JavalinConfig {

    fun startJavalinService(): Javalin {
        val app = Javalin.create {config ->
            config.bundledPlugins.enableCors { cors ->
                cors.addRule {
                    it.anyHost()
                }
            }
            //add this jsonMapper to serialise objects to json
            config.jsonMapper(JavalinJackson(jsonObjectMapper()))
        }

            .apply{
                exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
                error(404) { ctx -> ctx.json("404 - Not Found") }
            }
            .start(getRemoteAssignedPort())

        registerRoutes(app)
        return app
    }

    private fun registerRoutes(app: Javalin) {
        app.get("/api/users", HealthTrackerController::getAllUsers)
        app.get("/api/users/{user-id}", HealthTrackerController::getUserByUserId)
        app.post("/api/users", HealthTrackerController::addUser)
        app.post("/api/users/login", HealthTrackerController::loginUser)
        app.delete("/api/users/{user-id}", HealthTrackerController::deleteUser)
        app.patch("/api/users/{user-id}", HealthTrackerController::updateUser)
        app.get("/api/users/email/{email}", HealthTrackerController::getUserByEmail)

        app.get("/api/activities", HealthTrackerController::getAllActivities)
        app.post("/api/activities", HealthTrackerController::addActivity)
        app.get("/api/activities/email/{email}", HealthTrackerController::getActivityByEmail)
        app.delete("/api/activities/{act-id}", HealthTrackerController::deleteActivity)
        app.patch("/api/activities/{act-id}", HealthTrackerController::updateActivity)
        app.get("/api/activities/{act-id}", HealthTrackerController::getActivityByActivityId)

        app.get("/api/nutritions", HealthTrackerController::getAllNutritions)
        app.post("/api/nutritions", HealthTrackerController::addNutrition)
        app.get("/api/nutritions/email/{email}", HealthTrackerController::getNutritionsByEmail)
        app.delete("/api/nutritions/{nut-id}", HealthTrackerController::deleteNutrition)
        app.patch("/api/nutritions/{nut-id}", HealthTrackerController::updateNutrition)
        app.get("/api/nutritions/{nut-id}", HealthTrackerController::getNutritionsByNutritionId)

        app.get("/api/hydrations", HealthTrackerController::getAllHydrations)
        app.post("/api/hydrations", HealthTrackerController::addHydration)
        app.get("/api/hydrations/email/{email}", HealthTrackerController::getHydrationsByEmail)
        app.delete("/api/hydrations/{hyd-id}", HealthTrackerController::deleteHydration)
        app.patch("/api/hydrations/{hyd-id}", HealthTrackerController::updateHydration)
        app.get("/api/hydrations/{hyd-id}", HealthTrackerController::getHydrationsByHydrationId)

        app.get("/api/weightgoals", HealthTrackerController::getAllWeightGoal)
        app.post("/api/weightgoals", HealthTrackerController::addWeightGoal)
        app.get("/api/weightgoals/email/{email}", HealthTrackerController::getWeightGoalsByEmail)
        app.delete("/api/weightgoals/{wg-id}", HealthTrackerController::deleteWeightGoal)
        app.patch("/api/weightgoals/{wg-id}", HealthTrackerController::updateWeightGoal)
        app.get("/api/weightgoals/{wg-id}", HealthTrackerController::getWeightGoalByWeightGoalId)

    }

    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7001
    }
}
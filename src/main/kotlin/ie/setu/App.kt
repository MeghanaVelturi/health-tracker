package ie.setu

import ie.setu.config.DbConfig
import ie.setu.config.JavalinConfig
import io.javalin.Javalin

fun main() {

    DbConfig().getDbConnection()
    JavalinConfig().startJavalinService()

}
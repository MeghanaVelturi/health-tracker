package ie.setu.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.postgresql.util.PSQLException

class DbConfig {

    private val logger = KotlinLogging.logger {}
    private lateinit var dbConfig: Database
    //postgresql://health_tracker_gdws_user:aJClAft5EdlZoaKe1GcpOQHXbR6Res1N@dpg-ctku6d2j1k6c73csl9t0-a.singapore-postgres.render.com/health_tracker_gdws
    fun getDbConnection(): Database {

        val PGHOST = "dpg-ctku6d2j1k6c73csl9t0-a.singapore-postgres.render.com"
        val PGPORT = "5432"
        val PGUSER = "health_tracker_gdws_user"
        val PGPASSWORD = "aJClAft5EdlZoaKe1GcpOQHXbR6Res1N"
        val PGDATABASE = "health_tracker_gdws"
        //url format should be jdbc:postgresql://host:port/database
        val dbUrl = "jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE"

        try {
            logger.info { "Starting DB Connection...$dbUrl" }
            dbConfig = Database.connect(
                url = dbUrl, driver = "org.postgresql.Driver",
                user = PGUSER, password = PGPASSWORD
            )
            logger.info { "DB Connected Successfully..." + dbConfig.url }
        } catch (e: PSQLException) {
            logger.info { "Error in DB Connection...${e.printStackTrace()}" }
        }

        return dbConfig

    }
}
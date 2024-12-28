package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.date
import org.jetbrains.exposed.sql.jodatime.datetime
import org.jetbrains.exposed.sql.jodatime.time

// SRP - Responsibility is to manage one activity.
//       Database wise, this is the table object.

object WeightGoals : Table("weightgoals") {
    val id = integer("id").autoIncrement()
    val goaltype = varchar("goaltype",100)
    val targetweight = integer("targetweight")
    val started = datetime("started")
    val deadline = datetime("deadline")
    val useremail = varchar("useremail",100)

    override val primaryKey = PrimaryKey(WeightGoals.id, name = "PK_WeightGoal_ID")
}
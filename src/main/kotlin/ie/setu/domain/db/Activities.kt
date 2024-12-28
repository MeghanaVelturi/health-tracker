package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

// SRP - Responsibility is to manage one activity.
//       Database wise, this is the table object.

object Activities : Table("activities") {
    val id = integer("id").autoIncrement()
    val description = varchar("description", 100)
    val duration = double("duration")
    val calories = integer("calories")
    val stepcount = integer("stepcount")
    val started = datetime("started")
    val useremail = varchar("useremail",100)

    override val primaryKey = PrimaryKey(Activities.id, name = "PK_Activities_ID")
}
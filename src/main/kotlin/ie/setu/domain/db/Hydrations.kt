package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

// SRP - Responsibility is to manage one activity.
//       Database wise, this is the table object.

object Hydrations : Table("hydrations") {
    val id = integer("id").autoIncrement()
    val mltaken = integer("mltaken")
    val liquidname = varchar("liquidname",200)
    val datetime = datetime("datetime")
    val useremail = varchar("useremail",100)

    override val primaryKey = PrimaryKey(Hydrations.id, name = "PK_Hydration_ID")
}
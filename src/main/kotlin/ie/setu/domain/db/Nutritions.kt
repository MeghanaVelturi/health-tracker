package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.date
import org.jetbrains.exposed.sql.jodatime.datetime
import org.jetbrains.exposed.sql.jodatime.time

// SRP - Responsibility is to manage one activity.
//       Database wise, this is the table object.

object Nutritions : Table("nutritions") {
    val id = integer("id").autoIncrement()
    val noofcaloriestaken = integer("noofcaloriestaken")
    val foodname = varchar("foodname",200)
    val datetime = datetime("datetime")
    val useremail = varchar("useremail",100)

    override val primaryKey = PrimaryKey(Nutritions.id, name = "PK_Nutrition_ID")
}
package ie.setu.domain

import org.joda.time.DateTime
import java.sql.Time
import java.sql.Date

data class WeightGoal (var id: Int,
                       var goaltype: String,
                       var targetweight:Int,
                       var started:DateTime,
                       var deadline:DateTime,
                       var useremail:String)
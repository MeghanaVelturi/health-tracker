package ie.setu.domain

import org.joda.time.DateTime
import java.sql.Time
import java.sql.Date

data class Nutrition (var id: Int,
                      var noofcaloriestaken:Int,
                      var foodname:String,
                      var datetime:DateTime,
                      var useremail:String)
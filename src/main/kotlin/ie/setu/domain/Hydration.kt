package ie.setu.domain

import org.joda.time.DateTime
import java.sql.Time
import java.sql.Date

data class Hydration (var id: Int,
                      var mltaken: Int,
                      var liquidname: String,
                      var datetime: DateTime,
                      var useremail: String)
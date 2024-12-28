package ie.setu.domain.repository

import ie.setu.domain.Activity
import ie.setu.domain.db.Activities
import ie.setu.utils.mapToActivity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ActivityDAO {

    //Get all the activities in the database regardless of user id
    fun getAll(): ArrayList<Activity> {
        val activitiesList: ArrayList<Activity> = arrayListOf()
        transaction {
            Activities.selectAll().map {
                activitiesList.add(mapToActivity(it)) }
        }
        return activitiesList
    }

    //Find a specific activity by activity id
    fun findByActivityId(id: Int): Activity?{
        return transaction {
            Activities
                .selectAll().where { Activities.id eq id}
                .map{mapToActivity(it)}
                .firstOrNull()
        }
    }

    //Save an activity to the database
    fun save(activity: Activity){
        transaction {
            Activities.insert {
                it[description] = activity.description
                it[duration] = activity.duration
                it[started] = activity.started
                it[calories] = activity.calories
                it[stepcount]=activity.stepcount
                it[useremail]=activity.useremail
            }
        }
    }

    fun findByEmail(email: String) : ArrayList<Activity>{
        val activitiesList: ArrayList<Activity> = arrayListOf()
        transaction {
            Activities.selectAll().where { Activities.useremail eq email}.map {
                activitiesList.add(mapToActivity(it)) }
        }
        return activitiesList
    }

    fun delete(id: Int):Int {
        return transaction {
            Activities.deleteWhere { Activities.id eq id }
        }
    }

    fun update(id: Int, act: Activity): Activity?{
        transaction {
            Activities.update ({
                Activities.id eq id}) {
                it[description] = act.description
                it[duration] = act.duration
                it[calories]=act.calories
                it[stepcount]=act.stepcount
                it[started] = act.started
                it[useremail]=act.useremail
            }
        }
        return findByActivityId(act.id);
    }

}
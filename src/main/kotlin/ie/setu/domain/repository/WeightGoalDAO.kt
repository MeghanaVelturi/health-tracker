package ie.setu.domain.repository

import ie.setu.domain.WeightGoal
import ie.setu.domain.db.WeightGoals
import ie.setu.utils.mapToWeightGoal
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update

class WeightGoalDAO {

    fun getAllWeightGoals(): ArrayList<WeightGoal> {
        val weightGoalList: ArrayList<WeightGoal> = arrayListOf()
        transaction {
            WeightGoals.selectAll().map {
                weightGoalList.add(mapToWeightGoal(it)) }
        }
        return weightGoalList
    }

    fun findById(id: Int): WeightGoal?{
        return transaction {
            WeightGoals.selectAll().where { WeightGoals.id eq id }
                .map{ mapToWeightGoal(it) }
                .firstOrNull()
        }
    }

    fun save(wg: WeightGoal){
        transaction {
            WeightGoals.insert {
                it[goaltype] = wg.goaltype
                it[targetweight] = wg.targetweight
                it[deadline] = wg.deadline
                it[started] = wg.started
                it[useremail]=wg.useremail
            }
        }
    }

    fun findByEmail(email: String) :ArrayList<WeightGoal>{
        val weightGoalList: ArrayList<WeightGoal> = arrayListOf()
         transaction {
            WeightGoals.selectAll().where { WeightGoals.useremail eq email}
                .map{ weightGoalList.add(mapToWeightGoal(it)) }
        }
        return weightGoalList;
    }

    fun delete(id: Int):Int {
        return transaction {
            WeightGoals.deleteWhere { WeightGoals.id eq id }
        }
    }

    fun update(id: Int, wg: WeightGoal){
        transaction {
            WeightGoals.update ({
                WeightGoals.id eq id}) {
                it[goaltype] = wg.goaltype
                it[targetweight] = wg.targetweight
                it[started] = wg.started
                it[deadline] = wg.deadline
                it[useremail]=wg.useremail
            }
        }
    }

}
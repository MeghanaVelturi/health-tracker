package ie.setu.domain.repository

import ie.setu.domain.Nutrition
import ie.setu.domain.db.Nutritions
import ie.setu.utils.mapToNutrition
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update

class NutritionDAO {

    fun getAllNutritions(): ArrayList<Nutrition> {
        val nutritionsList: ArrayList<Nutrition> = arrayListOf()
        transaction {
            Nutritions.selectAll().map {
                nutritionsList.add(mapToNutrition(it)) }
        }
        return nutritionsList
    }

    fun findById(id: Int): Nutrition?{
        return transaction {
            Nutritions.selectAll().where { Nutritions.id eq id }
                .map{ mapToNutrition(it) }
                .firstOrNull()
        }
    }

    fun save(nut: Nutrition){
        transaction {
            Nutritions.insert {
                it[noofcaloriestaken] = nut.noofcaloriestaken
                it[foodname] = nut.foodname
                it[datetime] = nut.datetime
                it[useremail]=nut.useremail
            }
        }
    }

    fun findByEmail(email: String) :ArrayList<Nutrition>{
        val nutritionList: ArrayList<Nutrition> = arrayListOf()
         transaction {
            Nutritions.selectAll().where { Nutritions.useremail eq email}
                .map{ nutritionList.add(mapToNutrition(it)) }
        }
        return nutritionList;
    }

    fun delete(id: Int):Int {
        return transaction {
            Nutritions.deleteWhere { Nutritions.id eq id }
        }
    }

    fun update(id: Int, nut: Nutrition){
        transaction {
            Nutritions.update ({
                Nutritions.id eq id}) {
                it[noofcaloriestaken] = nut.noofcaloriestaken
                it[foodname] = nut.foodname
                it[datetime] = nut.datetime
                it[useremail]=nut.useremail
            }
        }
    }

}
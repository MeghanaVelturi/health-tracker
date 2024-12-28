package ie.setu.domain.repository

import ie.setu.domain.Hydration
import ie.setu.domain.db.Hydrations
import ie.setu.utils.mapToHydration
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update

class HydrationDAO {

    fun getAllHydration(): ArrayList<Hydration> {
        val hydrationList: ArrayList<Hydration> = arrayListOf()
        transaction {
            Hydrations.selectAll().map {
                hydrationList.add(mapToHydration(it)) }
        }
        return hydrationList
    }

    fun findById(id: Int): Hydration?{
        return transaction {
            Hydrations.selectAll().where { Hydrations.id eq id }
                .map{ mapToHydration(it) }
                .firstOrNull()
        }
    }

    fun save(hyd: Hydration){
        transaction {
            Hydrations.insert {
                it[mltaken] = hyd.mltaken
                it[liquidname] = hyd.liquidname
                it[datetime] = hyd.datetime
                it[useremail]=hyd.useremail
            }
        }
    }

    fun findByEmail(email: String) :ArrayList<Hydration>{
        val hydrationList: ArrayList<Hydration> = arrayListOf()
         transaction {
            Hydrations.selectAll().where { Hydrations.useremail eq email}
                .map{ hydrationList.add(mapToHydration(it)) }
        }
        return hydrationList;
    }

    fun delete(id: Int):Int {
        return transaction {
            Hydrations.deleteWhere { Hydrations.id eq id }
        }
    }

    fun update(id: Int, hyd: Hydration){
        transaction {
            Hydrations.update ({
                Hydrations.id eq id}) {
                it[mltaken] = hyd.mltaken
                it[liquidname] = hyd.liquidname
                it[datetime] = hyd.datetime
                it[useremail]=hyd.useremail
            }
        }
    }

}
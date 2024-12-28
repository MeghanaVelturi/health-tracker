package ie.setu.domain.repository

import ie.setu.domain.User
import ie.setu.domain.UserLoginBody
import ie.setu.domain.db.Users
import ie.setu.utils.mapToUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UserDAO {

    fun getAll(): ArrayList<User> {
        val userList: ArrayList<User> = arrayListOf()
        transaction {
            Users.selectAll().map {
                userList.add(mapToUser(it)) }
        }
        return userList
    }

    fun findById(id: Int): User?{
        return transaction {
            Users.selectAll().where { Users.id eq id }
                .map{mapToUser(it)}
                .firstOrNull()
        }
    }

    fun authenticateUser(user: User): User?{
        return transaction {
            Users.selectAll().where {Users.email eq user.email}.andWhere { Users.password eq user.password }
                .map{mapToUser(it)}
                .firstOrNull()
        }
    }

    fun save(user: User){
        transaction {
            Users.insert {
                it[name] = user.name
                it[email] = user.email
                it[password]=user.password
            }
        }
    }

    fun findByEmail(email: String) :User?{
        return transaction {
            Users.selectAll().where { Users.email eq email}
                .map{mapToUser(it)}
                .firstOrNull()
        }
    }

    fun delete(id: Int):Int {
        return transaction {
            Users.deleteWhere { Users.id eq id }
        }
    }

    fun update(id: Int, user: User){
        transaction {
            Users.update ({
                Users.id eq id}) {
                it[name] = user.name
                it[email] = user.email
            }
        }
    }

}
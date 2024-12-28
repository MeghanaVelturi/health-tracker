@file:JvmName("WeightGoalDAOTestKt")

package ie.setu.repository

import ie.setu.domain.WeightGoal
import ie.setu.domain.db.WeightGoals
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.BeforeAll
import ie.setu.domain.repository.WeightGoalDAO
import ie.setu.helpers.nonExistingEmail
import ie.setu.helpers.wgs
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from Fixtures
val weightGoal1 = wgs[0]
val weightGoal2 = wgs[1]
val weightGoal3 = wgs[2]

class WeightGoalDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class ReadWeightGoals {

        @Test
        fun `getting all WeightGoals from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate table with three users
                val weightGoalDAO = populateWeightGoalTable()

                //Act & Assert
                assertEquals(3, weightGoalDAO.getAllWeightGoals().size)
            }
        }

        @Test
        fun `get weightGoal by id that doesn't exist, results in no weightGoal returned`() {
            transaction {
                //Arrange - create and populate table with three weightGoals
                val weightGoalDAO = populateWeightGoalTable()

                //Act & Assert
                assertEquals(null, weightGoalDAO.findById(4))
            }
        }

        @Test
        fun `get WeightGoal by id that exists, results in a correct WeightGoal returned`() {
            transaction {
                //Arrange - create and populate table with three WeightGoals
                val weightGoalDAO = populateWeightGoalTable()

                //Act & Assert
                assertEquals(null, weightGoalDAO.findById(4))
            }

        }


        @Test
        fun `get all weightGoals over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(WeightGoals)
                val weightGoalDAO = WeightGoalDAO()

                //Act & Assert
                assertEquals(0, weightGoalDAO.getAllWeightGoals().size)
            }
        }

        @Test
        fun `get weightGoal by email that doesn't exist, results in no weightGoal returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val weightGoalDAO = populateWeightGoalTable()

                //Act & Assert
                assertEquals(0, weightGoalDAO.findByEmail(nonExistingEmail).size)
            }
        }

        @Test
        fun `get WeightGoal by email that exists, results in correct user returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val weightGoalDAO = populateWeightGoalTable()

                //Act & Assert
                assertEquals(weightGoal2, weightGoalDAO.findByEmail(weightGoal2.useremail).get(0))
            }
        }

    }

    @Nested
    inner class CreateWeightGoals {
        @Test
        fun `multiple WeightGoals added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate table with three WeightGoals
                val weightGoalDAO = populateWeightGoalTable()

                //Act & Assert
                assertEquals(3, weightGoalDAO.getAllWeightGoals().size)
                assertEquals(weightGoal1, weightGoalDAO.findById(weightGoal1.id))
                assertEquals(weightGoal2, weightGoalDAO.findById(weightGoal2.id))
                assertEquals(weightGoal3, weightGoalDAO.findById(weightGoal3.id))
            }
        }
    }


    @Nested
    inner class DeleteWeightGoals {
        @Test
        fun `deleting a non-existant WeightGoal in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three WeightGoals
                val weightGoalDAO = populateWeightGoalTable()

                //Act & Assert
                assertEquals(3, weightGoalDAO.getAllWeightGoals().size)
                weightGoalDAO.delete(4)
                assertEquals(3, weightGoalDAO.getAllWeightGoals().size)
            }
        }

        @Test
        fun `deleting an existing WeightGoal in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three WeightGoals
                val weightGoalDAO = populateWeightGoalTable()

                //Act & Assert
                assertEquals(3, weightGoalDAO.getAllWeightGoals().size)
                weightGoalDAO.delete(weightGoal3.id)
                assertEquals(2, weightGoalDAO.getAllWeightGoals().size)
            }
        }
    }

    @Nested
    inner class UpdateWeightGoals {

        @Test
        fun `updating existing WeightGoal in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three WeightGoals
                val weightGoalDAO = populateWeightGoalTable()

                //Act & Assert
                val weightGoal3Updated = WeightGoal(3,"weightloss",70,
                    DateTime("2024-11-20T09:00:00.000"),
                    DateTime("2024-12-20T09:00:00.000"),"user3@gmail.com");
                weightGoalDAO.update(weightGoal3.id, weightGoal3Updated)
                assertEquals(weightGoal3Updated, weightGoalDAO.findById(3))
            }
        }

        @Test
        fun `updating non-existant WeightGoal in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                val weightGoalDAO = populateWeightGoalTable()

                //Act & Assert
                val weightGoal4Updated = WeightGoal(4,"weightgain",90, DateTime("2024-11-28T09:00:00.000"),DateTime("2024-12-28T09:00:00.000"),"user4@gmail.com");
                weightGoalDAO.update(4, weightGoal4Updated)
                assertEquals(null, weightGoalDAO.findById(4))
                assertEquals(3, weightGoalDAO.getAllWeightGoals().size)
            }
        }
    }

    internal fun populateWeightGoalTable(): WeightGoalDAO{
        SchemaUtils.create(WeightGoals)
        val weightGoalDAO = WeightGoalDAO()
        weightGoalDAO.save(weightGoal1)
        weightGoalDAO.save(weightGoal2)
        weightGoalDAO.save(weightGoal3)
        return weightGoalDAO
    }
}



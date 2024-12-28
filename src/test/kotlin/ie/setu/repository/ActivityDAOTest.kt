@file:JvmName("ActivityDAOTestKt")

package ie.setu.repository

import ie.setu.domain.Activity

import ie.setu.domain.db.Activities
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.BeforeAll

import ie.setu.domain.repository.ActivityDAO
import ie.setu.helpers.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.joda.time.DateTime

//retrieving some test data from Fixtures
val act1 = acts[0]
val act2 = acts[1]
val act3 = acts[2]


class ActivityDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class ReadSleeps {

        @Test
        fun `getting all Activitys from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate table with three users
                val activityDAO = populateActivitiesTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
            }
        }

        @Test
        fun `get activity by id that doesn't exist, results in no activity returned`() {
            transaction {
                //Arrange - create and populate table with three bmis
                val activityDAO = populateActivitiesTable()

                //Act & Assert
                assertEquals(null, activityDAO.findByActivityId(4))
            }
        }

        @Test
        fun `get activity by id that exists, results in a correct activity returned`() {
            transaction {
                //Arrange - create and populate table with three bmis
                val activityDAO = populateActivitiesTable()

                //Act & Assert
                assertEquals(act3, activityDAO.findByActivityId(3))
            }

        }


        @Test
        fun `get all activitys over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(Activities)
                val activityDAO = ActivityDAO()

                //Act & Assert
                assertEquals(0, activityDAO.getAll().size)
            }
        }

        @Test
        fun `get activities by email that doesn't exist, results in no activitys returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val activityDAO = populateActivitiesTable()

                //Act & Assert
                assertEquals(0, activityDAO.findByEmail(nonExistingEmail).size)
            }
        }

        @Test
        fun `get activity by email that exists, results in correct activity returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val activityDAO = populateActivitiesTable()

                //Act & Assert
                assertEquals(act2, activityDAO.findByEmail(act2.useremail).get(0))
            }
        }

    }

    @Nested
    inner class CreateActivity {
        @Test
        fun `multiple activitys added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate table with three bmis
                val activityDAO = populateActivitiesTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
                assertEquals(act1, activityDAO.findByActivityId(act1.id))
                assertEquals(act2, activityDAO.findByActivityId(act2.id))
                assertEquals(act3, activityDAO.findByActivityId(act3.id))
            }
        }
    }


    @Nested
    inner class DeleteActivities {
        @Test
        fun `deleting a non-existant activity in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three bmis
                val activityDAO = populateActivitiesTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
                activityDAO.delete(4)
                assertEquals(3, activityDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing sleep in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three bmis
                val activityDAO = populateActivitiesTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
                activityDAO.delete(act3.id)
                assertEquals(2, activityDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateActivity {

        @Test
        fun `updating existing activity in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three bmis
                val activityDAO = populateActivitiesTable()

                //Act & Assert
                val act3Updated = Activity(3,"Completed RUnning",1.4,400,4000,DateTime("2024-11-22T09:00:00.000"),"user3@gmail.com");
                activityDAO.update(act3.id, act3Updated)
                assertEquals(act3, activityDAO.findByActivityId(3))
            }
        }

        @Test
        fun `updating non-existant activity in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                val activityDAO = populateActivitiesTable()

                //Act & Assert
                val work4Updated = Activity(4,"Completed Morning workout",1.2,400,4000,DateTime("2024-11-22T09:00:00.000"),"user4@gmail.com");
                activityDAO.update(4, work4Updated)
                assertEquals(null, activityDAO.findByActivityId(4))
                assertEquals(3, activityDAO.getAll().size)
            }
        }
    }

    internal fun populateActivitiesTable(): ActivityDAO{
        SchemaUtils.create(Activities)
        val activityDAO = ActivityDAO()
        activityDAO.save(act1)
        activityDAO.save(act2)
        activityDAO.save(act3)
        return activityDAO
    }
}



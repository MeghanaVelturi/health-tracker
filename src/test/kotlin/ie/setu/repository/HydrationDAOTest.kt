@file:JvmName("HydrationDAOTestKt")

package ie.setu.repository

import ie.setu.domain.Hydration

import ie.setu.domain.db.Hydrations
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.BeforeAll

import ie.setu.domain.repository.HydrationDAO
import ie.setu.helpers.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.joda.time.DateTime

//retrieving some test data from Fixtures
val hyd1 = hyds[0]
val hyd2 = hyds[1]
val hyd3 = hyds[2]


class HydrationDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class ReadHydrations {

        @Test
        fun `getting all Hydrations from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate table with three users
                val hydrationDAO = populateHydrationsTable()

                //Act & Assert
                assertEquals(3, hydrationDAO.getAllHydration().size)
            }
        }

        @Test
        fun `get Hydration by id that doesn't exist, results in no Hydration returned`() {
            transaction {
                //Arrange - create and populate table with three bmis
                val hydrationDAO = populateHydrationsTable()

                //Act & Assert
                assertEquals(null, hydrationDAO.findById(4))
            }
        }

        @Test
        fun `get Hydration by id that exists, results in a correct Hydration returned`() {
            transaction {
                //Arrange - create and populate table with three bmis
                val hydrationDAO = populateHydrationsTable()

                //Act & Assert
                assertEquals(hyd3, hydrationDAO.findById(3))
            }

        }


        @Test
        fun `get all Hydrations over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(Hydrations)
                val hydrationDAO = HydrationDAO()

                //Act & Assert
                assertEquals(0, hydrationDAO.getAllHydration().size)
            }
        }

        @Test
        fun `get Hydrations by email that doesn't exist, results in no Hydrations returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val hydrationDAO = populateHydrationsTable()

                //Act & Assert
                assertEquals(0, hydrationDAO.findByEmail(nonExistingEmail).size)
            }
        }

        @Test
        fun `get Hydration by email that exists, results in correct Hydration returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val hydrationDAO = populateHydrationsTable()

                //Act & Assert
                assertEquals(hyd2, hydrationDAO.findByEmail(hyd2.useremail).get(0))
            }
        }

    }

    @Nested
    inner class CreateHydration {
        @Test
        fun `multiple Hydrations added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate table with three bmis
                val hydrationDAO = populateHydrationsTable()

                //Act & Assert
                assertEquals(3, hydrationDAO.getAllHydration().size)
                assertEquals(hyd1, hydrationDAO.findById(hyd1.id))
                assertEquals(hyd2, hydrationDAO.findById(hyd2.id))
                assertEquals(hyd3, hydrationDAO.findById(hyd3.id))
            }
        }
    }


    @Nested
    inner class DeleteHydrations {
        @Test
        fun `deleting a non-existant Hydration in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three bmis
                val hydrationDAO = populateHydrationsTable()

                //Act & Assert
                assertEquals(3, hydrationDAO.getAllHydration().size)
                hydrationDAO.delete(4)
                assertEquals(3, hydrationDAO.getAllHydration().size)
            }
        }

        @Test
        fun `deleting an existing Hydration in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three bmis
                val hydrationDAO = populateHydrationsTable()

                //Act & Assert
                assertEquals(3, hydrationDAO.getAllHydration().size)
                hydrationDAO.delete(hyd3.id)
                assertEquals(2, hydrationDAO.getAllHydration().size)
            }
        }
    }

    @Nested
    inner class Updatehydration {

        @Test
        fun `updating existing hydration in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three bmis
                val hydrationDAO = populateHydrationsTable()

                //Act & Assert
                val hyd3Updated = Hydration(3,170,"Lemon Water",DateTime("2024-11-20T09:00:00.000"),"user3@gmail.com");
                hydrationDAO.update(hyd3.id, hyd3Updated)
                assertEquals(hyd3, hydrationDAO.findById(3))
            }
        }

        @Test
        fun `updating non-existant Hydration in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                val hydrationDAO = populateHydrationsTable()

                //Act & Assert
                val sle4Updated = Hydration(4,180,"Water", DateTime("2024-11-28T09:00:00.000"),"user4@gmail.com");
                hydrationDAO.update(4, sle4Updated)
                assertEquals(null, hydrationDAO.findById(4))
                assertEquals(3, hydrationDAO.getAllHydration().size)
            }
        }
    }

    internal fun populateHydrationsTable(): HydrationDAO{
        SchemaUtils.create(Hydrations)
        val hydrationDAO = HydrationDAO()
        hydrationDAO.save(hyd1)
        hydrationDAO.save(hyd2)
        hydrationDAO.save(hyd3)
        return hydrationDAO
    }
}



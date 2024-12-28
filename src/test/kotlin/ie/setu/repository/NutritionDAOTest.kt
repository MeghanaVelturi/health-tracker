@file:JvmName("NutritionDAOTestKt")

package ie.setu.repository

import ie.setu.domain.Nutrition

import ie.setu.domain.db.Nutritions
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.BeforeAll

import ie.setu.domain.repository.NutritionDAO
import ie.setu.helpers.nonExistingEmail
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ie.setu.helpers.nuts
import org.joda.time.DateTime

//retrieving some test data from Fixtures
val nut1 = nuts[0]
val nut2 = nuts[1]
val nut3 = nuts[2]


class NutritionDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class ReadNutritions {

        @Test
        fun `getting all Nutritions from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate table with three users
                val nutritionDAO = populateNutritionsTable()

                //Act & Assert
                assertEquals(3, nutritionDAO.getAllNutritions().size)
            }
        }

        @Test
        fun `get nutrition by id that doesn't exist, results in no Nutrition returned`() {
            transaction {
                //Arrange - create and populate table with three bmis
                val nutritionDAO = populateNutritionsTable()

                //Act & Assert
                assertEquals(null, nutritionDAO.findById(4))
            }
        }

        @Test
        fun `get nutrition by id that exists, results in a correct nutrition returned`() {
            transaction {
                //Arrange - create and populate table with three bmis
                val nutritionDAO = populateNutritionsTable()

                //Act & Assert
                assertEquals(nut3, nutritionDAO.findById(3))
            }

        }


        @Test
        fun `get all nutritions over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(Nutritions)
                val nutritionDAO = NutritionDAO()

                //Act & Assert
                assertEquals(0, nutritionDAO.getAllNutritions().size)
            }
        }

        @Test
        fun `get nutritions by email that doesn't exist, results in no nutritions returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val nutritionDAO = populateNutritionsTable()

                //Act & Assert
                assertEquals(0, nutritionDAO.findByEmail(nonExistingEmail).size)
            }
        }

        @Test
        fun `get nutrition by email that exists, results in correct user returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val nutritionDAO = populateNutritionsTable()

                //Act & Assert
                assertEquals(nut2, nutritionDAO.findByEmail(nut2.useremail).get(0))
            }
        }

    }

    @Nested
    inner class CreateNutrition {
        @Test
        fun `multiple nutritions added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate table with three bmis
                val nutritionDAO = populateNutritionsTable()

                //Act & Assert
                assertEquals(3, nutritionDAO.getAllNutritions().size)
                assertEquals(nut1, nutritionDAO.findById(nut1.id))
                assertEquals(nut2, nutritionDAO.findById(nut2.id))
                assertEquals(nut3, nutritionDAO.findById(nut3.id))
            }
        }
    }


    @Nested
    inner class DeleteNutritions {
        @Test
        fun `deleting a non-existant nutrition in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three bmis
                val nutritionDAO = populateNutritionsTable()

                //Act & Assert
                assertEquals(3, nutritionDAO.getAllNutritions().size)
                nutritionDAO.delete(4)
                assertEquals(3, nutritionDAO.getAllNutritions().size)
            }
        }

        @Test
        fun `deleting an existing bmi in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three bmis
                val nutritionDAO = populateNutritionsTable()

                //Act & Assert
                assertEquals(3, nutritionDAO.getAllNutritions().size)
                nutritionDAO.delete(nut3.id)
                assertEquals(2, nutritionDAO.getAllNutritions().size)
            }
        }
    }

    @Nested
    inner class Updatenutritions {

        @Test
        fun `updating existing nutrition in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three bmis
                val nutritionDAO = populateNutritionsTable()

                //Act & Assert
                val nut3Updated = Nutrition(3,170,"Chicken Noodles", DateTime("2024-11-20T09:00:00.000"),"user3@gmail.com");
                nutritionDAO.update(nut3.id, nut3Updated)
                assertEquals(nut3, nutritionDAO.findById(3))
            }
        }

        @Test
        fun `updating non-existant nutrition in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                val nutritionDAO = populateNutritionsTable()

                //Act & Assert
                val cal4Updated = Nutrition(4,180,"Pasta", DateTime.now(),"user4@gmail.com");
                nutritionDAO.update(4, cal4Updated)
                assertEquals(null, nutritionDAO.findById(4))
                assertEquals(3, nutritionDAO.getAllNutritions().size)
            }
        }
    }

    internal fun populateNutritionsTable(): NutritionDAO {
        SchemaUtils.create(Nutritions)
        val nutritionDAO = NutritionDAO()
        nutritionDAO.save(nut1)
        nutritionDAO.save(nut2)
        nutritionDAO.save(nut3)
        return nutritionDAO
    }
}



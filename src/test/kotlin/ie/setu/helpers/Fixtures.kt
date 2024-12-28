package ie.setu.helpers

import ie.setu.domain.*
import org.joda.time.DateTime

val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"

val users = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", id = 1, password = "pass"),
    User(name = "Bob Cat", email = "bob@cat.ie", id = 2, password = "abcd"),
    User(name = "Mary Contrary", email = "mary@contrary.com", id = 3, password = "klmn"),
    User(name = "Carol Singer", email = "carol@singer.com", id = 4, password = "asdf")
)

val acts = arrayListOf<Activity>(
    Activity(1,"Completed Morning workout",1.0,400,4000,DateTime("2024-11-22T09:00:00.000"),"user1@gmail.com"),
    Activity(2,"Completed Excercise",2.3,700,8000,DateTime("2024-11-22T09:00:00.000"),"user2@gmail.com"),
    Activity(3,"Completed RUnning",1.4,400,4000,DateTime("2024-11-22T09:00:00.000"),"user3@gmail.com"),
    Activity(4,"Completed Morning workout",1.2,400,4000,DateTime("2024-11-22T09:00:00.000"),"user4@gmail.com"),


    )

val nuts = arrayListOf<Nutrition>(
    Nutrition(1,150,"Rice", DateTime("2024-11-22T09:00:00.000"),"user1@gmail.com"),
    Nutrition(2,100,"Egg", DateTime("2024-11-23T09:00:00.000"),"user2@gmail.com"),
    Nutrition(3,170,"Chicken Noodles", DateTime("2024-11-20T09:00:00.000"),"user3@gmail.com"),
    Nutrition(4,180,"Pasta", DateTime("2024-11-28T09:00:00.000"),"user4@gmail.com"),

    )

val hyds = arrayListOf<Hydration>(
    Hydration(1,150,"Water", DateTime("2024-11-22T09:00:00.000"),"user1@gmail.com"),
    Hydration(2,100,"Cool Drink", DateTime("2024-11-23T09:00:00.000"),"user2@gmail.com"),
    Hydration(3,170,"Lemon Water",DateTime("2024-11-20T09:00:00.000"),"user3@gmail.com"),
    Hydration(4,180,"Water", DateTime("2024-11-28T09:00:00.000"),"user4@gmail.com"),

    )

val wgs = arrayListOf<WeightGoal>(
    WeightGoal(1,"weightloss",60, DateTime("2024-11-22T09:00:00.000"),DateTime("2024-12-22T09:00:00.000"),"user1@gmail.com"),
    WeightGoal(2,"weightgain",80, DateTime("2024-11-23T09:00:00.000"),DateTime("2024-12-23T09:00:00.000"),"user2@gmail.com"),
    WeightGoal(3,"weightloss",70,DateTime("2024-11-20T09:00:00.000"),DateTime("2024-12-20T09:00:00.000"),"user3@gmail.com"),
    WeightGoal(4,"weightgain",90, DateTime("2024-11-28T09:00:00.000"),DateTime("2024-12-28T09:00:00.000"),"user4@gmail.com"),

    )



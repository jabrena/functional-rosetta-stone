package level1

data class Person(val name: String, val age: Int)

fun main() {

    val person = Person("Juan Antonio", 40)

    //Destructoring
    val (name, age) = person
    println(name)
    println(age)
}
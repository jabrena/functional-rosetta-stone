package arrow.option

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class EmployeeUnitTest : FunSpec() {

    init {
        test("should return department name in uppercase") {
            val employee = Employee(name = "John", department = Department("hr"))
            val departmentName = employee.departmentName()

            departmentName shouldBe "HR"
        }

        test("should return unknnown department name in uppercase") {
            val employee = Employee(name = "John")
            val departmentName = employee.departmentName()

            departmentName shouldBe "UNASSIGNED"
        }
    }
}
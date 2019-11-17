package arrow.curry

data class CreditCard(val creditInfo: CreditCardInfo, val issuer: Person, val account: Account)

object CreditCardCompute {
    fun premium(totalCards: Int, creditCard: CreditCard): Double {
        return 12.0
    }
}

data class CreditCardInfo(val number: String)
data class Person(val name: String)
data class Account(val number: String)

//https://lukajcb.github.io/blog/scala/2016/03/08/a-real-world-currying-example.html
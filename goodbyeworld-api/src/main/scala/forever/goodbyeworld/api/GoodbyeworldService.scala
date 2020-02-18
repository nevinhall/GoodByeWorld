package forever.goodbyeworld.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.Service.restCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.broker.kafka.{KafkaProperties, PartitionKeyStrategy}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

object GoodbyeworldService {
  val TOPIC_NAME = "greetings"
}

case class Person(name: String, age: Int, address: Address)

object Person {
  final implicit val format: Format[Person] = Json.format[Person]
}

case class Address(first: String, second: String)


object Address {
  final implicit val format: Format[Address] = Json.format[Address]
}

trait PersonService extends Service {


  def getPerson(fname: String): ServiceCall[NotUsed, Person]

  def createPerson: ServiceCall[Person, String]

  def updatePersonName(currentName: String): ServiceCall[String, Person]

  def deletePerson: ServiceCall[String, String]


  override final def descriptor: Descriptor = {
    import Service._
    // @formatter:off
    named("goodbyeworld")
      .withCalls(
        restCall(Method.POST, "/api/Nevin/", createPerson),
        restCall(Method.GET,"/api/Nevin/:fname", getPerson _),
        restCall(Method.PUT,"/api/Nevin/:fname", updatePersonName _),
        restCall(Method.DELETE, "/api/Nevin/", deletePerson)

      )
      .withAutoAcl(true)
    // @formatter:on
  }
}


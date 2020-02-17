package forever.goodbyeworld.impl

import forever.goodbyeworld.api
import forever.goodbyeworld.api.{GoodbyeworldService, Person, PersonService}
import akka.Done
import akka.NotUsed
import akka.cluster.sharding.typed.scaladsl.ClusterSharding
import akka.cluster.sharding.typed.scaladsl.EntityRef
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.EventStreamElement
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import akka.util.Timeout
import com.lightbend.lagom.scaladsl.api.transport.BadRequest

import scala.collection.+:

class GoodbyeworldServiceImpl(
                               clusterSharding: ClusterSharding,
                               persistentEntityRegistry: PersistentEntityRegistry
                             )(implicit ec: ExecutionContext)
  extends PersonService {

  var currentPeople: List[Person] = List()

  override def createPerson: ServiceCall[Person, String] = { p =>
    addToList(p)
    Future(p.name)
  }

  def addToList(p: Person): Unit = {
    currentPeople = currentPeople :+ p
  }

  override def getPerson(fname: String): ServiceCall[NotUsed, Person] = { _ =>
    Future {
      val foundPerson: Option[Person] = currentPeople.find(p => p.name == fname)
      foundPerson.get
    }
  }
}

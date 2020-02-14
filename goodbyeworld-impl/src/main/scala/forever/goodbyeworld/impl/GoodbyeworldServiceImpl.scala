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

/**
 * Implementation of the GoodbyeworldService.
 */
class GoodbyeworldServiceImpl(
                               clusterSharding: ClusterSharding,
                               persistentEntityRegistry: PersistentEntityRegistry
                             )(implicit ec: ExecutionContext)
  extends PersonService {



//  override def getPerson(id: String): ServiceCall[NotUsed, String] = ServiceCall {
//   _ =>
//    Future("GoodbBye world " + id)
//  }


  override def createPerson : ServiceCall[Person, String] = { p =>
        Future(p.name)
  }
}

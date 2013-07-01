package de.caseclass.webservices.template.persistence

import javax.persistence.{EntityManager, Persistence}
import de.caseclass.webservices.template.util.Logging
import de.caseclass.webservices.template.BaseConfig


/**
 * Created with IntelliJ IDEA.
 * User: slauer
 * Date: 02.05.12
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */

trait PersistenceComponent {
  this: Logging =>

  def withEntityManager[T](cb: EntityManager => T): T = {
    val em = PersistenceComponent.entityManagerFactory.createEntityManager()
    try {
      cb(em)
    } finally {
      em.close()
    }
  }

  implicit protected def toTransaction(em: EntityManager) = new {
    def withTranaction[T](cb: => T): T = {
      em.getTransaction.begin()
      try {
        var r = cb
        em.getTransaction.commit()
        r
      }
      catch {
        case e => {
          em.getTransaction.rollback()
          throw e
        }
      }
    }
  }
}

object PersistenceComponent extends BaseConfig {

  import collection.JavaConversions._

  private val props: Map[String, String] = persistenceConfig.entrySet().iterator().toList.map {
    entry => entry.getKey -> entry.getValue.unwrapped().toString
  }.toMap

  private lazy val entityManagerFactory = Persistence.createEntityManagerFactory("CC_REPLICATION", props)

  def stop = entityManagerFactory.close()
}



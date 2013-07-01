package de.caseclass.webservices.template.web

import javax.servlet.{ServletContextEvent, ServletContextListener}
import de.caseclass.webservices.template.persistence.PersistenceComponent
import de.caseclass.webservices.template.BaseConfig


/**
 * Created by IntelliJ IDEA.
 * User: slauer
 * Date: 14.03.12
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */

class WebServiceContextListener extends ServletContextListener with BaseConfig {
  def contextInitialized(sce: ServletContextEvent) {

  }

  def contextDestroyed(sce: ServletContextEvent) {

    PersistenceComponent.stop

  }
}

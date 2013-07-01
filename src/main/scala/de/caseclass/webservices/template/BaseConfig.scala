package de.caseclass.webservices.template

import com.typesafe.config.ConfigFactory

/**
 * Created with IntelliJ IDEA.
 * User: stefan
 * Date: 15.07.12
 * Time: 12:49
 * To change this template use File | Settings | File Templates.
 */

object BaseConfig {


  private val config = ConfigFactory.load().withFallback(
    ConfigFactory.parseString( """
       webservice-template {

         env = "foo"

         foo  {
           persistence {
             hibernate.dialect = "org.hibernate.dialect.MySQLDialect"
             hibernate.connection.datasource = "jdbc_dbreplication"
             hibernate.jndi.url = "localhost:1099"
             hibernate.jndi.class = "org.jnp.interfaces.NamingContextFactory"
             hibernate.jndi.type value = "javax.sql.DataSource"
           }
         }
       }"""

    )
  ).getConfig("webservice-template")

  private val env = config.getString("env")
  private val persistenceConfig = config.getConfig(env + ".persistence")

}

trait BaseConfig {

  lazy val env = BaseConfig.env

  lazy val persistenceConfig = BaseConfig.persistenceConfig

  lazy val config = BaseConfig.config

}



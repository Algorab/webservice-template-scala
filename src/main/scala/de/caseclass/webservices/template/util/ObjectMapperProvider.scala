package de.caseclass.webservices.template.util

import javax.ws.rs.ext.{ContextResolver, Provider}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

@Provider
class ObjectMapperProvider extends ContextResolver[ObjectMapper] {

  override def getContext(`type`: Class[_]): ObjectMapper = {
    val om = new ObjectMapper()
    om.registerModule(DefaultScalaModule)
    om
  }
}
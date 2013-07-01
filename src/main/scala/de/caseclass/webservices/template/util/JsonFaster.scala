package de.caseclass.webservices.template.util

import java.lang.reflect.{Type, ParameterizedType}
import java.io.StringWriter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.core.`type`.TypeReference


/**
 * Created with IntelliJ IDEA.
 * User: slauer
 * Date: 04.04.13
 * Time: 06:43
 * To change this template use File | Settings | File Templates.
 */
object JsonFaster {
  /*
   FasterXML / jackson-module-scala
   */

  private [this] lazy val mapper = {
    val result = new ObjectMapper
    result.registerModule(DefaultScalaModule)
    result
  }

  private [this] def typeReference[T: Manifest] = new TypeReference[T] {
    override def getType = typeFromManifest(manifest[T])
  }

  private [this] def typeFromManifest(m: Manifest[_]): Type = {
    if (m.typeArguments.isEmpty) { m.runtimeClass }
    else new ParameterizedType {
      def getRawType = m.runtimeClass

      def getActualTypeArguments = m.typeArguments.map(typeFromManifest).toArray

      def getOwnerType = null
    }
  }

  def deserialize[T: Manifest](value: String) : T =
    mapper.readValue(value, typeReference[T])


  def serialize(value: Any): String = {
    val writer = new StringWriter()
    mapper.writeValue(writer, value)
    writer.toString
  }

}



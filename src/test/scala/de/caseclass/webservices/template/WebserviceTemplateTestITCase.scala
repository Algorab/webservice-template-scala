package de.caseclass.webservices.template

/**
 * Created by IntelliJ IDEA.
 * User: slauer
 * Date: 06.02.12
 * Time: 07:36
 * To change this template use File | Settings | File Templates.
 */


import org.specs2.mutable._
import com.sun.jersey.api.client.{Client, ClientResponse}
import javax.ws.rs.core.Response.Status
import persistence.entities
import entities.DummyDTO
import com.sun.jersey.api.client.config.DefaultClientConfig
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider
import com.sun.jersey.api.client.filter.LoggingFilter
import javax.ws.rs.core.MediaType
import de.caseclass.webservices.template.util.ObjectMapperProvider


class WebserviceTemplateTestITCase extends SpecificationWithJUnit with BaseConfig {

  sequential

  val url = "http://localhost:8088/webservice-template-scala/v1/dummy"
  var location = ""
  var dummyDTO = DummyDTO(200,"bar")

  def client(path: String = "") = {
    val cc = new DefaultClientConfig()
    cc.getClasses.add(classOf[JacksonJsonProvider])
    cc.getClasses.add(classOf[ObjectMapperProvider])

    val c = Client.create(cc)
    c.addFilter(new LoggingFilter())
    c.resource(path)
      .`type`(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
  }

  "The Webservice Resource" should {

    "get a imported dummy data with key 1" in {
      val cr = client(url+ "/1").get(classOf[ClientResponse])
      cr.getStatus must_== Status.OK.getStatusCode
      val resDummy = cr.getEntity(classOf[DummyDTO])
      resDummy.id.toString must_== "1"
      resDummy.name must_== "foo"
    }

    "get a imported unmapped dummy data with key 1" in {
      val cr = client(url+ "/1").get(classOf[ClientResponse])
      cr.getStatus must_== Status.OK.getStatusCode
      val resDummy = cr.getEntity(classOf[DummyDTO])
      resDummy.id.toString must_== "1"
      resDummy.name must_== "foo"
    }

    "create a dummy data" in {
      val cr = client(url).post(classOf[ClientResponse], dummyDTO)
      location = cr.getLocation.toString

      cr.getStatus must_== Status.CREATED.getStatusCode
      location must_== "http://localhost:8088/webservice-template-scala/v1/dummy/200"
    }

    "get a dummy data with key 200" in {

      val cr = client(location).get(classOf[ClientResponse])
      cr.getStatus must_== Status.OK.getStatusCode
      val resDTO = cr.getEntity(classOf[DummyDTO])
      resDTO.id.toString must_== "200"
    }

    "update dummy data" in {
      var cr = client(url).put(classOf[ClientResponse], DummyDTO(200, "updated"))
      cr.getStatus must_== Status.OK.getStatusCode
      val resDTO = client(location).get(classOf[DummyDTO])
      println("resDTO = " + resDTO)

      resDTO.id.toString must_==  "200"
      resDTO.name must_== "updated"

    }

    "delete dummy data" in {
      var cr = client(location).delete(classOf[ClientResponse])
      cr.getStatus must_== Status.OK.getStatusCode
    }
  }

}
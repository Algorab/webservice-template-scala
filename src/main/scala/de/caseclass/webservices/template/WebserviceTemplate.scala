package de.caseclass.webservices.template

import javax.ws.rs._
import core.{UriInfo, Context, Response}
import javax.ws.rs.core.MediaType._
import persistence.entities.{DummyDTO, Dummy}
import persistence.PersistenceComponent
import util.Logging

/**
 * Created with IntelliJ IDEA.
 * User: slauer
 * Date: 13.07.12
 * Time: 11:55
 * To change this template use File | Settings | File Templates.
 *
 * JPA persist vs. merge => http://stackoverflow.com/questions/1069992/jpa-entitymanager-why-use-persist-over-merge
 *
 */




@Path("/dummy")
@Produces(Array(APPLICATION_JSON))
@Consumes(Array(APPLICATION_JSON))
class WebserviceTemplate extends PersistenceComponent with Logging{


  import de.caseclass.webservices.template.persistence.entities.DummyImplicits._

  @Context
  var uriInfo: UriInfo = _


  @GET
  @Path("/{id}")
  def getDummy(@PathParam("id") id: Int) = {

    withEntityManager {
      em =>
        val result =  em.createNativeQuery("select * from cc.dummy where id=:id",classOf[Dummy]).setParameter("id",id).getSingleResult.asInstanceOf[Dummy]
      Response.ok(result.toDTODummy).build()
    }
  }

  @GET
  @Path("/unmapped/{id}")
  def getUnmappedDummy(@PathParam("id") id: Int) = {
    withEntityManager {
      em =>
        //val result =
          em.createNativeQuery("select * from cc.dummy where id=:id",classOf[Dummy]).setParameter("id",id).getSingleResult.asInstanceOf[Dummy]
        //Response.ok(result.toDTODummy).build()
    }
  }

  @POST
  def createDummy(dummyDTO: DummyDTO) = {
    withEntityManager {
      em => em.withTranaction {
       val dummy = dummyDTO.toCCDummy
       em.persist(dummy)
       val uri = uriInfo.getBaseUriBuilder.path("dummy/{id}").build(dummy.id.asInstanceOf[AnyRef])
       Response.created(uri).build()
      }
    }
  }

  @PUT
  def updateDummy(dummyDTO: DummyDTO) = {
    withEntityManager {
      em => em.withTranaction {
        val dummy = dummyDTO.toCCDummy
        em.merge(dummy)

        Response.ok(dummy.toDTODummy).build()
      }
    }
  }

  @DELETE
  @Path("/{id}")
  def deleteDummy(@PathParam("id") id: Int) = {
    withEntityManager {
      em => em.withTranaction {
        em.remove(em.createQuery("from Dummy where id=:id",classOf[Dummy]).setParameter("id",id).getSingleResult)
        Response.ok().build()
      }
    }
  }




}

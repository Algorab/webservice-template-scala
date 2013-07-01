package de.caseclass.webservices.template.persistence.entities

import javax.persistence.{Id, Table, Entity}


/**
 * Created with IntelliJ IDEA.
 * User: slauer
 * Date: 24.08.12
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */

object DummyImplicits {
  implicit class DtoConverter[A](o: A) {
    def toCCDummy: Dummy = {
      val toConvert = o.asInstanceOf[DummyDTO]
      val dummy = new Dummy
      dummy.id = toConvert.id
      dummy.name = toConvert.name
      dummy
    }
    def toDTODummy: DummyDTO = {
      val dummy = o.asInstanceOf[Dummy]
      DummyDTO(dummy.id, dummy.name)
    }
  }
}


case class DummyDTO(id: Int, name: String)

@Entity
@Table(name = "dummy", schema = "cc")
case class Dummy() {
  @Id
  var id: Int = _

  var name: String = _
}


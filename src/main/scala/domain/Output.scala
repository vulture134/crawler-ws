package domain

import zio.json.*

case class Output(data: List[(String, String)])

object Output {

  given encoder: JsonEncoder[Output] = DeriveJsonEncoder.gen[Output]

}
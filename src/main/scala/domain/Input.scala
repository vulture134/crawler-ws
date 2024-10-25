package domain

import zio.json.*

case class Input (urls: List[String])

object Input {

  given decoder: JsonDecoder[Input] = DeriveJsonDecoder.gen[Input]

}
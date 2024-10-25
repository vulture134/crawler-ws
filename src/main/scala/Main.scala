import zio.logging.backend.SLF4J
import zio.*
import server.CrawlerHttp.httpApp
import zio.http.Server
import zio.http.*

object Main extends ZIOAppDefault {

  override val bootstrap = Runtime.removeDefaultLoggers ++ SLF4J.slf4j

  val serverConfigLayer: ZLayer[Any, Throwable, ServerConfig] = ZLayer(ZIO.attempt(ServerConfig.default.port(9078)))  

  val run = Server.serve(httpApp).provide(serverConfigLayer, Server.live)

}
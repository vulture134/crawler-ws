package server

import domain.{Input, Output}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.*
import net.ruippeixotog.scalascraper.dsl.DSL.Extract.*
import net.ruippeixotog.scalascraper.model.*
import zio.*
import zio.http.*
import zio.http.model.*

object CrawlerHttp {

  val http = Http.collectZIO[Request] {
    case req@Method.POST -> _ / "input" => req.body.asString
      .map(json => Input.decoder.decodeJson(json))
      .flatMap(either => ZIO.fromEither(either).mapError(err => new Throwable(err)))
      .flatMap(input => getTitles(input.urls))
      .map(Response.json)
  }

  def getTitles(urls: List[String]) =
  ZIO.foreachPar(urls)(reqUrl => for {
    res <- Client.request(
      url = reqUrl,
      method = Method.GET,
      headers = Headers(Iterable.empty),
      content = Body.empty)
    data <- res.body.asString
    browser = JsoupBrowser()
    doc = browser.parseString(data)
    title = doc >> text("title")
    } yield (reqUrl, title)
  ).map(list => Output.encoder.encodeJson(Output(list))).provideLayer(Client.default)

  val httpApp = http
    .tapErrorZIO(err => ZIO.logError(s"$err"))
    .mapError(err => Response(status = Status.InternalServerError, body = Body.fromString(s"$err")))

}

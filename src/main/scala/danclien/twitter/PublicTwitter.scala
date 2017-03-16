package danclien.twitter

import io.circe.Json
import jawnstreamz._
import org.http4s._
import org.http4s.client.blaze._
import org.http4s.client.oauth1

import scalaz.concurrent.Task
import scalaz.stream.async.mutable.Queue
import scalaz.stream.Process

// Based on the http4s code:
// http://http4s.org/v0.15/streaming/#consuming-streams-with-the-client
object PublicTwitter {
  implicit val f = io.circe.jawn.CirceSupportParser.facade

  def sign(consumerKey: String, consumerSecret: String, accessToken: String, accessSecret: String)(req: Request): Task[Request] = {
    val consumer = oauth1.Consumer(consumerKey, consumerSecret)
    val token    = oauth1.Token(accessToken, accessSecret)
    oauth1.signRequest(req, consumer, callback = None, verifier = None, token = Some(token))
  }

  def getProcess(consumerKey: String, consumerSecret: String, accessToken: String, accessSecret: String)(queue: Queue[Json]): Process[Task, Unit] = {
    val client = PooledHttp1Client()

    val req = Request(Method.GET, Uri.uri("https://stream.twitter.com/1.1/statuses/sample.json"))

    val stream = for {
      sr  <- Process.eval(sign(consumerKey, consumerSecret, accessToken, accessSecret)(req))
      res <- client.streaming(sr)(resp => resp.body.parseJsonStream)
    } yield res

    stream.take(1).to(queue.enqueue).onComplete(Process.eval_(client.shutdown))
  }
}
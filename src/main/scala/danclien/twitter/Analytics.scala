
package danclien.twitter

import io.circe.{Json, JsonObject}

import scalaz.concurrent.Task
import scalaz.stream.Process
import scalaz.stream.async.mutable.Queue
import scalaz.stream.io.stdOutLines

object Analytics {
  implicit val f = io.circe.jawn.CirceSupportParser.facade

  def getProcess(queue: Queue[Json]): Process[Task, Unit] = {
    queue.dequeue.take(1).map(_.spaces2).to(stdOutLines)
  }
}

sealed trait TweetResult
case class TweetData(name: String) extends TweetResult
case class Deleted(jsonObj: JsonObject) extends TweetResult
case class LocationDeleted(jsonObj: JsonObject) extends TweetResult
case class LimitReached(jsonObj: JsonObject) extends TweetResult
case class StatusWithheld(jsonObj: JsonObject) extends TweetResult
case class Disconnected(jsonObj: JsonObject) extends TweetResult
case class UserWithheld(jsonObj: JsonObject) extends TweetResult
case class Stalled(jsonObj: JsonObject) extends TweetResult
case class UserUpdate(jsonObj: JsonObject) extends TweetResult
case class Unknown(json: Json) extends TweetResult

object TweetData {
  def apply(json: Json): TweetResult = {
    json.asObject match {
      case Some(obj) => parseJsonObject(obj)
      case None      => Unknown(json)
    }
  }

  def parseJsonObject(obj: JsonObject): TweetResult = {
    val delete = obj("delete")
    val scrub_geo = obj("scrub_geo")
    val limit = obj("limit")
    val status_withheld = obj("status_withheld")
    val user_withheld = obj("user_withheld")
    val disconnect = obj("disconnect")
    val warning = obj("warning")
    val event = obj("event")
    val isUserUpdate = event.map(_.asString) == Some("user_update")

    if      (delete.isDefined)          { new Deleted(obj) }
    else if (scrub_geo.isDefined)       { new LocationDeleted(obj) }
    else if (limit.isDefined)           { new LimitReached(obj) }
    else if (status_withheld.isDefined) { new StatusWithheld(obj) }
    else if (user_withheld.isDefined)   { new UserWithheld(obj) }
    else if (disconnect.isDefined)      { new Disconnected(obj) }
    else if (warning.isDefined)         { new Stalled(obj) }
    else if (isUserUpdate)              { new UserUpdate(obj) }
    else                                { parseData(obj) }
  }

  def parseData(obj: JsonObject): TweetResult = {
    ???
  }

}

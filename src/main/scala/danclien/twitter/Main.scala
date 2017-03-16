package danclien.twitter

import io.circe.Json

import scalaz.stream.async.mutable.Queue
import scalaz.stream.{Process, async, merge}

object Main {
  def main(args: Array[String]): Unit = {
    val consumerKey = sys.env("TWITTER_CONSUMER_KEY")
    val consumerSecret = sys.env("TWITTER_CONSUMER_SECRET")
    val accessToken = sys.env("TWITTER_ACCESS_TOKEN")
    val accessSecret = sys.env("TWITTER_ACCESS_SECRET")

    val jsonQueue: Queue[Json] = async.boundedQueue[Json](100)

    val enqueueProcess = PublicTwitter.getProcess(consumerKey, consumerSecret, accessToken, accessSecret)(jsonQueue)
    val dequeueProcess = Analytics.getProcess(jsonQueue)

    val processes = Process(
      enqueueProcess,
      dequeueProcess
    )

    merge.mergeN(10)(processes).run.unsafePerformSync
  }
}

package danclien.twitter.analysis

import io.circe.optics.JsonPath._
import io.circe.Json

// Each hashtag and how many times they appear
case class HashtagCounts(data: Map[String, Int])

// Aggregate
case class AggregateHashtags()

object Hashtags {
  def analyze(json: Json): HashtagCounts = {
    // Get all the hashtags in the `Json`
    val hashtags = root.entities.hashtags.each.text.string.getAll(json)

    // Count the number times each hashtag appears
    HashtagCounts(hashtags.groupBy(identity).mapValues(_.size))
  }
}
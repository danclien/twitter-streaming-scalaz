package danclien.twitter.analysis

import io.circe.Json

trait TweetAnalysis[Value, Aggregate] {
  def analyze(json: Json): Value
}
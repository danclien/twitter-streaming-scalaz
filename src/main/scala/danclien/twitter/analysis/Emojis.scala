package danclien.twitter.analysis

import io.circe.Json

// Each emoji and how many times they appear
case class EmojiCounts(data: Map[Char, Int])

// Aggregate
case class AggregateEmojis()

object Emojis extends TweetAnalysis[EmojiCounts, AggregateEmojis] {
  def analyze(json: Json): EmojiCounts = {
    new EmojiCounts(Map('a' -> 1, 'b' -> 2))
  }

  // Load emoji data here
  //Map[Char, Int]()

  // Define a Monoid instance for TweetEmojis
}
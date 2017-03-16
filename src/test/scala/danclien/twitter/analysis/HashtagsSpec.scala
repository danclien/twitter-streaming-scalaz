package danclien.twitter.analysis

import org.scalatest._
import scala.io.Source
import io.circe._, io.circe.parser._


class HashtagsSpec extends FlatSpec with Matchers {
  "Hashtags.analyze" should "return all hashtags in a tweet" in {
    val rawJson = Source.fromResource("hashtags.json").getLines.mkString(" ")
    val parseResult: Either[ParsingFailure, Json] = parse(rawJson)

    val result = parseResult.map(json => Hashtags.analyze(json))
    val expected = Right(HashtagCounts(Map("212RMX" -> 1)))

    result should be (expected)
  }
}
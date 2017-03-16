# Twitter Streaming

## TODO

* Individual tweet analysis
  * Emojis
  * Hashtags
    * More tests
  * Photos
  * Url domains
* Aggregate tweet analysis
  * Total number of tweets
  * Number of tweets by second/minute/hour
  * Top emojis
  * Percent of tweets containing emojis
  * Top hashtags
  * Percent of tweets container a URL
  * Percent of tweets containing a photo URL
  * Top domains in URLs
* Interface to display results
* Parallelism
* Automatic failure recovery
* Approximate data structures
* Error handling for environment variables


## Getting Started

### Requirements

* `sbt`


### Run

1. Set your Twitter credentials in the following environment variables:
  * `TWITTER_CONSUMER_KEY`
  * `TWITTER_CONSUMER_SECRET`
  * `WITTER_ACCESS_TOKEN`
  * `TWITTER_ACCESS_SECRET`
2. `sbt run`


## Notes

* Total number of tweets received
  * Intermediate: Nothing
  * Aggregate: `Int`  (total number of tweets)
* Average tweets per hour/minute/second
  * Intermediate: Created at time `DateTime`
  * Aggregate: `(DateTime, Int)`?
* Top emojis in tweets
  * Intermediate: `Map[Char, Int]`
  * Aggregate: `PriorityQueue[(Char, Int)]`?
* Percent of tweets that contains emojis  
  * Intermediate: Use intermediate for "top emojis in tweets"
  * Aggregate: `Int` (total number of tweets containing emoji)
* Top hashtags
  * Intermediate: `Map[String, Int]`
  * Aggregate: `PriorityQueue[(String, Int)]`?
* Percent of tweets that contain a url
  * Intermediate: `Bool`
  * Aggregate: `Int` (total number of tweets containing a URL)
* Percent of tweets that contain a photo URL (pic.twitter.com or instagram)
  * Intermediate: `Bool`
  * Aggregate: `Int` (total number of tweets containing a photo URL)
* Top domains of urls in tweets   
  * Intermediate: `[String]`
  * Aggregate: ???

### Message types
* Blank lines
* Status deletion notices (delete)
* Location deletion notices (scrub_geo)
* Limit notices (limit)
* status_withheld
* user_withheld
* Disconnect messages (disconnect)
* Stall warnings (warning)
* User update

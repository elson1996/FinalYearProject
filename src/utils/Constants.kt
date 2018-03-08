package utils

/**
 * This object holds all the constants used in this project
 *
 * Created by elson on 11/2/18.
 */
object Constants {
    // Name of the Topology. Used while launching the LocalCluster
    const val TOPOLOGY_NAME = "SentimentAnalysis"

    // Max allowed question marks in a string. Beyond this value the affect of the
    // Question marks will be considered the same.
    const val MAX_QUESTION_MARKS = 3

    const val PRECEDING_TRIGRAM_WINDOW = 3
    const val PRECEDING_BIGRAM_WINDOW = 2
    const val PRECEDING_UNIGRAM_WINDOW = 1
    const val MAX_EXCLAMATION_MARKS = 4

    // This is the window size within which processing will be done.
    // This means that we will only use unigrams, bigrams and trigrams.
    const val MAX_GRAM_WINDOW_SIZE = 3

    //This regex checks if a string has only alphabets and no special characters or numbers.
    const val NON_NUMERIC_STRING_REGEX = ".*[a-zA-Z]+.*"

    val URL_PREFIXES = arrayOf("http://", "https://", "www")
    const val VADER_FILE_PATH = "VADER/lexicon.txt"

    object EMITTED_TUPLE_NAMES {
        const val TWEET_JSON = "tweet-json"
        const val RAW_TWEET = "raw-tweet"
        const val TWEET_WITH_SENTIMENT = "tweet-with-sentiment"
    }

    object BOLT_OR_SPOUT_NAMES {
        const val TWEET_KAFKA_SPOUT = "tweet-kafka-spout"
        const val TWEET_PARSER_BOLT = "tweet-parser-bolt"
        const val TWEET_SENTIMENT_BOLT = "tweet-sentiment-bolt"
    }

    // In order to create the spout, we need to get twitter credentials
    // for using Twitter Firehose and update it below. Create the credentials at
    // https://apps.twitter.com
    const val OAUTH_ACCESS_TOKEN = "707193271085195264-fq4yVwn1zvJPxKBlxjOUAaiVkXFp5ew"
    const val OAUTH_ACCESS_TOKEN_SECRET = "VsZnHrtCQVWr3UZHxB6eWrn4u4H1Ptl7NM0BEIc1M0G5J"
    const val OAUTH_CONSUMER_KEY = "SHo4lHNWxFvRFMs4XRoFFHXqi"
    const val OAUTH_CONSUMER_SECRET = "R3ZX67z2nnPkiPv7TQ45Ajxx6foN2LmnuMhfCfBR8v0IY3fclY"
    const val KAFKA_TOPIC = "kafka.twitter.raw.topic"

    @JvmField val LANGUAGES = arrayOf("en")
    @JvmField val TOPIC = arrayOf("modi", "bjp")
    const val ZOOKEEPER_HOST = "localhost:2181"
}
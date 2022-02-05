package upf.edu.parser;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.util.Optional;

public class SimplifiedTweet{

  private static JsonParser parser = new JsonParser();

  private final long tweetId;			  // the id of the tweet ('id')
  private final String text;  		      // the content of the tweet ('text')
  private final long userId;			  // the user id ('user->id')
  private final String userName;		  // the user name ('user'->'name')
  private final String language;          // the language of a tweet ('lang')
  private final long timestampMs;		  // seconduserIds from epoch ('timestamp_ms')

  public SimplifiedTweet(long tweetId, String text, long userId, String userName,
                         String language, long timestampMs) {
    this.language = language;
    this.text = text;
    this.timestampMs = timestampMs;
    this.tweetId = tweetId;
    this.userId = userId;
    this.userName = userName;

  }

  /**
   * Returns a {@link SimplifiedTweet} from a JSON String.
   * If parsing fails, for any reason, return an {@link Optional#empty()}
   *
   * @param jsonStr
   * @return an {@link Optional} of a {@link SimplifiedTweet}
   */
  public static Optional<SimplifiedTweet> fromJson(String jsonStr) {
    long tweetId;
    String text;
    long userId;
    String userName;
    String language;
    long timestampMs;



    JsonElement je = JsonParser.parseString(jsonStr);
    JsonObject jo = je.getAsJsonObject();
    SimplifiedTweet tweet  = null;

    if (jo.has("lang") && jo.has("text") && jo.has("timestamps_ms") && jo.has("id")){
      tweetId = jo.get("id").getAsLong();
      text = jo.get("text").getAsString();
      language = jo.get("lang").getAsString();
      timestampMs = jo.get("timestamp_ms").getAsLong();

      if(jo.has("user")){
        JsonObject userObj = jo.getAsJsonObject("user");
        if (userObj.has("name") && userObj.has("user_id")){
          userName = userObj.get("name").getAsString();
          userId = userObj.get("user_id").getAsLong();
          tweet = new SimplifiedTweet(tweetId, text, userId, userName, language, timestampMs);
        }
      }
    }
    return Optional.ofNullable(tweet);


  }


  @Override
  public String toString() {
// Overriding how SimplifiedTweets are printed in console or the output file
// The following line produces valid JSON as output
    return new Gson().toJson(this);
  }
  public static JsonParser getParser() {
    return parser;
  }

  public long getTimestampMs() {
    return timestampMs;
  }

  public long getTweetId() {
    return tweetId;
  }

  public long getUserId() {
    return userId;

  }

  public String getText() {
    return text;
  }

  public String getUserName() {
    return userName;
  }

  public String getLanguage() {
    return language;
  }

  public static void setParser(JsonParser parser) {
    SimplifiedTweet.parser = parser;
  }

}

package upf.edu.model;

import com.google.gson.*;

import java.util.Optional;

public class SimplifiedTweet{

  private static final JsonParser parser = new JsonParser();

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
   * @param jsonStr a json string
   * @return an {@link Optional} of a {@link SimplifiedTweet}
   */
  public static Optional<SimplifiedTweet> fromJson(String jsonStr) {
    long tweetId;
    String text;
    long userId;
    String userName;
    String language;
    long timestampMs;

    SimplifiedTweet tweet  = null;
    JsonElement je = null;

    try{
    je = JsonParser.parseString(jsonStr);
    }
    catch (JsonSyntaxException badJson){
      return Optional.empty();
    }
    if (je.isJsonNull()){return Optional.empty();}
    JsonObject jo = je.getAsJsonObject();

    if (jo.has("lang") && jo.has("text") && jo.has("timestamp_ms") && jo.has("id")){
      tweetId = jo.get("id").getAsLong();
      text = jo.get("text").getAsString();
      language = jo.get("lang").getAsString();
      timestampMs = jo.get("timestamp_ms").getAsLong();

      if(jo.has("user")){
        JsonObject userObj = jo.getAsJsonObject("user");

        if (userObj.has("name") && userObj.has("id")){
          userName = userObj.get("name").getAsString();
          userId = userObj.get("id").getAsLong();
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

  public String getLanguage() {
    return language;
  }

}

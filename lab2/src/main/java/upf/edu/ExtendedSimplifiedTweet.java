package upf.edu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import scala.Array;
import scala.Serializable;
import upf.edu.parser.SimplifiedTweet;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ExtendedSimplifiedTweet implements Serializable {
    private final long tweetId;
    private final String text;
    private final long userId;
    private final String userName;  // the user name (’user’->’name’)
    private final long followersCount; // the number of followers (’user’->’followers_count’)
    private final String language;  // the language of a tweet (’lang’)
    private final boolean isRetweeted; // is it a retweet? (the object ’retweeted_status’ exists?)
    private final Long retweetedUserId; // [if retweeted] (’retweeted_status’->’user’->’id’)
    private final Long retweetedTweetId; // [if retweeted] (’retweeted_status’->’id’)
    private final long timestampMs; // seconds from epoch (’timestamp_ms’)
    private final String retweeterUserName;

    public ExtendedSimplifiedTweet(long tweetId, String text, long userId, String userName,
                                   long followersCount, String language, boolean isRetweeted,
                                   Long retweetedUserId, String retweetedUserName, long timestampMs, Long retweetedTweetId) {
        this.tweetId = tweetId;
        this.text = text;
        this.userId = userId;
        this.userName = userName;
        this.followersCount = followersCount;
        this.language = language;
        this.isRetweeted = isRetweeted;
        this.retweeterUserName = retweetedUserName
        this.retweetedUserId = retweetedUserId;
        this.retweetedTweetId = retweetedTweetId;
        this.timestampMs = timestampMs;
    }



    private boolean isValidSimple(JsonObject jsonTweet){
        List<String> fields = Arrays.asList("lang" , "text", "timestamp_ms", "id", "user", "name", "id");
        for (String field: fields){
            if (!jsonTweet.has(field)){
                return false;
            }
        }
        return true;
    }


    /**
     * Returns a {@link ExtendedSimplifiedTweet} from a JSON String.
     * If parsing fails, for any reason, return an {@link Optional#empty()}
     *
     * @param jsonStr
     * @return an {@link Optional} of a {@link ExtendedSimplifiedTweet}
     */
    public static Optional<ExtendedSimplifiedTweet> fromJson(String jsonStr) {
        long tweetId;
        String text;
        long userId;
        String userName;
        String language;
        long timestampMs;
        boolean isRetweeted;
        Long retweetedUserId;
        Long retweetedTweetId;
        String retweetedUserName;
        Long followersCount;

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
                    tweet = new ExtendedSimplifiedTweet(tweetId, text, userId, userName, followersCount, language, isRetweeted, retweetedUserId, retweetedUserName, timestampMs, retweetedTweetId);
                }
            }
        }
        return Optional.ofNullable(tweet);


    }
}


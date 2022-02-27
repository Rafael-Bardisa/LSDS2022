package upf.edu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import scala.Serializable;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ExtendedSimplifiedTweet(long tweetId, String text, long userId, String userName,
                                   long followersCount, String language, boolean isRetweeted,
                                   Long retweetedUserId, long timestampMs, Long retweetedTweetId) {
        this.tweetId = tweetId;
        this.text = text;
        this.userId = userId;
        this.userName = userName;
        this.followersCount = followersCount;
        this.language = language;
        this.isRetweeted = isRetweeted;
        this.retweetedUserId = retweetedUserId;
        this.retweetedTweetId = retweetedTweetId;
        this.timestampMs = timestampMs;
    }


    /**
     * Checks if the given Json has all provided fields
     * @param json
     * @param conditions
     * @return
     */
    private static Boolean validJson(JsonObject json, String... conditions){
        return !Arrays.stream(conditions).map(json::has).collect(Collectors.toList()).contains(false);
    }


    /**
     * Returns a {@link ExtendedSimplifiedTweet} from a JSON String.
     * If parsing fails, for any reason, return a {@link Optional#empty()}
     * @param jsonStr
     * @return an {@link Optional} of a {@link ExtendedSimplifiedTweet}
     */
    public static Optional<ExtendedSimplifiedTweet> fromJson(String jsonStr) {

        boolean isRetweeted;//
        Long retweetedUserId = null;
        Long retweetedTweetId = null;

        ExtendedSimplifiedTweet tweet  = null;
        JsonElement je;

        try{
            je = JsonParser.parseString(jsonStr);
        }
        catch (JsonSyntaxException badJson){
            return Optional.empty();
        }
        if (je.isJsonNull()){return Optional.empty();}
        JsonObject jo = je.getAsJsonObject();
        if (validJson(jo, "id", "text", "timestamp_ms", "id", "user", "retweeted", "lang")){
            isRetweeted = validJson(jo, "retweeted_status");
            if (isRetweeted) {
                retweetedTweetId = jo.getAsJsonObject("retweeted_status").get("id").getAsLong();
                retweetedUserId = jo.getAsJsonObject("retweeted_status").getAsJsonObject("user").get("id").getAsLong();
            }
            JsonObject jUser = jo.getAsJsonObject("user");
            if (validJson(jUser, "name", "id", "followers_count")){
                tweet = new ExtendedSimplifiedTweet(
                            jo.get("id").getAsLong(),
                            jo.get("text").getAsString(),
                            jo.getAsJsonObject("user").get("id").getAsLong(),
                            jo.getAsJsonObject("user").get("name").getAsString(),
                            jo.getAsJsonObject("user").get("followersCount").getAsLong(),
                            jo.get("language").getAsString(),
                            isRetweeted,
                            retweetedUserId,
                            jo.get("timestampMs").getAsLong(),
                            retweetedTweetId);

            }

        }
        return Optional.ofNullable(tweet);
    }

    public String getText() {
        return text;
    }
}


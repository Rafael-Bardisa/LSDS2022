package upf.edu;

import java.util.*;
import java.util.stream.Collectors;

public class BiGram {
    private String first;
    private String last;

    public BiGram(String first, String last){
        this.first = first;
        this.last = last;
    }

    public static List<BiGram> fromExtendedTweet(ExtendedSimplifiedTweet tweet){
        List<BiGram> biGrams = new ArrayList<BiGram>();
        List<String> words = Arrays.stream(tweet.getText().split(" ")).map(BiGram::normalise).collect(Collectors.toList());
        Iterator<String> firstWords = words.iterator();
        Iterator<String> lastWords = words.subList(1, words.size()).iterator();
        for (String first = firstWords.next(), last = lastWords.next(); firstWords.hasNext() && lastWords.hasNext(); first = firstWords.next(), last = lastWords.next()){
            biGrams.add(new BiGram(first, last));
        }
        return biGrams;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last);
    }


    @Override
    public boolean equals(Object other){
        boolean result;
        if((other == null) || (getClass() != other.getClass())){
            return (false);
        }
        BiGram otherBiGram = (BiGram) other;
        return (this.first.equals(otherBiGram.first) &&  this.last.equals(otherBiGram.last));
    } // end equals

    @Override
    public String toString(){
        return ("[" + first + " " + last + "]");
    }

    private static String normalise(String word) {
        return word.trim().toLowerCase();
    }
}

package upf.edu;

import java.util.List;
import java.util.Objects;

public class BiGram {
    private String first;
    private String last;

    public BiGram(String first, String last){
        this.first = first;
        this.last = last;
    }

    public List<BiGram> fromExtendedTweet(ExtendedSimplifiedTweet tweet){

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

    private static String normalise(String word) {
        return word.trim().toLowerCase();
    }
}

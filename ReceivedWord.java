
import java.io.Serializable;

public class ReceivedWord implements Serializable {
    private String word;
    private int timestamp;

    public ReceivedWord(String word, int timestamp) {
        this.word = word;
        this.timestamp = timestamp;
    }

    public String getWord() {
        return word;
    }

    public int getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Word: " + word + ", Timestamp: " + timestamp;
    }
}

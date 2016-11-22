import java.util.*;
import java.lang.*;

public class Video {

    int videoLength;
    String legalMentions;
    int loanLength;
    double loanFare;

    public Video(int videoLength, String legalMentions) {
        this.videoLength = videoLength;
        this.legalMentions = legalMentions;
        this.loanLength = 14;
        this.loanFare = 1.5;
    }
}
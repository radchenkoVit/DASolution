package utils;

public interface Waiting {
    //seconds
    int NONE = 0;
    int SHORT = 2;
    int MIDDLE = 4;
    int LONG = 6;

    static interface Polling {
        // miliseconds
        int OFFTEN = 250;
        int RARE = 750;
    }

}

package gomoku.net.utils;

public class ForbiddenNames {

    private static final String[] fNames = {
        "[x]",
        "[X]",
        "[!]",
        "break",
        "OK",
        "END",
        "ERROR",
        "ok",
        "end",
        "error"
    };

    public static boolean isForbidden(String name) {
        for(String fName : fNames) {
            if(name.equals(fName)) {
                return true;
            }
        }
        return false;
    }
}
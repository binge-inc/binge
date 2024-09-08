package binge;

public class Build {
    private static final int VERSION_MAJOR = 1;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_PATCH = 0;

    public static String getFullVersionString() {
        return "v" + VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_PATCH;
    }
}

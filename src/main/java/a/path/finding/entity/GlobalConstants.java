package a.path.finding.entity;

public class GlobalConstants {

    public static int SIZE = 10;
    public static int TIME_INTERVAL = 1;
    public static int RESOLUTION_PENALTY = 20;
    public static int ORIGINAL_STEP_SIZE = 6;

    public static void updateSetup(Setup setup) {
        SIZE = setup.getBoardSize();
        TIME_INTERVAL = setup.getTimeInterval();
        RESOLUTION_PENALTY = setup.getResolutionPenalty();
        ORIGINAL_STEP_SIZE = setup.getOriginalStepSize();

    }
}

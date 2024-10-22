package a.path.finding.entity;

import a.path.finding.orientation.Orientation;

public class GlobalConstants {

    public static int SIZE = 20;
    public static int TIME_INTERVAL = 1;
    public static int RESOLUTION_PENALTY = 20;
    public static int ORIGINAL_STEP_SIZE = 6;
    public static Orientation START_ORIENTATION = Orientation.DOWN;

    public static void updateSetup(Setup setup) {
        SIZE = setup.getBoardSize();
        TIME_INTERVAL = setup.getTimeInterval();
        RESOLUTION_PENALTY = setup.getResolutionPenalty();
        ORIGINAL_STEP_SIZE = setup.getOriginalStepSize();
    }

    public static void updateTimeInterval(int value) {
        TIME_INTERVAL = value;
    }

    public static void updateResolutionPenalty(int value) {
        RESOLUTION_PENALTY = value;
    }
}

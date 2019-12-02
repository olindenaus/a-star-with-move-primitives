package a.path.finding;

public enum Orientation {

    UP(false),
    RIGHT(true),
    DOWN(false),
    LEFT(true);

    private boolean horizontal;

    private Orientation(boolean isHorizontal) {
        horizontal = isHorizontal;
    }

    public boolean isHorizontal() {
        return horizontal;
    }
}

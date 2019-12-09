package a.path.finding.entity;

import a.path.finding.Astar;

import java.io.Serializable;
import java.util.List;

public class Setup implements Serializable {

    private List<Node> borders;
    private Node start;
    private Node end;
    private int boardSize;
    private int resolutionPenalty;
    private int timeInterval;
    private int originalStepSize;

    @Override
    public String toString() {
        return borders + ";" +
                start + ";" +
                end + ";" +
                boardSize + ";" +
                resolutionPenalty + ";" +
                timeInterval + ";" +
                originalStepSize;
    }

    public String fileName() {
        return String.format("bSize-%s__resPenalty-%s__tInterval-%s__stepSize-%s", boardSize, resolutionPenalty, timeInterval, originalStepSize);
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getResolutionPenalty() {
        return resolutionPenalty;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public int getOriginalStepSize() {
        return originalStepSize;
    }

    public Node getStartNode() {
        return start;
    }

    public Node getEndNode() {
        return end;
    }

    public List<Node> getBorders() {
        return borders;
    }

    public Setup() {
        this.boardSize = 50;
        this.resolutionPenalty = 200;
        this.timeInterval = 300;
        this.originalStepSize = 6;
    }

    public Setup(Builder builder) {
        this.borders = builder.borders;
        this.start = builder.start;
        this.end = builder.end;
        this.boardSize = builder.boardSize;
        this.resolutionPenalty = builder.resolutionPenalty;
        this.timeInterval = builder.timeInterval;
        this.originalStepSize = builder.originalStepSize;
    }

    public Setup(Astar astar, Node s, Node e) {
        this();
        this.borders = astar.getObstacles();
        this.start = s;
        this.end = e;
    }

    private static class Builder {
        private List<Node> borders;
        private Node start;
        private Node end;
        private int boardSize;
        private int resolutionPenalty;
        private int timeInterval;
        private int originalStepSize;

        public Builder withBorders(List<Node> borders) {
            this.borders = borders;
            return this;
        }

        public Builder withStartNode(Node start) {
            this.start = start;
            return this;
        }

        public Builder withEnd(Node end) {
            this.end = end;
            return this;
        }

        public Builder withBoardSize(int boardSize) {
            this.boardSize = boardSize;
            return this;
        }

        public Builder withResolutionPenalty(int resolutionPenalty) {
            this.resolutionPenalty = resolutionPenalty;
            return this;
        }

        public Builder withTimeInterval(int timeInterval) {
            this.timeInterval = timeInterval;
            return this;
        }

        public Builder withStepSize(int stepSize) {
            this.originalStepSize = stepSize;
            return this;
        }

        public Setup build() {
            return new Setup(this);
        }
    }
}

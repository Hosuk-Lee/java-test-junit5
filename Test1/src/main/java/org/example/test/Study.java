package org.example.test;

public class Study {

    private StudyStatus status = StudyStatus.DRAFT;

    private int limit;

    private String name;

    public Study(){}

    public Study(int limit) {
        if ( limit < 0) {
            throw new IllegalArgumentException("0보다 커야 합니다.");
        }
        this.limit = limit;

    }

    public Study(int limit, String name) {
        this(limit);
        this.name = name;
    }

    public StudyStatus getStatus() {
        return status;
    }

    public void setStatus(StudyStatus status) {
        this.status = status;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {

        this.limit = limit;

    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limit +
                '}';
    }
}

// Logan MacGregor s4095198
package com.roadregistry;

import java.time.LocalDate;

public class DemeritRecord {
    private int points;
    private LocalDate date;

    public DemeritRecord(int points, LocalDate date) {
        this.points = points;
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return points + " points on " + date.toString();
    }
}

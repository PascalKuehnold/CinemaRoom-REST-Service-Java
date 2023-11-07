package cinema.model;

import cinema.builder.StatsBuilder;

public class Stats {
    private int income;
    private int available;
    private int purchased;

    public Stats(StatsBuilder statsBuilder) {
        this.income = statsBuilder.getIncome();
        this.available = statsBuilder.getAvailable();
        this.purchased = statsBuilder.getPurchased();
    }

    public static StatsBuilder builder() {
        return new StatsBuilder();
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }
}
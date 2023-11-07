package cinema.builder;

import cinema.model.Stats;

public class StatsBuilder {
    private int income;
    private int available;
    private int purchased;

    public StatsBuilder() {
    }

    public Stats build() {
        return new Stats(this);
    }

    public StatsBuilder withIncome(int income) {
        this.income = income;
        return this;
    }

    public StatsBuilder withAvailable(int available) {
        this.available = available;
        return this;
    }

    public StatsBuilder withPurchased(int purchased) {
        this.purchased = purchased;
        return this;
    }

    public int getIncome() {
        return income;
    }

    public int getAvailable() {
        return available;
    }

    public int getPurchased() {
        return purchased;
    }
}

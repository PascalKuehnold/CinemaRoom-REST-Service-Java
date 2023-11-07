package cinema.model;

import java.util.ArrayList;
import java.util.List;

public class MovieTheater {
    int rows;
    int columns;

    List<Seat> seats;

    public MovieTheater(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        initializeSeats();
    }

    private void initializeSeats() {
        seats = new ArrayList<>();

        for (int row = 1; row < this.rows + 1; row++) {
            for (int col = 1; col < this.columns + 1; col++) {
                seats.add(new Seat(row, col));
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }


    public List<Seat> getSeats() {
        return seats;
    }

}

package cinema.services;

import cinema.error.InvalidSeatException;
import cinema.error.PurchaseFailedException;
import cinema.model.MovieTheater;
import cinema.model.ReturnTicket;
import cinema.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CinemaServiceImpl implements CinemaService {
    private final MovieTheater movieTheater;

    private List<ReturnTicket> returnTickets;

    @Autowired
    public CinemaServiceImpl(MovieTheater movieTheater) {
        this.movieTheater = movieTheater;
        this.returnTickets = new ArrayList<>();
    }

    @Override
    public Optional<Seat> getSeat(int row, int col) {
        return movieTheater.getSeats().stream().filter(
                        seat1 -> seat1.getRow() == row && seat1.getColumn() == col)
                .findFirst();
    }

    @Override
    public ResponseEntity<ReturnTicket> purchaseSeat(Seat seat) {
        Optional<Seat> tempSeat = getSeat(seat.getRow(), seat.getColumn());

        if (tempSeat.isEmpty()) {
            throw new InvalidSeatException("The number of a row or a column is out of bounds!");
        }

        Seat presentSeat = tempSeat.get();
        if (presentSeat.isTaken()) {
            throw new PurchaseFailedException("The ticket has been already purchased!");
        }

        presentSeat.setTaken(true);

        UUID ticketId = UUID.randomUUID();

        ReturnTicket purchasedTicket = new ReturnTicket(ticketId, presentSeat);
        returnTickets.add(purchasedTicket);

        return ResponseEntity.status(200).body(purchasedTicket);
    }

    @Override
    public MovieTheater getFilteredMovieList() {
        this.movieTheater.setSeats(filterSeats());

        return this.movieTheater;
    }


    @Override
    public MovieTheater getMovieTheater() {
        return this.movieTheater;
    }

    @Override
    public ResponseEntity<Map<String, Seat>> refundTicket(UUID token) {
        for (ReturnTicket returnTicket : returnTickets) {
            if (returnTicket.getToken().equals(token)) {
                Seat tempSeat = returnTicket.getTicket();
                tempSeat.setTaken(false);

                returnTickets.remove(returnTicket);

                return ResponseEntity.status(200).body(Map.of("ticket", tempSeat));
            }
        }

        throw new InvalidSeatException("Wrong token!");
    }

    private List<Seat> filterSeats() {
        return this.movieTheater.getSeats().stream()
                .filter(seat -> !seat.isTaken())
                .collect(Collectors.toList());
    }
}

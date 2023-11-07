package cinema.services;

import cinema.model.MovieTheater;
import cinema.model.ReturnTicket;
import cinema.model.Seat;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CinemaService {
    Optional<Seat> getSeat(int row, int col);
    ResponseEntity<ReturnTicket> purchaseSeat(Seat seat);

    MovieTheater getFilteredMovieList();

    MovieTheater getMovieTheater();

    ResponseEntity<Map<String, Seat>> refundTicket(UUID token);
}

package cinema.controllers;

import cinema.error.InvalidSeatException;
import cinema.error.CustomCinemaError;
import cinema.error.PurchaseFailedException;
import cinema.model.MovieTheater;
import cinema.model.ReturnTicket;
import cinema.model.Seat;
import cinema.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.UUID;

@RestController
public class SeatsController {
    private final CinemaService cinemaService;

    @Autowired
    public SeatsController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/seats")
    public MovieTheater getTheatre() {
        return cinemaService.getFilteredMovieList();
    }

    @PostMapping("/purchase")
    public ResponseEntity<ReturnTicket> purchaseSeat(@RequestBody Seat seat) {
        return cinemaService.purchaseSeat(seat);
    }

    @PostMapping("/return")
    public ResponseEntity<Map<String, Seat>> refundTicket(@RequestBody String token){
        String[] tokenArray = token.split(":");
        UUID uuid = UUID.fromString(tokenArray[1].substring(1,37));

        return cinemaService.refundTicket(uuid);
    }



    @ExceptionHandler(InvalidSeatException.class)
    public ResponseEntity<CustomCinemaError> handleInvalidSeat(InvalidSeatException e, WebRequest request){
        CustomCinemaError body = new CustomCinemaError(e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PurchaseFailedException.class)
    public ResponseEntity<CustomCinemaError> handlePurchaseError(PurchaseFailedException e, WebRequest request){
        CustomCinemaError body = new CustomCinemaError(e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}

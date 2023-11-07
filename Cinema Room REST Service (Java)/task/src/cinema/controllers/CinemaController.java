package cinema.controllers;

import cinema.builder.StatsBuilder;
import cinema.error.CinemaException;
import cinema.error.CustomCinemaError;
import cinema.model.Seat;
import cinema.model.Stats;
import cinema.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@RestController
public class CinemaController {
    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/stats")
    public Stats getCinemaStats(@RequestParam Optional<String> password) {
        if(password.isEmpty()) {
            throw new CinemaException("The password is wrong!");
        }

        if (!password.get().equals("super_secret")) {
            throw new CinemaException("The password is wrong!");
        }

        int income = cinemaService.getMovieTheater()
                .getSeats()
                .stream().filter(Seat::isTaken)
                .mapToInt(Seat::getPrice).sum();

        int available = (int) cinemaService.getMovieTheater()
                .getSeats()
                .stream().filter(seat -> !seat.isTaken())
                .count();

        int purchased = (int) cinemaService.getMovieTheater()
                .getSeats()
                .stream().filter(Seat::isTaken)
                .count();

        return new StatsBuilder()
                .withIncome(income)
                .withAvailable(available)
                .withPurchased(purchased)
                .build();
    }

    @ExceptionHandler(CinemaException.class)
    public ResponseEntity<CustomCinemaError> handleUnauthorizedError(CinemaException e, WebRequest request) {
        CustomCinemaError body = new CustomCinemaError(e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
}

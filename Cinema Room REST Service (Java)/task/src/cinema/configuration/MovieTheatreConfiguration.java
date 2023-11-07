package cinema.configuration;

import cinema.model.MovieTheater;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieTheatreConfiguration {

    @Bean
    public MovieTheater movieTheater(){
        return new MovieTheater(9, 9);
    }

}

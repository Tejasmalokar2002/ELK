package com.RatingService.RatingService.services;

import ch.qos.logback.classic.pattern.RelativeTimeConverter;
import com.RatingService.RatingService.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    Rating create(Rating rating);

    List<Rating> getRating();

    List<Rating> getRatingByUserId(String userId);

    List<Rating> getRatingByHotelId(String hotelId);
}

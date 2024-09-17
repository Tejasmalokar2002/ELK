package com.UserService.UserService.services.impl;


import com.UserService.UserService.entities.Hotel;
import com.UserService.UserService.entities.Rating;
import com.UserService.UserService.entities.User;
import com.UserService.UserService.exceptions.ResourceNotFoundException;
import com.UserService.UserService.external.services.HotelService;
import com.UserService.UserService.repository.UserRepository;
import com.UserService.UserService.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HotelService hotelService;


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

//    @Override
//    public User getUser(String userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server " + userId));

//        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
//
//        ArrayList forObject = restTemplate.getForObject("http://localhost:9092/ratings/users/"+user.getUserId(), ArrayList.class);
//        logger.info("Fetched data: {}", forObject);
//
//        List<Rating> ratingList = (List<Rating>) forObject.stream().map(rating -> {
//            restTemplate.getForEntity("http://localhost:9091/hotels/aefa7ac2-c951-4610-96ac-8e0ba707a83d", Hotel.class);
//            com.UserService.UserService.entities.Hotel hotel = forEntity.getBody();
//
//            rating.setHotel(hotel);
//            return rating;
//        }).collect(Collectors.toList());
//        user.setRatings(ratingList);

//        logger.info("{} ", ratingsOfUser);
//        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
//        List<Rating> ratingList = ratings.stream().map(rating -> {
//            //api call to hotel service to get the hotel
//            //http://localhost:8082/hotels/1cbaf36d-0b28-4173-b5ea-f1cb0bc0a791
//            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//            // logger.info("response status code: {} ",forEntity.getStatusCode());
//            //set the hotel to rating
//            rating.setHotel(hotel);
//            //return the rating
//            return rating;
//        }).collect(Collectors.toList());
//
//        user.setRatings(ratingList);

//        return user;
//    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server " + userId));

        // Fetch ratings for the user from the Rating service
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/" + user.getUserId(), Rating[].class);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        // Convert the array to a list
        List<Rating> ratingList = Arrays.asList(ratingsOfUser);

        // Map each rating to a hotel and update the rating
        ratingList = ratings.stream().map(rating -> {
            // Fetch the hotel details
            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVCIE/hotels/" + rating.getHotelId(), Hotel.class);

            // Extract the hotel from the response
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

            if (hotel != null) {
                rating.setHotel(hotel);
            }

            return rating;
        }).collect(Collectors.toList());

        // Set the ratings in the user object
        user.setRatings(ratingList);

        return user;
    }

}

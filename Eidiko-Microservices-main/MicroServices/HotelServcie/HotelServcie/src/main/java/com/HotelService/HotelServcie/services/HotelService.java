package com.HotelService.HotelServcie.services;

import com.HotelService.HotelServcie.entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel create(Hotel hotel);

    List<Hotel> getALl();

    Hotel get(String id);
}

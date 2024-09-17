package com.HotelService.HotelServcie.repositories;

import com.HotelService.HotelServcie.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, String> {
}

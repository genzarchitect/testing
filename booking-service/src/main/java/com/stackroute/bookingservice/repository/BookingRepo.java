package com.stackroute.bookingservice.repository;

import com.stackroute.bookingservice.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepo extends MongoRepository<Booking,Integer> {
    @Query("Select b from Booking b where b.playerEmail= : playerEmail")
    List<Booking> findByEmail(String playerEmail);
    @Query("Select s from Booking s where s.groundOwnerEmail")
    List<Booking> findByOEmail(String groundOwnerEmail);

    Booking findByBookingId(String id);


}

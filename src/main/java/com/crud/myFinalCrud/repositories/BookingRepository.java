package com.crud.myFinalCrud.repositories;

import com.crud.myFinalCrud.model.Booking;
import com.crud.myFinalCrud.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {


    Optional<Booking> findByOrderNumber(int orderNumber);

}

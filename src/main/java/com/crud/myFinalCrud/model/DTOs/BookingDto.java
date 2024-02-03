package com.crud.myFinalCrud.model.DTOs;

import com.crud.myFinalCrud.model.Booking;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;



@Component
@Data
public class BookingDto {

    private Long id;
    private int orderNumber;
    private LocalDate orderDate;
    private String orderDescription;
    private int sum;
    private ClientDto client;

    public BookingDto() {
    }

    public BookingDto(Booking booking) {
        this.id = booking.getId();
        this.orderNumber = booking.getOrderNumber();
        this.orderDate = booking.getOrderDate();
        this.orderDescription = booking.getOrderDescription();
        this.sum = booking.getSum();
        this.client = new ClientDto(booking.getClient());
    }
}

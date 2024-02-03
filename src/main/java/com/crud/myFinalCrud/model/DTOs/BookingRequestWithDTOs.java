package com.crud.myFinalCrud.model.DTOs;

import com.crud.myFinalCrud.model.Booking;
import com.crud.myFinalCrud.model.Client;
import lombok.Data;
import org.mapstruct.Mapper;


@Data
public class BookingRequestWithDTOs {


    private BookingDto bookingDto;
    private ClientDto clientDto;


}





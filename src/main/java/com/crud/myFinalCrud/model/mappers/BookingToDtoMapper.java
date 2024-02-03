package com.crud.myFinalCrud.model.mappers;


import com.crud.myFinalCrud.model.Booking;
import com.crud.myFinalCrud.model.Client;
import com.crud.myFinalCrud.model.DTOs.BookingDto;
import com.crud.myFinalCrud.model.DTOs.ClientDto;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@Mapper(componentModel = "spring")
public interface BookingToDtoMapper {

    BookingDto toDto(Booking booking);
    Booking fromDto(BookingDto bookingDto);

    ClientDto toDto(Client client);
    Client fromDto(ClientDto clientDto);


}

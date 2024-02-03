package com.crud.myFinalCrud.service;
import com.crud.myFinalCrud.exceptions.ErrorMesageEnum;
import com.crud.myFinalCrud.exceptions.NoSuchClientException;
import com.crud.myFinalCrud.exceptions.NoSuchOrderException;
import com.crud.myFinalCrud.model.Booking;
import com.crud.myFinalCrud.model.Client;
import com.crud.myFinalCrud.model.DTOs.BookingDto;
import com.crud.myFinalCrud.model.DTOs.ClientDto;
import com.crud.myFinalCrud.model.mappers.BookingToDtoMapper;
import com.crud.myFinalCrud.model.mappers.ClientAddressToDtoMapper;
import com.crud.myFinalCrud.model.mappers.ClientToDtoMapper;
import com.crud.myFinalCrud.repositories.BookingRepository;
import com.crud.myFinalCrud.repositories.ClientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;


@Component
public class Service {

    private final ClientRepository clientRepository;
    private final BookingRepository bookingRepository;
    private final ClientAddressToDtoMapper clientAddressToDtoMapper;
    private final ClientToDtoMapper clientToDtoMapper;
    private final BookingToDtoMapper bookingToDtoMapper;


    @Autowired
    public Service(ClientRepository clientRepository, BookingRepository bookingRepository,
                   ClientAddressToDtoMapper clientAddressToDtoMapper
            , ClientToDtoMapper clientToDtoAndReverse, BookingToDtoMapper bookingToDtoMapper) {
        this.clientRepository = clientRepository;
        this.bookingRepository = bookingRepository;
        this.clientAddressToDtoMapper = clientAddressToDtoMapper;
        this.clientToDtoMapper = clientToDtoAndReverse;
        this.bookingToDtoMapper = bookingToDtoMapper;
    }

    public Client convertClientDtoToClientAndAddClientToDb(ClientDto clientDto) {
        Client client = clientToDtoMapper.fromDto(clientDto);
        clientRepository.save(client);
        return client;
}





    public Client addClientToDb(Client client) {
        clientRepository.save(client);
        return client;
    }


    public Client getClientByINN(long INN) {
        Client client = clientRepository.findByClientINN(INN)
                .orElseThrow(() ->
                        new NoSuchClientException(ErrorMesageEnum.CLIENT_NOT_FOUND));
        return client;
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public List<Booking> getAllBookingsByClient(String clientName, long clientINN) {
        Client client = getClientByINN(clientINN);
        if (client.getClientINN() == clientINN) {
            return client.getOrders();
        }
        return null;
    }



    public void addBooking(BookingDto bookingDto, ClientDto clientDto) {

        //1.Кинули какого -то clinetDto
        //2.Проверяем, ищем по полям этого DTO такого реального клиента


        Client existingClient = clientRepository.findAll().stream()
                .filter(c -> c.getClientName().equals(clientDto.getClientName())
                        && c.getClientINN() == (clientDto.getClientINN()))
                .findFirst()
                .orElse(null);

        //3.Если такое CLinet имеется, то

        if (existingClient != null) { // елси такой клиент уже есть
            Booking booking = bookingToDtoMapper.fromDto(bookingDto);//делаем из Дто букинга нормальный букинг
            booking.setClient(existingClient); // назначаем его форен ключу в букинге
            bookingRepository.save(booking); // сохранили букинг за клиентом который уже в списках

            //4.Если такого клиента в базе нет, то конвертм его и букинг из ДТО и кладем в базку тоже

        } else {
            Client client = clientToDtoMapper.fromDto(clientDto);
            Booking booking = bookingToDtoMapper.fromDto(bookingDto);
            clientRepository.save(client);
            booking.setClient(client);
            bookingRepository.save(booking);
        }
    }




    public Client removeClient(Client client) { //удаляет клиента и все его данные из других таблиц
        clientRepository.delete(client);
        return null;
    }

    public void deleteBooking(Booking booking) { //удаляет 1 букинг но не клинета
        bookingRepository.delete(booking);
    }

    public Booking getOneBooking(int orderNumber) {
        Booking bookingFound = bookingRepository.findByOrderNumber(orderNumber).orElseThrow(() ->
                new NoSuchOrderException(ErrorMesageEnum.ORDER_NOT_FOUND));
        return bookingFound;
    }

    public void updateBooking(Booking booking) {

        bookingRepository.save(booking);
    }
}















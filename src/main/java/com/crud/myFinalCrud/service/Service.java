package com.crud.myFinalCrud.service;


import com.crud.myFinalCrud.exceptions.ErrorMesageEnum;
import com.crud.myFinalCrud.exceptions.NoSuchClientException;
import com.crud.myFinalCrud.exceptions.NoSuchOrderException;
import com.crud.myFinalCrud.model.Booking;
import com.crud.myFinalCrud.model.Client;
import com.crud.myFinalCrud.repositories.BookingRepository;
import com.crud.myFinalCrud.repositories.ClientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class Service {

    private final ClientRepository clientRepository;
    private final BookingRepository bookingRepository;


    @Autowired
    public Service(ClientRepository clientRepository, BookingRepository bookingRepository) {
        this.clientRepository = clientRepository;
        this.bookingRepository = bookingRepository;
    }


    public void addClientService(Client client) {
        clientRepository.save(client); // не надо отдельно писать метод в PersonRepository
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

    public List<Booking>getAllBookingsByClient(String clientName, long clientINN){
        Client client  = getClientByINN(clientINN);
        if(client.getClientINN() == clientINN){
            return client.getOrders();
        }
        return null;
    }

    public void addBooking(Booking booking, Client client) {

        Client existingClient = clientRepository.findAll().stream()
                .filter(c -> c.getClientName().equals(client.getClientName())
                        && c.getClientINN() == (client.getClientINN()))
                .findFirst()
                .orElse(null);

        if (existingClient != null) { // елси такой клиент уже есть
            booking.setClient(existingClient); // назначаем его форен ключу в букинге
            bookingRepository.save(booking); // сохранили букинг за клиентом который уже в списках
        } else {
            clientRepository.save(client);
            booking.setClient(client);
            bookingRepository.save(booking);
        }
    }

    public void removeClient(Client client) { //удаляет клиента и все его данные из других таблиц
        clientRepository.delete(client);
    }

    public void deleteBooking(Booking booking){ //удаляет 1 букинг но не клинета
        bookingRepository.delete(booking);
    }

    public Booking getOneBooking(int orderNumber) {
        Booking bookingFound = bookingRepository.findByOrderNumber(orderNumber).orElseThrow(() ->
                new NoSuchOrderException(ErrorMesageEnum.ORDER_NOT_FOUND));
        return bookingFound;
    }

    public void updateBooking(Booking booking){
        bookingRepository.save(booking);
    }

}













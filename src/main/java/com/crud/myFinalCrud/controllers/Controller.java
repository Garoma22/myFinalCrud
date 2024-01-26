package com.crud.myFinalCrud.controllers;


import com.crud.myFinalCrud.model.Booking;
import com.crud.myFinalCrud.model.BookingRequest;
import com.crud.myFinalCrud.model.Client;
import com.crud.myFinalCrud.repositories.ClientRepository;
import com.crud.myFinalCrud.service.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class Controller {

    private Service service;

    @Autowired
    public Controller(Service service) {
        this.service = service;
    }

    @PostMapping("/add-new-client")
    public void addNewClient(@RequestBody Client client) {
        service.addClientService(client);
    }

    @GetMapping("/get-client-by-inn")
    public Client getClient(@RequestParam long clientINN) {
        Client client = service.getClientByINN(clientINN);
        return client; // return это идет клиентской части в респонс
    }

    @PatchMapping("/update-client-name")
    public void updateClientName(@RequestParam Long clientINN, @RequestParam String newName
    ) {
        Client c = getClient(clientINN); //подцепили клиента по ИНН
        // у этого клиента нам надо заменить что-то или все.
        c.setClientName(newName);
        service.addClientService(c);
    }

    @GetMapping("/get-all-clients")
    public List<Client> allClients() {
        return service.getAll();
    }

    /*
    Тут интересный момент, нельзя засунуть в параметры 2 request body,
    поэтому делатся класс-обертка, который содержит в себе
    2 нужных нам объекта.
     */
    @PostMapping("/add-booking")
    public void addOrder(@RequestBody BookingRequest bookingRequest) {
        service.addBooking(bookingRequest.getBooking(), bookingRequest.getClient());
    }
    @DeleteMapping("/remove-client-fully") //удаляем всю информацию по клиенту из базы
    public void removeClientFullData(@RequestParam Long clientINN) {

        Client client = service.getClientByINN(clientINN);

        if (client != null) {
            // Удаление клиента и связанных данных (каскадное удаление)
            service.removeClient(client);
        }
    }
    @GetMapping("/get-single-order") //выдаем единичный заказ клинета (по номеру)
    public Booking getSingleOrderByNum(@RequestParam int orderNumber ){

        Booking booking = service.getOneBooking(orderNumber);
        return booking;
    }
    @PatchMapping("update-order")
    public void updateOrder(@RequestBody Booking newBooking,
                            @RequestParam int orderNumber){

        Booking toUpdate = service.getOneBooking(orderNumber);
        toUpdate.setSum(newBooking.getSum());
        toUpdate.setOrderNumber(newBooking.getOrderNumber());
        toUpdate.setOrderDate(newBooking.getOrderDate());
        toUpdate.setOrderDescription(newBooking.getOrderDescription());
        service.updateBooking(toUpdate);
    }
    @GetMapping("/get-all-clients-bookings")
    public List<Booking> orders(@RequestParam String clientName, @RequestParam long clientINN){
        return service.getAllBookingsByClient(clientName,clientINN);
    }
    @DeleteMapping("/remove-one-client's-booking")
    public void deleteOneBooking(@RequestParam int orderNumber){
        Booking booking = service.getOneBooking(orderNumber);
        service.deleteBooking(booking);
    }
}



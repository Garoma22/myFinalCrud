package com.crud.myFinalCrud.controllers;

import com.crud.myFinalCrud.model.Booking;
import com.crud.myFinalCrud.model.BookingRequest;
import com.crud.myFinalCrud.model.Client;
import com.crud.myFinalCrud.model.DTOs.BookingDto;
import com.crud.myFinalCrud.model.DTOs.BookingRequestWithDTOs;
import com.crud.myFinalCrud.model.DTOs.ClientDto;
import com.crud.myFinalCrud.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class Controller {

    private Service service;

    @Autowired
    public Controller(Service service) {
        this.service = service;
    }



    //Принцип конвертации DTO: кидаем в сервис clientDto, а получаем назад
    // "чистого" клиента сохраненного в базе.
    @PostMapping("/add-new-client")
    public Client addNewClient(@RequestBody ClientDto clientDto) {
        return service.convertClientDtoToClientAndAddClientToDb(clientDto); //возвращается Client
    }


    //Тут просто получаем клиента из базы. Что есть то и получаем, никаких DTO
    @GetMapping("/get-client-by-inn")
    public Client getClient(@RequestParam long clientINN) {
        Client client = service.getClientByINN(clientINN);
        return client; // return это идет клиентской части в респонс
    }

    @PatchMapping("/update-client-name")
    public Client updateClientName(@RequestParam Long clientINN, @RequestParam String newName
    ) {
        Client client= getClient(clientINN); //подцепили клиента по ИНН
        // у этого клиента нам надо заменить имя.
        client.setClientName(newName);
        service.addClientToDb(client);
        return client;
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

    //охватываем тут все слои сразу

    //ВАЖНО! В этос способе, когда распрсиваем json на 2 обхекта, у джейсона есть заголовки.
    // И вот эти заоловки должны иметь такое же название как и поля в классе BookingRequestWithDTOs
    @PostMapping("/add-booking")
    public void addOrder(@RequestBody BookingRequestWithDTOs bookingRequesTwoDtos) {
        service.addBooking(bookingRequesTwoDtos.getBookingDto(), bookingRequesTwoDtos.getClientDto());
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
    public void updateOrder(@RequestBody BookingDto newBookingDto,
                            @RequestParam int orderNumber){

        Booking toUpdate = service.getOneBooking(orderNumber);
        toUpdate.setSum(newBookingDto.getSum());
//        toUpdate.setOrderNumber(newBookingDto.getOrderNumber()); // не стоит давать возможность менять
        toUpdate.setOrderDate(newBookingDto.getOrderDate());
        toUpdate.setOrderDescription(newBookingDto.getOrderDescription());
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



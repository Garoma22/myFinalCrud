package com.crud.myFinalCrud.model;


import lombok.Data;



//поскольку нельзя запихать в параметры метода два реквест бади, то делается вот такой
//класс обертка и уже его общий джейосн пихаем в метод addOrder

@Data
public class BookingRequest {
    private Booking booking;
    private Client client;

}
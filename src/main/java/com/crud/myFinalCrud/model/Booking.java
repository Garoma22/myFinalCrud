


package com.crud.myFinalCrud.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import org.springframework.stereotype.Component;


import java.time.LocalDate;


@Component
@Entity
@Table(name = "booking")
@Data
public class Booking {


//    @Transient //анотация говорит что поле не должно мапаться
//    private boolean isNewClient;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number")
    private int orderNumber;

    @Column(name = "order_date")
    private LocalDate orderDate;


    @Column(name = "order_description")
    private String orderDescription;

    @Column(name = "sum")
    private int sum;

    @ManyToOne(cascade = CascadeType.PERSIST)
    // Каскадное сохранение, помогло сохранить нового клиента при формирвоании нового заказа
    @JoinColumn(name = "client_id")
    @JsonBackReference // Обозначает владельца отношения (в данном случае - Booking)
    private Client client;

    public Booking() {
    }

    public Booking(Long id, int number, LocalDate orderDate, String description, int sum, Client client) {
        this.id = id;
        this.orderNumber = number;
        this.orderDate = orderDate;
        this.orderDescription = description;
        this.sum = sum;
        this.client = client;
    }

//    // Когда речь идет о связи  @ManyToOne то foreign key всегда находится в таблице, которая отвечает за many
//    // и hibernate понимает что department_id находится в таблице person, и далее hibernate понимает
//    // на что ссылается foreign_key, на какой primary key в таблице departmant ссылается этот ключ внешний
//    @ManyToOne() //при удалении сотрудника из депаратамента, департаменту ничего не грозит.
//    @JoinColumn(name= "department_id")  // надо создать в таблице такую колонку
//    private Department department;

}


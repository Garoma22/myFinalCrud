package com.crud.myFinalCrud.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;

import java.util.List;



@Entity
@Table(name = "client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_inn")
    private long clientINN;

    @Column(name = "client_phone")
    private long clientPhone;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_address_id", referencedColumnName = "id")
    //на каждом объекте депаратмента своя вспомогательная таблица
    private ClientAddress clientAddressData;                   //вот она, это поле в данном классе.

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL) // указывает, что связь управляется полем client в классе Order.
//    @JoinColumn(name = "client_id")
    // Это означает, что столбец, используемый для соединения, находится в классе Order,
    // и Hibernate не должен создавать дополнительную внешнюю таблицу для управления связью.
    @JsonManagedReference // Обозначает управляемую сторону отношения (в данном случае - Client)
    private List<Booking> orders;


    public Client() {
    }

    public Client(long id, String clientName, long clientINN, long clientPhone, ClientAddress clientAddressData, List<Booking> orders) {
        this.id = id;
        this.clientName = clientName;
        this.clientINN = clientINN;
        this.clientPhone = clientPhone;
        this.clientAddressData = clientAddressData;
        this.orders = orders;
    }


}





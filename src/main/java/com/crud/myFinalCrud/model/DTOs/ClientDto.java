package com.crud.myFinalCrud.model.DTOs;


import com.crud.myFinalCrud.model.Client;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class ClientDto {

    private Long id;
    private String clientName;
    private long clientINN;
    private long clientPhone;
    private ClientAddressDTO clientAddressData;
    private List<BookingDto> orders;

    public ClientDto() {
    }

    public ClientDto(Client client) {
        this.id = client.getId();
        this.clientName = client.getClientName();
        this.clientINN = client.getClientINN();
        this.clientPhone = client.getClientPhone();
        this.clientAddressData = new ClientAddressDTO(client.getClientAddressData());
        this.orders = client.getOrders().stream()
                .map(BookingDto::new)
                .toList();
    }
}

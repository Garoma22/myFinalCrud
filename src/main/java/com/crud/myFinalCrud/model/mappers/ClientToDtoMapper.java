package com.crud.myFinalCrud.model.mappers;

import com.crud.myFinalCrud.model.Client;
import com.crud.myFinalCrud.model.DTOs.ClientDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = ClientAddressToDtoMapper.class)
public interface ClientToDtoMapper {

    ClientDto toDto(Client client);
    Client fromDto(ClientDto clientDto);


    //поскольку у меня одно из полей Client является классом ClientAddress
    // мне надо в этом маппинге прописать и то, как он будет конвертиться.
    // то же самое для класса Booking делаем, поскольку там тоже ссылка на Client есть одним из полей


    //Вместо этого пищем uses = ClientAddressToDtoMapper.class
//   ClientAddressDTO toClientAddressDTO(ClientAddress clientAddress);
//    ClientAddress fromClientAddressDto(ClientAddressDTO clientAddressDTO);



}
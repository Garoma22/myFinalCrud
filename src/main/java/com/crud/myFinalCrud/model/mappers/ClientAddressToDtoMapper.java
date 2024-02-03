package com.crud.myFinalCrud.model.mappers;


import com.crud.myFinalCrud.model.ClientAddress;
import com.crud.myFinalCrud.model.DTOs.ClientAddressDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ClientAddressToDtoMapper {

    ClientAddressDTO toDTO(ClientAddress clientAddress);
    ClientAddress fromDTO(ClientAddressDTO clientAddressDTO);



}

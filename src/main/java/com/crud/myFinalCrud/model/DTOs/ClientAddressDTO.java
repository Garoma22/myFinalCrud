package com.crud.myFinalCrud.model.DTOs;

import com.crud.myFinalCrud.model.ClientAddress;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
public class ClientAddressDTO {

    private Long id;
    private String regionId;
    private String regionName;
    private String cityName;
    private String streetName;
    private long buildingNum;
    private long corpusNum;
    private long officeNum;

    public ClientAddressDTO() {
    }

    public ClientAddressDTO(ClientAddress clientAddress) {
        this.id = clientAddress.getId();
        this.regionId = clientAddress.getRegionId();
        this.regionName = clientAddress.getRegionName();
        this.cityName = clientAddress.getCityName();
        this.streetName = clientAddress.getStreetName();
        this.buildingNum = clientAddress.getBuildingNum();
        this.corpusNum = clientAddress.getCorpusNum();
        this.officeNum = clientAddress.getOfficeNum();
    }
}

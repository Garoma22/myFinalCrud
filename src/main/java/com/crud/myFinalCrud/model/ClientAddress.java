package com.crud.myFinalCrud.model;


import jakarta.persistence.Entity;
import lombok.Data;


import jakarta.persistence.*;


@Entity
@Table(name = "client_address")
@Data
public class ClientAddress {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "region_id")
    private String regionId;
    @Column(name = "region_name")
    private String regionName;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "building_num")
    private long buildingNum;
    @Column(name = "corpus_num")
    private long corpusNum;
    @Column(name = "office_num")
    private long officeNum;


    // эти 2 конструктора  нужны

    public ClientAddress() {
    } // Пустой конструктор нужен для десериализации JSON

    // Kонструктор с параметрами нужен для создания объекта из JSON

    public ClientAddress(Long id, String regionId, String regionName, String cityName, String streetName, long buildingNum, long corpusNum, long officeNum) {
        this.id = id;
        this.regionId = regionId;
        this.regionName = regionName;
        this.cityName = cityName;
        this.streetName = streetName;
        this.buildingNum = buildingNum;
        this.corpusNum = corpusNum;
        this.officeNum = officeNum;
    }
}

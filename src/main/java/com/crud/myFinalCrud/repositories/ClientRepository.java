package com.crud.myFinalCrud.repositories;

import com.crud.myFinalCrud.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientINN(long clientINN);


    Optional<Client> findByClientName(String clientName);






}


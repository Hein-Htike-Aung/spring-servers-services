package com.example.serverservice.repo;

import com.example.serverservice.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepo extends JpaRepository<Server, Long> {

    Server findByIpAddress(String ipAddress);

    Server findByName(String name);
}

package com.example.serverservice.service.implementation;

import com.example.serverservice.enumeration.Status;
import com.example.serverservice.model.Server;
import com.example.serverservice.repo.ServerRepo;
import com.example.serverservice.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.example.serverservice.enumeration.Status.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new Server: {}", server.getName());

        server.setImageUrl(setServerImageUrl());
        return this.serverRepo.save(server);
    }

    private String setServerImageUrl() {

        String[] imagesNames = {"server-1.png", "server-2.png", "server-3.png", "server-4.png"};

        return ServletUriComponentsBuilder
                // Go To Controller
                .fromCurrentContextPath().path("/server/image/" + imagesNames[new Random().nextInt(4)])
                .toUriString();
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging Server IP: {}", ipAddress);

        Server server = this.serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        /* Change Server Status */
        server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
        this.serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> serverList(int limit) {
        log.info("Fetching All Servers");

        return this.serverRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server By Id: {}", id);

        return this.serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating new Server: {}", server.getName());

        return this.serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting Server: {}", id);

        this.serverRepo.deleteById(id);

        return Boolean.TRUE;
    }

}

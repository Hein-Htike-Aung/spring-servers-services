package com.example.serverservice.service;

import com.example.serverservice.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {

    Server create(Server server);

    Server ping(String ipAddress) throws IOException;

    Collection<Server> serverList(int limit);

    Server get(Long id);

    Server update(Server server);

    Boolean delete(Long id);

}

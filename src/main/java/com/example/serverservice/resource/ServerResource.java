package com.example.serverservice.resource;

import com.example.serverservice.enumeration.Status;
import com.example.serverservice.model.Response;
import com.example.serverservice.model.Server;
import com.example.serverservice.service.ServerService;
import com.example.serverservice.service.implementation.ServerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.springframework.util.MimeTypeUtils.IMAGE_PNG_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/server")
public class ServerResource {

    private final ServerServiceImpl serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("servers", this.serverService.serverList(30)))
                        .message("Servers retrieved")
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(
            @PathVariable("ipAddress") String ipAddress
    ) throws IOException {
        Server server = this.serverService.ping(ipAddress);

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping Success" : "Ping Failed")
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(
            @RequestBody @Valid Server server
    ) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", this.serverService.create(server)))
                        .message("Server Created")
                        .httpStatus(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(
            @PathVariable("id") Long id
    ) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", this.serverService.get(id)))
                        .message("Server Retrieved")
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(
            @PathVariable("id") Long id
    ) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("deleted", this.serverService.delete(id)))
                        .message("Server Deleted")
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    /* Return PNG */
    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(
            @PathVariable("fileName") String fileName
    ) throws IOException {
        
        return Files.readAllBytes(
                Paths.get("D:\\spring\\spring-learning\\server-service\\src\\main\\resources\\images\\" + fileName)
        );
    }
}

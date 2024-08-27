package com.visionary.crud_cliente.controller;

import com.visionary.crud_cliente.model.Client;
import com.visionary.crud_cliente.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> obtenerTodosLosClientes(){
        return clientService.obtenerTodosLosClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> obtenerClientById(@PathVariable Long id){
        Optional<Client> client = clientService.obtenerClientById(id);
        return client.map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public Client crearClient(@RequestBody Client client){
        return clientService.guardarClient(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> actualizarClient(@PathVariable Long id, @RequestBody Client clientDetalles){
        Optional<Client> client = clientService.obtenerClientById(id);

        if (client.isPresent()) {
            Client clientExistent = client.get();
            clientExistent.setName(clientDetalles.getName());
            clientExistent.setMail(clientDetalles.getMail());
            return ResponseEntity.ok(clientService.guardarClient(clientExistent));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elimarClient(@PathVariable Long id){
        clientService.eliminarClient(id);
        return ResponseEntity.noContent().build();
    }
}

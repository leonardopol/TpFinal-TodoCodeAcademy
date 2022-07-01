package com.Pierro_Leonardo.TpFinal_Bazar.controller;

import com.Pierro_Leonardo.TpFinal_Bazar.model.Cliente;
import com.Pierro_Leonardo.TpFinal_Bazar.service.IClienteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    
    @Autowired
    private IClienteService clienteServ;

    /*------------- 2 CRUD COMPLETO DE CLIENTES----------------*/
    //Creacion
    @PostMapping("/clientes/crear")
    public String saveCliente(@RequestBody Cliente clien) {
        clienteServ.saveCliente(clien);

        return "El cliente fue creado correctamente";
    }
    //Lista completa de clientes
    @GetMapping("/clientes")
    public List<Cliente> traerClientes() {
        return clienteServ.getClientes();
    }
    //traer cliente en particular
    @GetMapping("/clientes/{id_cliente}")
    public Cliente traerCliente(@PathVariable Long id_cliente){
        return clienteServ.findCliente(id_cliente);
    }
    //Eliminacion
    @DeleteMapping("/clientes/eliminar/{id_cliente}")
    public String deleteCliente(@PathVariable Long id_cliente) {
        clienteServ.deleteCliente(id_cliente);
        return "El cliente fue eliminado correctamente";
    }
    //Edicion por codigo
    @PutMapping("/clientes/editar/{id_cliente}")
    public Cliente editCliente(@PathVariable Long id_cliente,
            @RequestParam(required = false, name = "id_nueva") Long codigo_nuevo,
            @RequestParam(required = false, name = "nombre") String nuevo_nombre,
            @RequestParam(required = false, name = "apellido") String nuevo_apellido,
            @RequestParam(required = false, name = "dni") String nuevo_dni){
        
        //Envio id_cliente(Para buscar)
        //Envio nuevos datos para modificar
        clienteServ.editCliente(id_cliente, codigo_nuevo, nuevo_nombre, nuevo_apellido, nuevo_dni);
        
        //busco el cliente editado para mostrarlo en la Response
        Cliente clien = clienteServ.findCliente(id_cliente);
        //retorna el nuevo cliente
        return clien;
    }
    //Edicion por objeto        
    @PutMapping("/clientes/editar")
    public String editCliente(@RequestBody Cliente clien) {
        
        clienteServ.editCliente(clien);
        return "El cliente fue editado correctamente";
    }
}

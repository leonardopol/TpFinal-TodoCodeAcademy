package com.Pierro_Leonardo.TpFinal_Bazar.service;

import com.Pierro_Leonardo.TpFinal_Bazar.model.Cliente;
import com.Pierro_Leonardo.TpFinal_Bazar.repository.IClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements IClienteService{
    
    @Autowired
    private IClienteRepository clienteRepo;

    //traer todos los clientes
    @Override
    public List<Cliente> getClientes() {
        List<Cliente> listaClientes = clienteRepo.findAll();
        return listaClientes;
    }
    //Alta cliente
    @Override
    public void saveCliente(Cliente client) {
        clienteRepo.save(client);
    }
    //Eliminar cliente
    @Override
    public void deleteCliente(Long id_cliente) {
        clienteRepo.deleteById(id_cliente);
    }
    //Traer cliente por id
    @Override
    public Cliente findCliente(Long id_cliente) {
        return clienteRepo.findById(id_cliente).orElse(null);
    }
    //Editar cliente por parametros
    @Override
    public void editCliente(Long id_cliente, Long id_nueva, String nuevo_nombre, String nuevo_apellido, String nuevo_dni){
        //Ocupo this porque llamo al mismo metodo findCliente de esta clase
        Cliente clie = this.findCliente(id_cliente);
        clie.setId_cliente(id_nueva);
        clie.setNombre(nuevo_nombre);
        clie.setApellido(nuevo_apellido);
        clie.setDni(nuevo_dni);
        
        //Ocupo this porque llamo al mismo metodo saveCliente de esta clase
        this.saveCliente(clie);
    }
    //Editar cliente objeto
    @Override
    public void editCliente(Cliente client) {
        this.saveCliente(client);
    }
    
}

package com.Pierro_Leonardo.TpFinal_Bazar.service;

import com.Pierro_Leonardo.TpFinal_Bazar.model.Cliente;
import java.util.List;


public interface IClienteService {
    
    //método para traer a todos los clientes
    //lectura
    public List<Cliente> getClientes();

    //Metodo para dar de alta un cliente
    public void saveCliente(Cliente client);

    //Metodo para dar de baja un cliente
    public void deleteCliente(Long id_cliente);

    //lectura de un solo cliente
    public Cliente findCliente(Long id_cliente);
    
    //Modificacion cliente por parametros
    public void editCliente(Long id_cliente, Long id_nueva, String nuevo_nombre, String nuevo_apellido, String nuevo_dni);

    //edición/modificación por objeto
    public void editCliente(Cliente client);
}

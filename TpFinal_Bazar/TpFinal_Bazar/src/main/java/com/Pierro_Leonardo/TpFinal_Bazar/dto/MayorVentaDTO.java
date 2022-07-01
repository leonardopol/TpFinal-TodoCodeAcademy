package com.Pierro_Leonardo.TpFinal_Bazar.dto;

import com.Pierro_Leonardo.TpFinal_Bazar.model.Producto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MayorVentaDTO {
    
    private Long codigo_venta;
    private Double total;
    private Double cantidad_productos;
    private String nombre_cliente;
    private String apellido_cliente;
    //ignoro en la respuesta ya que no intereza mostrarla
    @JsonIgnore
    private List<Producto> listaProductos;

    public MayorVentaDTO() {
    }

    public MayorVentaDTO(Long codigo_venta, Double total, Double cantidad_productos, String nombre_cliente, String apellido_cliente, List<Producto> listaProductos) {
        this.codigo_venta = codigo_venta;
        this.total = total;
        this.cantidad_productos = cantidad_productos;
        this.nombre_cliente = nombre_cliente;
        this.apellido_cliente = apellido_cliente;
        this.listaProductos = listaProductos;
    }

    
    
    
}

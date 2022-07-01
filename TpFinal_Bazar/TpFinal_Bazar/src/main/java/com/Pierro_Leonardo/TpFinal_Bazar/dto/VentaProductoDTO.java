package com.Pierro_Leonardo.TpFinal_Bazar.dto;

import com.Pierro_Leonardo.TpFinal_Bazar.model.Producto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VentaProductoDTO {
    
    private Long codigo_venta;
    private List<Producto> lista_productos;

    public VentaProductoDTO() {
    }

    public VentaProductoDTO(Long codigo_venta, List<Producto> lista_productos) {
        this.codigo_venta = codigo_venta;
        this.lista_productos = lista_productos;
    }
    
    
}

package com.Pierro_Leonardo.TpFinal_Bazar.service;

import com.Pierro_Leonardo.TpFinal_Bazar.dto.MayorVentaDTO;
import com.Pierro_Leonardo.TpFinal_Bazar.dto.VentaMontoDiaDTO;
import com.Pierro_Leonardo.TpFinal_Bazar.dto.VentaProductoDTO;
import com.Pierro_Leonardo.TpFinal_Bazar.model.Venta;
import java.time.LocalDate;
import java.util.List;

public interface IVentaService {
   
    //método para traer a todos las ventas
    //lectura
    public List<Venta> getVentas();

    //Metodo para dar de alta una venta
    public void saveVenta(Venta vent);

    //Metodo para dar de baja una venta
    public void deleteVenta(Long codigo_venta);

    //lectura de una solo objeto
    public Venta findVenta(Long codigo_venta);
    
    //Modificacion venta
    public void editVenta(Long codigo_venta, Long codigo_nuevo, LocalDate nueva_fecha_venta, Double nuevo_total);

    //edición/modificación incluyendo listas asociadas y objetos completos.
    public void editVenta(Venta vent);
    
    //obtener lista de productos de una determinada venta
    public VentaProductoDTO productosPorVenta(Long codigo_venta);
    
    //Sumatoria de monto y cantidad total de ventas de una determinada fecha
    public VentaMontoDiaDTO ventaMontoDia(String fecha);
    
    //Venta calculo automatico y actualizacion stock
    public void saveVentaCalculo(Venta vent);
    
    //cliente con la venta mas alta
    public MayorVentaDTO getMayorVenta();
    
    //
}

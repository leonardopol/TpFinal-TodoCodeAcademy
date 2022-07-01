package com.Pierro_Leonardo.TpFinal_Bazar.controller;

import com.Pierro_Leonardo.TpFinal_Bazar.dto.MayorVentaDTO;
import com.Pierro_Leonardo.TpFinal_Bazar.dto.VentaMontoDiaDTO;
import com.Pierro_Leonardo.TpFinal_Bazar.dto.VentaProductoDTO;
import com.Pierro_Leonardo.TpFinal_Bazar.model.Venta;
import com.Pierro_Leonardo.TpFinal_Bazar.service.IVentaService;
import java.time.LocalDate;
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
public class VentaController {
    
    @Autowired
    private IVentaService ventaServ;

    /*----------------- 3 CRUD COMPLETO DE VENTAS----------------*/
    //Listado de ventas
    @GetMapping("/ventas")
    public List<Venta> traerVentas() {
        return ventaServ.getVentas();
    }
    
    //traer venta en particular
    @GetMapping("/ventas/{codigo_venta}")
    public Venta traerVenta(@PathVariable Long codigo_venta){
        return ventaServ.findVenta(codigo_venta);
    }
    //crear
    @PostMapping("/ventas/crear")
    public String saveVenta(@RequestBody Venta vent) {
        ventaServ.saveVenta(vent);
        
        return "La venta fue creada correctamente";
    }

    //Eliminar venta
    @DeleteMapping("/ventas/eliminar/{id_venta}")
    public String deleteVenta(@PathVariable Long id_venta) {
        ventaServ.deleteVenta(id_venta);
        return "La venta fue borrada correctamente";
    }
    
    //Edicion por codigo
    @PutMapping("/ventas/editar/{codigo_venta}")
    public Venta editVenta(@PathVariable Long codigo_venta,
            @RequestParam(required = false, name = "id") Long codigo_nuevo,
            @RequestParam(required = false, name = "fecha") LocalDate nueva_fecha_venta,
            @RequestParam(required = false, name = "total") Double nuevo_total){
        
        //Envio codigo_venta(Para buscar)
        //Envio nuevos datos para modificar
        ventaServ.editVenta(codigo_venta, codigo_nuevo, nueva_fecha_venta, nuevo_total);
        
        //busco la venta editada para mostrarla en la Response
        Venta vent = ventaServ.findVenta(codigo_venta);
        //retorna la nueva venta
        return vent;
    }
    //Edicion por objeto
    @PutMapping("/ventas/editar")
    public String editVenta(@RequestBody Venta vent) {
        
        ventaServ.editVenta(vent);
        return "La venta fue editada correctamente";
    }
    /*-------5. Obtener la lista de productos de una determinada venta---------*/
    //traer productos de una determinada venta
    @GetMapping ("/ventas/productos/{codigo_venta}")
    public VentaProductoDTO productosPorVenta (@PathVariable Long codigo_venta) {
        return ventaServ.productosPorVenta(codigo_venta);
    
    }
    /*----6. Obtener la sumatoria del monto y también cantidad total de ventas de un determinado dia ----*/
    //sumatoria de monto y cantidad total de ventas de una determinada fecha
    @GetMapping("/ventas/total/{fecha}")
    public VentaMontoDiaDTO ventaMontoDia(@PathVariable String fecha){
        return ventaServ.ventaMontoDia(fecha);
    }
    /*---7. Obtener el codigo_venta, el total, la cantidad de productos, el nombre del cliente y el
    apellido del cliente de la venta con el monto más alto de todas. */
    
    //Mostrar codigo_venta,total, cantidad de productos nombre Cliente, apellido, de la mayor venta
    @GetMapping ("/ventas/mayor_venta")
    public MayorVentaDTO getMayorVenta(){
        return ventaServ.getMayorVenta();
    }
    
    /*----------8. BONUS (OPCIONAL)-----*/
    
    //crea venta calculo total automatico, descuenta y actualiza stock. si es stock 0 no lo suma a la venta
    @PostMapping("/ventas/crear/calculo")
    public String saveVentaCalculo(@RequestBody Venta vent) {
        ventaServ.saveVentaCalculo(vent);
        return "La venta fue creada correctamente";
    }
}

package com.Pierro_Leonardo.TpFinal_Bazar.service;

import com.Pierro_Leonardo.TpFinal_Bazar.dto.MayorVentaDTO;
import com.Pierro_Leonardo.TpFinal_Bazar.dto.VentaMontoDiaDTO;
import com.Pierro_Leonardo.TpFinal_Bazar.dto.VentaProductoDTO;
import com.Pierro_Leonardo.TpFinal_Bazar.model.Venta;
import com.Pierro_Leonardo.TpFinal_Bazar.repository.IVentaRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private IVentaRepository ventaRepo;
    
    //inyecto dependencia a la interface para poder usar metodos de otro service
    @Autowired
    private IProductoService produServ;

    //lista total de ventas
    @Override
    public List<Venta> getVentas() {
        List<Venta> listaVentas = ventaRepo.findAll();
        return listaVentas;
    }
    //Alta venta
    @Override
    public void saveVenta(Venta vent) {
        ventaRepo.save(vent);
    }
    //Alta venta con calculo automatico del total y actualizacion de stock
    //si no hay stock el producto no se agrega a la venta
    @Override
    public void saveVentaCalculo(Venta vent) {
        
        Double suma = 0.0;
        Double cantidad = 0.0;
        Venta venta = new Venta();
        
        venta.setCodigo_venta(vent.getCodigo_venta());
        venta.setFecha_venta(vent.getFecha_venta());
        venta.setUnCliente(vent.getUnCliente());
        
        //creo un arreglo para guardar los codigos de producto. con el tamaño de la lista de productos.
        Long codigo_producto[];
        codigo_producto = new Long[vent.getLista_productos().size()];
        //Recorro lista de productos y guardo los codigos
        for(int i = 0; i < vent.getLista_productos().size(); i++){
            
            codigo_producto[i] = vent.getLista_productos().get(i).getCodigo_producto();
        }
            //seteo la lista de productos actualizada. soloProductosConStock recibe un array con los codigos
            //chequea la cantidad disponible de cada producto y devuelve una lista actualizada.
            venta.setLista_productos(produServ.soloProductosConStock(codigo_producto));
            
        for(int j = 0; j < venta.getLista_productos().size(); j++){
            //recorro la lista de productos ahora actualizada y sumo los costos de cada producto para calcular
            //el total de la venta
            suma = suma + venta.getLista_productos().get(j).getCosto();
        }

        venta.setTotal(suma);
        //persisto los datos
        ventaRepo.save(venta);
        
        //Actualiza stock producto, una ves que se vende el metodo actualizaStockProducto recibe la id
        //del producto y le descuenta
        for(int k = 0; k < venta.getLista_productos().size(); k++){
            Long id = venta.getLista_productos().get(k).getCodigo_producto();
            produServ.actualizaStockProducto(id);
        }
    }
    //Baja venta por id
    @Override
    public void deleteVenta(Long id_venta) {
        ventaRepo.deleteById(id_venta);
    }
    //encuentra una venta en particular por id
    @Override
    public Venta findVenta(Long id_venta) {
        return ventaRepo.findById(id_venta).orElse(null);
    }
    //Editar venta por parametros
    @Override
    public void editVenta(Long codigo_venta, Long codigo_nuevo, LocalDate nueva_fecha_venta, Double nuevo_total) {
        //Ocupo this porque llamo al mismo metodo findVenta de esta clase
        Venta vent = this.findVenta(codigo_venta);
        vent.setCodigo_venta(codigo_nuevo);
        vent.setFecha_venta(nueva_fecha_venta);
        vent.setTotal(nuevo_total);
        
        //Ocupo this porque llamo al mismo metodo saveVenta de esta clase
        this.saveVenta(vent);
    }
    //Editar venta por objeto
    @Override
    public void editVenta(Venta vent) {
        this.saveVenta(vent);
    }
    //trae productos por venta mediante objeto dto
    @Override
    public VentaProductoDTO productosPorVenta(Long codigo_venta) {
        
        VentaProductoDTO venProDTO = new VentaProductoDTO ();
        Venta venta = this.findVenta(codigo_venta);
        venProDTO.setCodigo_venta(venta.getCodigo_venta());
        venProDTO.setLista_productos(venta.getLista_productos());
        
        return venProDTO;
        
    }
    //sumatoria de monto y cantidad total de ventas de una determinada fecha
    @Override
    public VentaMontoDiaDTO ventaMontoDia(String fecha) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(fecha, formatter);
        //System.out.println(date);
        
        VentaMontoDiaDTO venMonDia = new VentaMontoDiaDTO();
        List<Venta> listaVentas = this.getVentas();
        Double suma = 0.0;
        Double unaVenta = 0.0;
        Double contador = 0.0;
        
        for(Venta venta : listaVentas){
            
            if(venta.getFecha_venta().equals(date)){
                
                unaVenta = venta.getTotal();
                suma = suma + unaVenta;
                contador = contador + 1;
            }
                venMonDia.setFecha(date);
                venMonDia.setCantidadDeVentas(contador);
                //Reduzco decimales a 2
                Double roundSuma = Math.round(suma*100.0)/100.0;
                venMonDia.setSumaTotal(roundSuma);
        }
        
        return venMonDia;
    }
    //Mostrar codigo_venta,total, cantidad de productos nombre Cliente, apellido, de la mayor venta
    @Override
    public MayorVentaDTO getMayorVenta() {
        List<Venta> listaVentas = this.getVentas();
        MayorVentaDTO mayorDTO = new MayorVentaDTO();
        Double total = 0.0;
        Double temp = 0.0;
        Double contador = 0.0;
        for(Venta mayorVen : listaVentas){
            temp = mayorVen.getTotal();
            if(temp > total){
                total = temp;
                Double roundTotal = Math.round(total*100.0)/100.0;
                mayorDTO.setTotal(roundTotal);
                mayorDTO.setCodigo_venta(mayorVen.getCodigo_venta());
                mayorDTO.setNombre_cliente(mayorVen.getUnCliente().getNombre());
                mayorDTO.setApellido_cliente(mayorVen.getUnCliente().getApellido());
                mayorDTO.setListaProductos(mayorVen.getLista_productos());
                
                for(int i=0; i < mayorDTO.getListaProductos().size(); i++){
                    contador = contador + 1;
                    mayorDTO.setCantidad_productos(contador);
                }
            }
        }
           return mayorDTO;
    }

}

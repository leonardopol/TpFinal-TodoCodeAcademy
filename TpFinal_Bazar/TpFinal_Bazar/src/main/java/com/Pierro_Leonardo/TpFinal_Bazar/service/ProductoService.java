package com.Pierro_Leonardo.TpFinal_Bazar.service;

import com.Pierro_Leonardo.TpFinal_Bazar.model.Producto;
import com.Pierro_Leonardo.TpFinal_Bazar.repository.IProductoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository produRepo;
    
    //trae los productos
    @Override
    public List<Producto> getProductos() {
        List<Producto> listaProductos = produRepo.findAll();
        return listaProductos;
    }
    //Alta productos
    @Override
    public void saveProducto(Producto produc) {
        produRepo.save(produc);
    }
    //Baja productos
    @Override
    public void deleteProducto(Long id_producto) {
        produRepo.deleteById(id_producto);
    }
    //Encuentra un producto por id
    @Override
    public Producto findProducto(Long id_producto) {
        return produRepo.findById(id_producto).orElse(null);
    }
    //Edita los productos por parametros
    @Override
    public void editProducto(Long codigo_producto, Long codigo_nuevo, String nuevo_nombre, String nueva_marca, Double nuevo_costo, Double cantidad_disponible){
        //Ocupo this porque llamo al mismo metodo findProducto de esta clase
        Producto produ = this.findProducto(codigo_producto);
        produ.setCodigo_producto(codigo_nuevo);
        produ.setNombre(nuevo_nombre);
        produ.setMarca(nueva_marca);
        produ.setCosto(nuevo_costo);
        produ.setCantidad_disponible(cantidad_disponible);
        
        //Ocupo this porque llamo al mismo metodo saveProducto de esta clase
        this.saveProducto(produ);
    }
    //Edita productos por objeto
    @Override
    public void editProducto(Producto produc) {
        this.saveProducto(produc);
    }
    //Obtener productos con cantidad_disponible sea menor a 5
    @Override
    public List<Producto> getFaltaStock() {
        List<Producto> listaProductos = this.getProductos();
        List<Producto> listaStock = new ArrayList<Producto>();
        
        for(Producto pro : listaProductos){
            if(pro.getCantidad_disponible() < 5){
                listaStock.add(pro);
            }
        }
        return listaStock;
    }
    
     //encuentra un producto por id y devuelve el costo
    
    @Override
    public Double findCostoProducto(Long id_producto){
        Double costo = 0.0;
        
        List<Producto> listaProductos = this.getProductos();
        for(Producto pro : listaProductos){
            if(pro.getCodigo_producto().equals(id_producto)){
                costo = pro.getCosto();
               
            }
        }
        return costo;
    }
    
    //encuentra un producto por id y verifica si hay stock distinto de 0
    @Override
    public boolean findStockProducto(Long id_producto){
        boolean faltaStock = false;
        List<Producto> listaProductos = this.getProductos();
        for(Producto pro : listaProductos){
            if(pro.getCodigo_producto().equals(id_producto)){
                if(pro.getCantidad_disponible() <= 0){
                    faltaStock = true;
                }
                else{
                    faltaStock = false;
                }
            }
        }
        return faltaStock;
    }
    
    //encuentra un producto y le resta 1 a la cantidad dependiendo de la venta y actualiza stock
    @Override
    public void actualizaStockProducto(Long id_producto){
        Double cantidad = 0.0;
        List<Producto> listaProductos = this.getProductos();
        Producto produ = new Producto();
        
        for(Producto pro : listaProductos){
            if(pro.getCodigo_producto().equals(id_producto)){
                cantidad = pro.getCantidad_disponible() - 1;
                produ.setCodigo_producto(pro.getCodigo_producto());
                produ.setCosto(pro.getCosto());
                produ.setLista_ventas(pro.getLista_ventas());
                produ.setMarca(pro.getMarca());
                produ.setNombre(pro.getNombre());
                produ.setCantidad_disponible(cantidad);
            }
        }
        this.editProducto(produ);
    }
    //devuelve lista de productos que tengan stock para la venta
    @Override
    public List<Producto> soloProductosConStock(Long [] codigo_producto) {
        List<Producto> listaProductos = new ArrayList<Producto>();
        List<Producto> listaActualizada = new ArrayList<Producto>();
        
        boolean noIncluir = false;
        Double cantidad = 0.0;
        Long codigo;
        Long id;
        
            for(int j = 0; j < codigo_producto.length; j++){
                
                //System.out.println(codigo_producto[j]);
                codigo = codigo_producto[j];
                
                listaProductos.add(this.findProducto(codigo));
                
            }
        
        for(int i = 0; i < listaProductos.size(); i++){
            
            if(listaProductos.get(i).getCantidad_disponible() <= cantidad){
                noIncluir = true;
            } else{
                //System.out.println(listaProductos.get(i).getNombre());
                id = listaProductos.get(i).getCodigo_producto();
                listaActualizada.add(this.findProducto(id));
            }
        }
        return listaActualizada ;
    }
}

package com.Pierro_Leonardo.TpFinal_Bazar.service;

import com.Pierro_Leonardo.TpFinal_Bazar.model.Producto;
import java.util.List;

public interface IProductoService {
    
    //método para traer a todos los productos
    //lectura
    public List<Producto> getProductos();

    //Metodo para dar de alta un producto
    public void saveProducto(Producto produc);

    //Metodo para dar de baja un producto
    public void deleteProducto(Long id_producto);

    //lectura de un solo producto
    public Producto findProducto(Long id_producto);
    
    //encuentra un producto por id y devuelve el costo
    public Double findCostoProducto(Long id_producto);
    
    //encuentra un producto y le resta 1 a la cantidad y lo actualiza
    public void actualizaStockProducto(Long id_producto);
    
    //encuentra un producto por id y verifica si hay stock distinto a 0
    public boolean findStockProducto(Long id_producto);
    
    //Metodo para editar un producto
    public void editProducto(Long codigo_producto, Long codigo_nuevo, String nuevo_nombre, String nueva_marca, 
                             Double nuevo_costo, Double cantidad_disponible);

    //edición/modificación
    public void editProducto(Producto produc);
    
    //Falta Stock
    public List<Producto> getFaltaStock(); 
    
    //Actualiza la lista de Productos cuya cantidad sean distintos de 0
    public List<Producto> soloProductosConStock(Long [] codigo_producto);
}

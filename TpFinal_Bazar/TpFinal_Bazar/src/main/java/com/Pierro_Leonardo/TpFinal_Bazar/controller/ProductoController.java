package com.Pierro_Leonardo.TpFinal_Bazar.controller;

import com.Pierro_Leonardo.TpFinal_Bazar.model.Producto;
import com.Pierro_Leonardo.TpFinal_Bazar.service.IProductoService;
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
public class ProductoController {
    
    
    @Autowired
    private IProductoService productoServ;
    
    /*-------------- 1 CRUD COMPLETO DE PRODUCTOS---------------*/
    //Creacion
    @PostMapping("/productos/crear")
    public String saveProducto(@RequestBody Producto produ) {
        productoServ.saveProducto(produ);

        return "El producto fue creado correctamente";
    }
    //Lista completa de productos
    @GetMapping("/productos")
    public List<Producto> traerProductos() {
        return productoServ.getProductos();
    }
    //traer producto en particular
    @GetMapping("/productos/{codigo_producto}")
    public Producto traerProducto(@PathVariable Long codigo_producto){
        return productoServ.findProducto(codigo_producto);
    }
    //Eliminacion
    @DeleteMapping("/productos/eliminar/{id_producto}")
    public String deleteProducto(@PathVariable Long id_producto) {
        productoServ.deleteProducto(id_producto);
        return "El producto fue eliminado correctamente";
    }
    //Edicion por codigo
    @PutMapping("/productos/editar/{codigo_producto}")
    public Producto editProducto(@PathVariable Long codigo_producto,
            @RequestParam(required = false, name = "id_nueva") Long codigo_nuevo,
            @RequestParam(required = false, name = "nombre") String nuevo_nombre,
            @RequestParam(required = false, name = "marca") String nueva_marca,
            @RequestParam(required = false, name = "costo") Double nuevo_costo,
            @RequestParam(required = false, name = "cantidad_disponible") Double cantidad_disponible){
        
        //Envio codigo_producto(Para buscar)
        //Envio nuevos datos para modificar
        productoServ.editProducto(codigo_producto, codigo_nuevo, nuevo_nombre, nueva_marca, nuevo_costo, cantidad_disponible);
        
        //busco el producto editado para mostrarlo en la Response
        Producto produ = productoServ.findProducto(codigo_producto);
        //retorna el nuevo producto
        return produ;
    }
    //Edita objeto        
    @PutMapping("/productos/editar")
    public String editProducto(@RequestBody Producto produ) {
        
        productoServ.editProducto(produ);
        return "El producto fue editado correctamente";
    }
    /*---------4. Obtener todos los productos cuya cantidad_disponible sea menor a 5*/
    //Lista Stock faltante
    @GetMapping("/productos/falta_stock")
    public List<Producto> traerStock(){
        return productoServ.getFaltaStock();
    }
    /*------------Bonus Productos--------------------------------*/
    //trae el costo de un producto
    @GetMapping("/productos/costo_producto/{codigo_producto}")
    public String findCostoProducto(@PathVariable Long codigo_producto){
        return "El costo del Producto es: " + productoServ.findCostoProducto(codigo_producto);
    }
    //Verifica si hay o no hay producto
    @GetMapping("/productos/faltante/{codigo_producto}")
    public String faltanteProducto(@PathVariable Long codigo_producto){
        return "Hay faltante de producto: " + productoServ.findStockProducto(codigo_producto);
    }
}

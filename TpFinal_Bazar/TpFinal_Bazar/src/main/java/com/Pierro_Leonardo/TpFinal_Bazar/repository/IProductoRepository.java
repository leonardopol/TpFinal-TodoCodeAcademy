package com.Pierro_Leonardo.TpFinal_Bazar.repository;

import com.Pierro_Leonardo.TpFinal_Bazar.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long>{
    
}

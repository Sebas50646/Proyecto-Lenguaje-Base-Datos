package com.tienda.dao;

import com.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoDao
        extends JpaRepository<Producto, Long> {

    /*Método que usa Query un listado de productos filtrado por precio,
    ordenado por descipcion*/
    public List<Producto> findByPrecioBetweenOrderByDescripcion(double precioInf, double precioSup);

    /*Método que usa JPQL un listado de productos filtrado por precio,
    ordenado por descipcion*/
    @Query(value="Select a from Producto a where a.precio between :precioInf and :precioSup order by a.descripcion asc")
    public List<Producto> consultaJPQL(double precioInf, double precioSup);
    
    /*Método que usa SQL un listado de productos filtrado por precio,
    ordenado por descipcion*/
    @Query(nativeQuery=true,value="Select * from producto a where a.precio between :precioInf and :precioSup order by a.descripcion asc")
    public List<Producto> consultaSQL(double precioInf, double precioSup);
}

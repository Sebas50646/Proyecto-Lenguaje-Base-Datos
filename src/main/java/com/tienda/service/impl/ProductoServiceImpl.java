package com.tienda.service.impl;

import com.tienda.dao.ProductoDao;
import com.tienda.domain.Producto;
import com.tienda.service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo) {
        var productos = productoDao.findAll();
        //Si son solo activas (activo = true) debo filtrar las inactivas...
        if (activo) {
            productos.removeIf(e -> !e.isActivo());
        }
        return productos;
    }

    //Se obtiene una Categoría según el Id pasado por parámetro
    @Transactional(readOnly = true)
    @Override
    public Producto getProducto(Producto producto) {
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    }

    //Se actualiza una producto o se inserta una nueva..(Si no hay id es un insert)
    @Transactional
    @Override
    public void save(Producto producto) {
        // Verifica si el producto ya existe en la base de datos
        if (producto.getIdProducto() != null) {
            // Carga el producto existente desde la base de datos
            Producto existingProduct = productoDao.findById(producto.getIdProducto()).orElseThrow(()
                    -> new EntityNotFoundException("Producto no encontrado con id: " + producto.getIdProducto()));

            // Solo actualiza los campos que no son nulos
            if (producto.getDescripcion() != null) {
                existingProduct.setDescripcion(producto.getDescripcion());
            }

            // Si el checkbox no se selecciona, 'activo' no se enviará en la solicitud y se establecerá en false
            existingProduct.setActivo(producto.isActivo());

            if (producto.getRutaImagen() != null) {
                existingProduct.setRutaImagen(producto.getRutaImagen());
            }

            // Actualiza la categoría si se envió una nueva
            if (producto.getCategoria() != null && producto.getCategoria().getIdCategoria() != null) {
                existingProduct.setCategoria(producto.getCategoria());
            }

            // Guarda el producto actualizado
            productoDao.save(existingProduct);
        } else {
            // Si es un nuevo producto, simplemente guárdalo
            productoDao.save(producto);
        }
    }

    //Se elimina una producto segun el id pasado 
    @Transactional
    @Override
    public void delete(Producto producto) {
        productoDao.delete(producto);
    }

    /*Método para obtener un listado de productos filtrado por precio,
    ordenado por descipcion*/
    @Transactional(readOnly = true)
    @Override
    public List<Producto> consultaQuery(double precioInf, double precioSup) {
        return productoDao.findByPrecioBetweenOrderByDescripcion(precioInf, precioSup);
    }

    /*Método para obtener un listado de productos filtrado por precio,
    ordenado por descipcion*/
    @Transactional(readOnly = true)
    @Override
    public List<Producto> consultaJPQL(double precioInf, double precioSup) {
        return productoDao.consultaJPQL(precioInf, precioSup);
    }

    /*Método para obtener un listado de productos filtrado por precio,
    ordenado por descipcion*/
    @Transactional(readOnly = true)
    @Override
    public List<Producto> consultaSQL(double precioInf, double precioSup) {
        return productoDao.consultaSQL(precioInf, precioSup);
    }
}

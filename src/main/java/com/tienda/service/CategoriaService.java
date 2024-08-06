package com.tienda.service;

import com.tienda.domain.Categoria;
import java.util.List;

public interface CategoriaService {
    
    //Se obtiene un array List con todas las categorias de la tabla
    public List<Categoria> getCategorias(boolean activo);
    
    //Se obtiene una Categoría según el Id pasado por parámetro
    public Categoria getCategoria(Categoria categoria);
    
    //Se actualiza una categoria o se inserta una nueva..(Si no hay id es un insert)
    public void save(Categoria categoria);
    
    //Se elimina una categoria segun el id pasado 
    public void delete(Categoria categoria);
}

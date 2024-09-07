package com.libreria.bdlibreria.repository;

import com.libreria.bdlibreria.model.Producto;;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional

public class ProductoRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    public Producto save(Producto producto) {
        entityManager.persist(producto);
        return producto;
    }

    public Producto findById(Long id) {
        return entityManager.find(Producto.class, id);
    }

    public List<Producto> findAll() {
        return entityManager.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
    }

    public Producto update(Producto producto) {
        return entityManager.merge(producto);
    }

    public void delete(Long id) {
        Producto producto = findById(id);
        if (producto != null) {
            entityManager.remove(producto);
        }
    }
}

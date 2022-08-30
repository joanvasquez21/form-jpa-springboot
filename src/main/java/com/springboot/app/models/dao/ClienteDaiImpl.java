package com.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springboot.app.models.entity.Cliente;

// Para marcar la clase como componente de Persistencia acceso a datos
@Repository
public class ClienteDaiImpl implements IClienteDao {

	@PersistenceContext
	private EntityManager em;

	// consulta
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList();

	}

	// readonly porque es de consulta
	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {

		return em.find(Cliente.class, id);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		if (cliente.getId() != null && cliente.getId() > 0) {
			// vamos a editar, merge actualiza
			em.merge(cliente);
		} else {
			// persist crea un nuevo cliente y inserta y lo atacha al concepto de
			// persistencia
			em.persist(cliente);
		}
	}

	// insertar si el id es igual a 0 o nula cuando es id es mayor a 0

	// delete-save metodos que actualizan la tabla
	@Override
	@Transactional
	public void delete(Long id) {
		// Primero obtenemos el cliente
		Cliente cliente = findOne(id);
		em.remove(cliente);

	}

}

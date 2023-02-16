package com.email.app.be.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.email.app.be.models.Usuario;

@Repository("usuarioRepositorio")
public interface IUsuarioRepository extends CrudRepository<Usuario,Long>{

	Usuario findByCorreoIgnoreCase(String correo);
}

package com.email.app.be.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.email.app.be.models.Token;

@Repository("tokenRepositorio")
public interface IConfirmacionToken extends CrudRepository<Token,Long>{
	
	Token findByConfirmacion(String confirmacion);
}

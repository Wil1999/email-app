package com.email.app.be.models;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="token")
public class Token {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tokenId;
	
	private String confirmacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	
	@OneToOne(targetEntity = Usuario.class,fetch= FetchType.EAGER)
	@JoinColumn(nullable = false,name="user_id")
	private Usuario usuario;


	public Token() {
	}
	
	public Token(Usuario usuario){
		this.usuario = usuario;
		fecha = new Date();
		confirmacion = UUID.randomUUID().toString();
	}

	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public String getConfirmacion() {
		return confirmacion;
	}

	public void setConfirmacion(String confirmacion) {
		this.confirmacion = confirmacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}

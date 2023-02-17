package com.email.app.be.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CorreoService {
	
	private JavaMailSender javaMail;
	
	public CorreoService(JavaMailSender javaMail) {
		this.javaMail = javaMail;
	}
	
	@Async
	public void enviarEmail(SimpleMailMessage correo) {
		javaMail.send(correo);
	}
	
}

package com.email.app.be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.email.app.be.models.Token;
import com.email.app.be.models.Usuario;
import com.email.app.be.repository.IConfirmacionToken;
import com.email.app.be.repository.IUsuarioRepository;
import com.email.app.be.service.CorreoService;

@Controller
public class UsuarioController {
	
	@Autowired
	private IUsuarioRepository usuarioRepositorio;
	
	@Autowired
	private IConfirmacionToken confirmacionToken;
	
	@Autowired
	private CorreoService correoService;
	
	@PostMapping("/registrar")
	public ResponseEntity<String> registroUsuario(Usuario usuario){
		Usuario old_usuario = usuarioRepositorio.findByCorreoIgnoreCase(usuario.getCorreo());
		if(old_usuario != null) {
			return new ResponseEntity<>("Error, el usuario ya existe",HttpStatus.BAD_REQUEST);
		}
		usuarioRepositorio.save(usuario);
		Token tokenConfirmation = new Token(usuario);
		confirmacionToken.save(tokenConfirmation);
		SimpleMailMessage correoConfirmation = new SimpleMailMessage();
		correoConfirmation.setTo(usuario.getCorreo());
		correoConfirmation.setSubject("Registro Completo!!");
		correoConfirmation.setFrom("wcaceresaurelio@gmail.com");
		correoConfirmation.setText("Para confirmar tu cuenta, porfavor CLICK AQUI: "+ "http://localhost:8080/confirmar-correo?token="+tokenConfirmation.getConfirmacion());
		correoService.enviarEmail(correoConfirmation);
		return new ResponseEntity<>(usuario.getCorreo(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/confirmar-correo", method= {RequestMethod.GET,RequestMethod.POST})
	public ResponseEntity<String> confirmarCorreo(@RequestParam("token") String tokenConfirmation){
		Token token = confirmacionToken.findByConfirmacion(tokenConfirmation);
		if(token != null) {
			Usuario usuario = usuarioRepositorio.findByCorreoIgnoreCase(token.getUsuario().getCorreo());
			usuario.setEnabled(true);
			usuarioRepositorio.save(usuario);
			return new ResponseEntity<>("Cuenta verificada",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Error link inválido",HttpStatus.BAD_REQUEST);
		}
	}
}

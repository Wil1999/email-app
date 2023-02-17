package com.email.app.be.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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

//@CrossOrigin(allowedHeaders = "*",origins = "*")
@Controller
public class UsuarioController {
	
	@Autowired
	private IUsuarioRepository usuarioRepositorio;
	
	@Autowired
	private IConfirmacionToken confirmacionToken;
	
	@Autowired
	private CorreoService correoService;
	
	private final Logger log= LoggerFactory.getLogger(getClass());
	
	@PostMapping("/registrar")
	public ResponseEntity<String> registroUsuario(Usuario usuario){
		log.info("entrando: "+usuario.getNombres() + " "+ usuario.getCorreo());
		Usuario old_usuario = usuarioRepositorio.findByCorreoIgnoreCase(usuario.getCorreo());
		if(old_usuario != null) {
			return new ResponseEntity<>("Error, el usuario ya existe",HttpStatus.BAD_REQUEST);
		}
		usuarioRepositorio.save(usuario);
		Token tokenConfirmation = new Token(usuario);
		confirmacionToken.save(tokenConfirmation);
		SimpleMailMessage correoConfirmation = new SimpleMailMessage();
		correoConfirmation.setTo(usuario.getCorreo());
		correoConfirmation.setSubject("COMPLETA TU REGISTRO!!");
		correoConfirmation.setFrom("marco.rimac@ae.edu.pe");
		correoConfirmation.setText("Para activar tu correo, porfavor CLICK AQUI: "+ "https://emailappbe.herokuapp.com/confirmar-correo?token="+tokenConfirmation.getConfirmacion());
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
			return new ResponseEntity<>("Tu correo ha sido ACTIVADO exitósamente!! Puedes disfrutar de tu cuenta.",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Tu cuenta ya ha sido activada!!",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Usuario>> listarUsuarios(){
		List<Usuario> result = (List<Usuario>) usuarioRepositorio.findAll();
		if(result.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
}

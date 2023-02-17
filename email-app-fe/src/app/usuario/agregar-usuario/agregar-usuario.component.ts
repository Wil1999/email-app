import { UsuarioDO } from './../../models/UsuarioDO';
import { Usuario } from './../../models/Usuario';
import { UsuarioService } from './../usuario.service';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

declare var $:any;

@Component({
  selector: 'app-agregar-usuario',
  templateUrl: './agregar-usuario.component.html',
  styleUrls: ['./agregar-usuario.component.css']
})
export class AgregarUsuarioComponent {

  formulario !:  FormGroup;
  errorMessage: string = '';
  usuarioDO !: UsuarioDO;

  constructor(public usuarioService: UsuarioService,private router: Router, private fb:FormBuilder,private route: ActivatedRoute){
    this.crearFormulario();
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      nombres:[''],
      correo:[''],
      isEnabled:['false'],//Argumentos para validar campos
    });
  }

  onSubmit(){
    console.log( this.formulario);
    $.ajax({
      url: "http://localhost:8080/registrar",
      type: 'POST',
      data: $("#form-usuario").serialize(),
      success: function(_response: any) {
          //$('#tabla-asistencia tbody').html(htmlCode);
          $("#modalSucces").modal("show");
          $('input[type="text"]').val('');
          $('input[type="email"]').val('');
      },
      error: function(_err: any){
        this.errorMessage = "El usuario ya existe";
        $("#modalError").modal("show");
      }
    });

    //let response = this.usuarioService.crearUsuario(this.formulario.value);
    //response.subscribe(
    //  data => {
        //this.router.navigateByUrl(''); //Crear una pagina para mostrar que ya se registro el usuario pero falta validar su correo
    //  },
    //  error =>{
    //    this.errorMessage = error.message;
     // }
    //);
  }
}

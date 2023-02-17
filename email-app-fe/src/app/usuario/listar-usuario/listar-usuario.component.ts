import { Usuario } from './../../models/Usuario';
import { ActivatedRoute, Router } from '@angular/router';
import { UsuarioService } from './../usuario.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-listar-usuario',
  templateUrl: './listar-usuario.component.html',
  styleUrls: ['./listar-usuario.component.css']
})
export class ListarUsuarioComponent implements OnInit{

  usuarios: Usuario[] = [];
  mensageVacio: String= '';
  constructor(
    public usuarioService: UsuarioService,
    private route : ActivatedRoute,
    private router: Router,
  ){}

  ngOnInit(): void {
    this.usuarioService.listarTodo().subscribe(
      data => {this.usuarios = data },
      error =>{
        this.mensageVacio = "No hay usuarios registrados";
      }
    );
  }

}

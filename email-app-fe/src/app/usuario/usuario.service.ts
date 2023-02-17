import { Usuario } from './../models/Usuario';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const baseUrl = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  listarTodo(): Observable<Usuario[]>{
    return this.http.get<Usuario[]>(baseUrl+'listar');
  }

  crearUsuario(response:any): Observable<any>{
    console.log(JSON.stringify(response));
    return this.http.post<any>(baseUrl+'registrar',JSON.stringify(response),this.httpOptions);
  }

}

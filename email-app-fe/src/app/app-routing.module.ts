import { ListarUsuarioComponent } from './usuario/listar-usuario/listar-usuario.component';
import { AgregarUsuarioComponent } from './usuario/agregar-usuario/agregar-usuario.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: '', component: ListarUsuarioComponent},
  {path: 'registrar', component: AgregarUsuarioComponent},
  {path: 'listar', component: ListarUsuarioComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

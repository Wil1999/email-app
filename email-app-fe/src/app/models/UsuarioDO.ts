
export class UsuarioDO{
  constructor(
    public nombres: string,
    public correo: string,
    public isEnabled: boolean = false
  ){

  }
}

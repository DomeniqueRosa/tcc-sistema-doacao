import { Component, ViewChild } from '@angular/core'; // Adicionar ViewChild
import { FlexLayoutModule} from '@angular/flex-layout';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatInputModule} from "@angular/material/input";
import {FormsModule, NgForm} from "@angular/forms"; // Adicionar NgForm
import {MatButtonModule} from '@angular/material/button';
import { Usuario } from './usuario';

@Component({
  selector: 'app-pagina-cadastrar-usuario',
  standalone: true,
  templateUrl: './pagina-cadastrar-usuario.html',
  styleUrl: './pagina-cadastrar-usuario.css',
  imports: [ FlexLayoutModule, 
             MatCardModule,
             FormsModule, 
             MatFormFieldModule,  
             MatInputModule,
             MatButtonModule,
           ],
})

export class PaginaCadastroUsuario {
  @ViewChild('clientesFrm') clientesForm!: NgForm;

  usuario = Usuario.novoUsuario();

  salvar(){
    console.log("Dados Usuario: ", this.usuario);    
    this.usuario = Usuario.novoUsuario(); 
    
    this.clientesForm.resetForm(); 
  }
}


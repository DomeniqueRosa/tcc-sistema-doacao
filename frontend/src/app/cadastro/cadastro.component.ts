import { Component, ViewChild } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from "@angular/material/input";
import { FormsModule, NgForm } from "@angular/forms";
import { MatButtonModule } from '@angular/material/button';
import { Usuario } from './usuario';
import { UsuarioService } from '../services/usuario.service'; 
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.css',
  imports: [
    FlexLayoutModule,
    MatCardModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule
  ],
})
export class CadastroComponent {
  @ViewChild('clientesFrm') clientesForm!: NgForm;

  usuario = Usuario.novoUsuario();

  constructor(private usuarioService: UsuarioService,
    private snackBar: MatSnackBar
  ) { }

  salvar() {
    // 1. A validação deve acontecer AQUI, no início do método
    // Verifique se no seu arquivo usuario.ts o nome é 'senhaConfirmacao' ou 'confirmarSenha'
    if (this.usuario.senha !== this.usuario.senhaConfirmacao) {
      this.mostrarMensagem("As senhas não coincidem.", true);
      return; // O 'return' aqui impede que o código continue e tente salvar
    }

    // Se passou do if acima, segue para o salvamento
    console.log("Enviando dados:", this.usuario);

    this.usuarioService.salvar(this.usuario).subscribe({
      next: (data) => {
        console.log("Usuário salvo com sucesso!", data);
        this.mostrarMensagem("Usuário salvo com sucesso!");
        
        // this.usuario = Usuario.novoUsuario(); // essa linha causa erro no console
       
        setTimeout(() => {
          if(this.clientesForm){
            this.clientesForm.resetForm();
          }
        }
        , 1000);
        
      },
      error: (error) => {
        console.error("Erro ao salvar usuário", error);
        // Mostra o erro exato na tela para ajudar a debugar
        this.mostrarMensagem(`Erro ao salvar usuário: ${error.message || error}`, true);

      }
    });
  }

  // Método auxiliar para exibir o SnackBar
  mostrarMensagem(msg: string, isError: boolean = false) {
    this.snackBar.open(msg, 'Fechar', {
      duration: 5000, // Fica na tela por 5 segundos
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: isError ? ['error-snackbar'] : ['success-snackbar']
    });
  }
}
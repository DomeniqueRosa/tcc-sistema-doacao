import { Component, ViewChild } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from "@angular/material/input";
import { FormsModule, NgForm, NgModel } from "@angular/forms";
import { MatButtonModule } from '@angular/material/button';
import { Usuario } from './usuario';
import { UsuarioService } from '../services/usuario.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { NgxMaskDirective } from 'ngx-mask';

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
    MatSnackBarModule,
    NgxMaskDirective
  ],
})
export class CadastroComponent {
  @ViewChild('clientesFrm') clientesForm!: NgForm;

  usuario = Usuario.novoUsuario();

  constructor(private usuarioService: UsuarioService,
    private snackBar: MatSnackBar
  ) { }

  salvar() {

    if (this.usuario.senha !== this.usuario.senhaConfirmacao) {
      this.mostrarMensagem("As senhas não coincidem.", true);
      return;
    }

    // Se passou do if acima, segue para o salvamento
    console.log("Enviando dados:", this.usuario);

    this.usuarioService.salvar(this.usuario).subscribe({
      next: (data) => {
        console.log("Usuário salvo com sucesso!", data);
        this.mostrarMensagem("Usuário salvo com sucesso!");

        // this.usuario = Usuario.novoUsuario(); // essa linha causa erro no console

        setTimeout(() => {
          if (this.clientesForm) {
            this.clientesForm.resetForm();
          }
        }
          , 1000);

      },
      error: (error) => {
        console.error("Erro ao salvar usuário", error);

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

  validarSenhaConfirmacao(senhaConfirmacaoRef: NgModel) {
    const senha = this.usuario.senha;
  const confirmacao = this.usuario.senhaConfirmacao;

  // 
  if (senha !== confirmacao) {
    // senha diferente de confirmação: seta o erro 'senhaMismatch'
    const errosAtuais = senhaConfirmacaoRef.control.errors || {};
    senhaConfirmacaoRef.control.setErrors({ ...errosAtuais, senhaMismatch: true });
  } 
  //  Senhas iguais (precisa limpar o erro!)
  else {
    if (senhaConfirmacaoRef.control.errors && senhaConfirmacaoRef.control.errors['senhaMismatch']) {
      delete senhaConfirmacaoRef.control.errors['senhaMismatch'];
      
      // Se não sobrou nenhum erro, seta null para o campo ficar Válido (verde)
      if (Object.keys(senhaConfirmacaoRef.control.errors).length === 0) {
        senhaConfirmacaoRef.control.setErrors(null);
      }
    }
  }
  }

  validarCpf(cpfRef: NgModel) {
    const cpf = this.usuario.cpf;

    // 'required' já é validado automaticamente pelo Angular
    if (!cpf) return;

    // remove tudo que não for número
    const numbers = cpf.toString().replace(/\D/g, '');

    // logica de validação do CPF
    let valido = true;

    if (numbers.length !== 11 || /^(\d)\1+$/.test(numbers)) {
      valido = false;
    } else {
      let sum = 0, remainder;

      for (let i = 1; i <= 9; i++) sum += parseInt(numbers.substring(i - 1, i)) * (11 - i);
      remainder = (sum * 10) % 11;
      if (remainder === 10 || remainder === 11) remainder = 0;
      if (remainder !== parseInt(numbers.substring(9, 10))) valido = false;

      sum = 0;
      for (let i = 1; i <= 10; i++) sum += parseInt(numbers.substring(i - 1, i)) * (12 - i);
      remainder = (sum * 10) % 11;
      if (remainder === 10 || remainder === 11) remainder = 0;
      if (remainder !== parseInt(numbers.substring(10, 11))) valido = false;
    }

    // 4. se nao for valido, seta o erro 'cpfInvalido' no controle
    if (!valido) {
      cpfRef.control.setErrors({ cpfInvalido: true });
    } else {
      // Se deu tudo certo, precisamos limpar o erro 'cpfInvalido' 
      // mas manter outros erros se existirem (ex: null se estiver tudo ok)
      if (cpfRef.control.errors && cpfRef.control.errors['cpfInvalido']) {
        delete cpfRef.control.errors['cpfInvalido'];
        if (Object.keys(cpfRef.control.errors).length === 0) {
          cpfRef.control.setErrors(null);
        }
      }
    }
  }
}
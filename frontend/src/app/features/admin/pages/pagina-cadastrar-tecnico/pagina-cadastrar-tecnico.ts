import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { UsuarioService } from '../../../../core/services/usuario.service';
import { TecnicoCadastroRequest } from '../../../../core/models/usuario.model';

@Component({
  selector: 'app-pagina-cadastrar-tecnico',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule
  ],
  templateUrl: './pagina-cadastrar-tecnico.html',
  styleUrl: './pagina-cadastrar-tecnico.css'
})
export class PaginaCadastrarTecnico {
  private fb = inject(FormBuilder);
  private usuarioService = inject(UsuarioService);
  private snackBar = inject(MatSnackBar);

  carregando = false;

  tecnicoForm = this.fb.group({
    nome: ['', [Validators.required, Validators.minLength(3)]],
    cpf: ['', [Validators.required, Validators.minLength(11), Validators.maxLength(11)]],
    email: ['', [Validators.required, Validators.email]],
    senha: ['', [Validators.required, Validators.minLength(6)]],
    grr: ['', [Validators.required]],
    curso: ['', [Validators.required]]
  });

  salvar(): void {
    if (this.tecnicoForm.invalid) {
      this.tecnicoForm.markAllAsTouched();
      return;
    }

    this.carregando = true;

    const dados: TecnicoCadastroRequest = {
      nome: this.tecnicoForm.value.nome!,
      cpf: this.tecnicoForm.value.cpf!,
      email: this.tecnicoForm.value.email!,
      senha: this.tecnicoForm.value.senha!,
      grr: this.tecnicoForm.value.grr!,
      curso: this.tecnicoForm.value.curso!
    };

    this.usuarioService.cadastrarTecnico(dados).subscribe({
      next: () => {
        this.snackBar.open('Aluno técnico cadastrado com sucesso!', 'Fechar', {
          duration: 3000
        });
        this.tecnicoForm.reset();
        this.carregando = false;
      },
      error: (erro) => {
        console.error('Erro ao cadastrar técnico:', erro);

        this.snackBar.open('Erro ao cadastrar aluno técnico.', 'Fechar', {
          duration: 3000
        });

        this.carregando = false;
      }
    });
  }

  campoTemErro(nomeCampo: string, erro: string): boolean {
    const campo = this.tecnicoForm.get(nomeCampo);
    return !!campo && campo.hasError(erro) && campo.touched;
  }
}
import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { UsuarioService } from '../../../../core/services/usuario.service';
import { Usuario, UsuarioAtualizacaoRequest } from '../../../../core/models/usuario.model';

@Component({
  selector: 'app-pagina-editar-usuario',
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
  templateUrl: './pagina-editar-usuario.html',
  styleUrl: './pagina-editar-usuario.css'
})
export class PaginaEditarUsuario implements OnInit {
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private usuarioService = inject(UsuarioService);
  private snackBar = inject(MatSnackBar);

  carregando = false;
  usuarioId!: number;

  form = this.fb.group({
    nome: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    cpf: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
    perfil: ['', [Validators.required]]
  });

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (!idParam) {
      this.snackBar.open('ID do usuário não informado.', 'Fechar', {
        duration: 3000
      });
      this.router.navigate(['/admin/usuarios']);
      return;
    }

    this.usuarioId = Number(idParam);

    if (isNaN(this.usuarioId)) {
      this.snackBar.open('ID do usuário inválido.', 'Fechar', {
        duration: 3000
      });
      this.router.navigate(['/admin/usuarios']);
      return;
    }

    this.buscarUsuario();
  }

  buscarUsuario(): void {
    this.carregando = true;

    this.usuarioService.buscarPorId(this.usuarioId).subscribe({
      next: (usuario: Usuario) => {
        this.form.patchValue({
          nome: usuario.nome,
          email: usuario.email,
          cpf: usuario.cpf,
          perfil: usuario.perfil
        });

        this.carregando = false;
      },
      error: (erro) => {
        console.error('Erro ao buscar usuário:', erro);

        this.snackBar.open('Erro ao carregar usuário.', 'Fechar', {
          duration: 3000
        });

        this.carregando = false;
      }
    });
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.carregando = true;

    const formValue = this.form.value;
    const dados: UsuarioAtualizacaoRequest = {
      nome: formValue.nome ?? '',
      email: formValue.email ?? '',
      cpf: formValue.cpf ?? ''
    };

    this.usuarioService.atualizarUsuario(this.usuarioId, dados).subscribe({
      next: () => {
        this.snackBar.open('Usuário atualizado com sucesso!', 'Fechar', {
          duration: 3000
        });

        this.router.navigate(['/admin/usuarios']);
      },
      error: (erro) => {
        console.error('Erro ao atualizar usuário:', erro);

        this.snackBar.open('Erro ao atualizar usuário.', 'Fechar', {
          duration: 3000
        });

        this.carregando = false;
      }
    });
  }

  campoTemErro(nomeCampo: string, erro: string): boolean {
    const campo = this.form.get(nomeCampo);
    return !!campo && campo.hasError(erro) && campo.touched;
  }
}
import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroupDirective, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-pagina-cadastro-solicitacao',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './pagina-cadastro-solicitacao.html',
  styleUrl: './pagina-cadastro-solicitacao.css'
})
export class PaginaCadastroSolicitacao {
  private fb = inject(FormBuilder);
  private router = inject(Router);

  form = this.fb.group({
    curso: ['', Validators.required],
    grr: ['', Validators.required],
    motivo: ['', [Validators.required, Validators.minLength(10)]],
    semComputador: [false, Validators.requiredTrue],
    matriculaAtiva: [false, Validators.requiredTrue]
  });

  voltar(): void {
    this.router.navigate(['/usuario/listar-solicitacoes']);
  }

  confirmar(formDirective: FormGroupDirective): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    // chamada api
    // this.solicitacaoService.cadastrarSolicitacao(this.form.value).subscribe({
    //   next: () => { ... },
    //   error: (erro) => { ... }
    // });

    console.log('Dados da solicitação:', this.form.value);

    alert('Solicitação preenchida com sucesso!');

    this.resetarFormulario(formDirective);
  }

  cancelar(formDirective: FormGroupDirective): void {
    this.resetarFormulario(formDirective);
  }

  private resetarFormulario(formDirective: FormGroupDirective): void {
    this.form.reset({
      curso: '',
      grr: '',
      motivo: '',
      semComputador: false,
      matriculaAtiva: false
    });

    formDirective.resetForm({
      curso: '',
      grr: '',
      motivo: '',
      semComputador: false,
      matriculaAtiva: false
    });
  }

  get curso() {
    return this.form.get('curso');
  }

  get grr() {
    return this.form.get('grr');
  }

  get motivo() {
    return this.form.get('motivo');
  }

  get semComputador() {
    return this.form.get('semComputador');
  }

  get matriculaAtiva() {
    return this.form.get('matriculaAtiva');
  }
}

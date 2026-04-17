import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroupDirective, ReactiveFormsModule, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { DialogSucessoDoacao } from './dialog-sucesso-doacao/dialog-sucesso-doacao';

@Component({
  selector: 'app-pagina-cadastro-doacao',
  standalone: true,
  imports: [ CommonModule, ReactiveFormsModule, MatCardModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatRadioModule,
            MatButtonModule, MatIconModule, MatDialogModule ],
  templateUrl: './pagina-cadastro-doacao.html',
  styleUrl: './pagina-cadastro-doacao.css'
})
export class PaginaCadastroDoacao {
  private fb = inject(FormBuilder);
  private dialog = inject(MatDialog);
  private router = inject(Router);

  imagemPreview: string | null = null;
  nomeArquivo = 'Nenhum arquivo selecionado';

  form = this.fb.group({
    tipoItem: ['', Validators.required],
    quantidade: [null, [Validators.required, Validators.min(1)]],
    descricao: ['', [Validators.required, Validators.minLength(5)]],
    estadoConservacao: ['USADO', Validators.required],
    imagem: [null as File | null]
  });

  tiposItens: string[] = [
    'Notebook',
    'SSD',
    'Memória RAM',
    'Tela',
    'Bateria',
    'Teclado',
    'Outro'
  ];

  voltar(): void {
    this.router.navigate(['/usuario/listar-doacoes']);
  }

  selecionarImagem(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (!input.files || input.files.length === 0) {
      return;
    }

    const arquivo = input.files[0];
    this.nomeArquivo = arquivo.name;
    this.form.patchValue({ imagem: arquivo });

    const reader = new FileReader();
    reader.onload = () => {
      this.imagemPreview = reader.result as string;
    };
    reader.readAsDataURL(arquivo);
  }

  removerImagem(): void {
    this.imagemPreview = null;
    this.nomeArquivo = 'Nenhum arquivo selecionado';
    this.form.patchValue({ imagem: null });
  }

  confirmar(formDirective: FormGroupDirective): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload = this.form.value;
    console.log('Dados da doação:', payload);

    this.dialog.open(DialogSucessoDoacao, {
      width: '480px',
      maxWidth: '92vw',
      disableClose: true,
      panelClass: 'dialog-doacao-sucesso'
    });

    this.resetarFormulario(formDirective);
  }

  cancelar(formDirective: FormGroupDirective): void {
    this.resetarFormulario(formDirective);
  }

  private resetarFormulario(formDirective: FormGroupDirective): void {
    this.form.reset({
      tipoItem: '',
      quantidade: null,
      descricao: '',
      estadoConservacao: 'USADO',
      imagem: null
    });

    formDirective.resetForm({
      tipoItem: '',
      quantidade: null,
      descricao: '',
      estadoConservacao: 'USADO',
      imagem: null
    });

    this.imagemPreview = null;
    this.nomeArquivo = 'Nenhum arquivo selecionado';
  }

  get tipoItem() {
    return this.form.get('tipoItem');
  }

  get quantidade() {
    return this.form.get('quantidade');
  }

  get descricao() {
    return this.form.get('descricao');
  }

  get estadoConservacao() {
    return this.form.get('estadoConservacao');
  }
}

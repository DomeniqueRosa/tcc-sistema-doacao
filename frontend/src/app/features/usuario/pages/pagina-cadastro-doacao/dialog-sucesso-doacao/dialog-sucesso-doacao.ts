import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-dialog-sucesso-doacao',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule, MatIconModule],
  templateUrl: './dialog-sucesso-doacao.html',
  styleUrl: './dialog-sucesso-doacao.css'
})
export class DialogSucessoDoacao {
  private dialogRef = inject(MatDialogRef<DialogSucessoDoacao>);

  fechar(): void {
    this.dialogRef.close();
  }
}
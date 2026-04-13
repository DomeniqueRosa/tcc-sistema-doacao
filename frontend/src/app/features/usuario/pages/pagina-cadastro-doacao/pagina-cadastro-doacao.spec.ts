import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaCadastroDoacao } from './pagina-cadastro-doacao';

describe('PaginaCadastroDoacao', () => {
  let component: PaginaCadastroDoacao;
  let fixture: ComponentFixture<PaginaCadastroDoacao>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaCadastroDoacao]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaCadastroDoacao);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaCadastroUsuario } from './pagina-cadastrar-usuario';

describe('PaginaCadastroUsuario', () => {
  let component: PaginaCadastroUsuario;
  let fixture: ComponentFixture<PaginaCadastroUsuario>;

  beforeEach(() => {
    fixture = TestBed.createComponent(PaginaCadastroUsuario);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});

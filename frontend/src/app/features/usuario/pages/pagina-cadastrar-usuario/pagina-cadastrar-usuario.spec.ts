import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaCadastrarUsuario } from './pagina-cadastrar-usuario';

describe('PaginaCadastroUsuario', () => {
  let component: PaginaCadastrarUsuario;
  let fixture: ComponentFixture<PaginaCadastrarUsuario>;

  beforeEach(() => {
    fixture = TestBed.createComponent(PaginaCadastrarUsuario);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});

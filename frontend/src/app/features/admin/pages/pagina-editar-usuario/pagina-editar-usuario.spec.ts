import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaEditarUsuario } from './pagina-editar-usuario';

describe('PaginaEditarUsuario', () => {
  let component: PaginaEditarUsuario;
  let fixture: ComponentFixture<PaginaEditarUsuario>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaEditarUsuario]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaEditarUsuario);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

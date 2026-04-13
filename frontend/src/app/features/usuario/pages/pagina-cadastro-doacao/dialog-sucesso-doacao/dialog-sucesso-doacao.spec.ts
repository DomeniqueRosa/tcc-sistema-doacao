import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogSucessoDoacao } from './dialog-sucesso-doacao';

describe('DialogSucessoDoacao', () => {
  let component: DialogSucessoDoacao;
  let fixture: ComponentFixture<DialogSucessoDoacao>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DialogSucessoDoacao]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialogSucessoDoacao);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

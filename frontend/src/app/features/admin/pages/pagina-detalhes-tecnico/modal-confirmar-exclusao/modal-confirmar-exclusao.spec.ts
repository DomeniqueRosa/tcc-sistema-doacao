import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalConfirmarExclusao } from './modal-confirmar-exclusao';

describe('ModalConfirmarExclusao', () => {
  let component: ModalConfirmarExclusao;
  let fixture: ComponentFixture<ModalConfirmarExclusao>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalConfirmarExclusao]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalConfirmarExclusao);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

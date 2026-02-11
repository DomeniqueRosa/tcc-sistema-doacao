import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LayoutAutenticacao } from './layout-autenticacao';

describe('LayoutAutenticacao', () => {
  let component: LayoutAutenticacao;
  let fixture: ComponentFixture<LayoutAutenticacao>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LayoutAutenticacao]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LayoutAutenticacao);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LayoutPrincipal } from './layout-principal';

describe('LayoutPrincipal', () => {
  let component: LayoutPrincipal;
  let fixture: ComponentFixture<LayoutPrincipal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LayoutPrincipal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LayoutPrincipal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

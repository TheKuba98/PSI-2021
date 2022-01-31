import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilitiesFormComponent } from './facilities-form.component';

describe('FacilitiesFormComponent', () => {
  let component: FacilitiesFormComponent;
  let fixture: ComponentFixture<FacilitiesFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FacilitiesFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilitiesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyThesisListComponent } from './my-thesis-list.component';

describe('MyThesisListComponent', () => {
  let component: MyThesisListComponent;
  let fixture: ComponentFixture<MyThesisListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyThesisListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyThesisListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

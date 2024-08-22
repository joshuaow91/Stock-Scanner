import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ButtonComponent } from './button/button.component';
import { ModalComponent } from './modal/modal.component';
import { TableComponent } from './table/table.component';
import { TimeframeButtonsComponent } from './timeframe-buttons/timeframe-buttons.component';
import {HttpClientModule} from "@angular/common/http";
import { ScenarioTransformPipe } from './scenario-transform.pipe';
import { LoginComponent } from './login-form/login.component';
import {ReactiveFormsModule} from "@angular/forms";
import { RegistrationFormComponent } from './registration-form/registration-form.component';

@NgModule({
  declarations: [
    AppComponent,
    ButtonComponent,
    ModalComponent,
    TableComponent,
    TimeframeButtonsComponent,
    ScenarioTransformPipe,
    LoginComponent,
    RegistrationFormComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ButtonComponent } from './button/button.component';
import { ModalComponent } from './modal/modal.component';
import { TableComponent } from './table/table.component';
import { TimeframeButtonsComponent } from './timeframe-buttons/timeframe-buttons.component';
import {HttpClientModule} from "@angular/common/http";
import { ScenarioTransformPipe } from './scenario-transform.pipe';

@NgModule({
  declarations: [
    AppComponent,
    ButtonComponent,
    ModalComponent,
    TableComponent,
    TimeframeButtonsComponent,
    ScenarioTransformPipe,
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ReviewPageComponent} from './admin/views/review-page/review-page.component';
import {HomeComponent} from './home/home.component';
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {StolpersteineComponent} from './home/stolpersteine/stolpersteine.component';
import {GoogleMapsModule} from "@angular/google-maps";
import {initializeKeycloak} from "./init/keycloak-init.factory";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {HttpClientModule} from "@angular/common/http";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {
  StolpersteinManageViewComponent
} from "./home/stolpersteine/stolperstein-manage-view/stolperstein-manage-view.component";
import {ReactiveFormsModule} from "@angular/forms";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatChipsModule} from "@angular/material/chips";
import {DatePipe} from "@angular/common";
import {ToastrModule, ToastrService} from "ngx-toastr";

@NgModule({
  declarations: [
    AppComponent,
    ReviewPageComponent,
    HomeComponent,
    StolpersteineComponent,
    StolpersteinManageViewComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      progressBar: true,
      progressAnimation: "increasing"
    }),
    MatInputModule,
    MatToolbarModule,
    GoogleMapsModule,
    KeycloakAngularModule,
    ReactiveFormsModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatChipsModule,
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    },
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

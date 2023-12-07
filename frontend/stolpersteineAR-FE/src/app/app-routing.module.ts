import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ReviewPageComponent} from "./admin/views/review-page/review-page.component";
import {AppComponent} from "./app.component";
import {HomeComponent} from "./home/home.component";
import {StolpersteineComponent} from "./home/stolpersteine/stolpersteine.component";
import {AuthGuard} from "./guard/auth.guard";

const routes: Routes = [
  {
    path: 'admin/review',
    component: ReviewPageComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'home/stolpersteine',
    component: StolpersteineComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

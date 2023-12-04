import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ReviewPageComponent} from "./admin/views/review-page/review-page.component";
import {AppComponent} from "./app.component";
import {HomeComponent} from "./home/home.component";

const routes: Routes = [
  {
    path: 'admin/review',
    component: ReviewPageComponent
  },
  {
    path: 'home',
    component: HomeComponent
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

import {Routes} from "@angular/router";
import {AppComponent} from "./app.component";
import {IndexComponent} from "./index/index.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'index',
    pathMatch: 'full'
  },
  {
    path: '',
    component: AppComponent
  },
  {
    path: 'index',
    component: IndexComponent
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

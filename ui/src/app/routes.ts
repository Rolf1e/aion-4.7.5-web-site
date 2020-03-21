import {Routes} from "@angular/router";
import {AppComponent} from "./app.component";
import {IndexComponent} from "./index/index.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {ForumComponent} from "./forum/forum.component";
import {SigninComponent} from "./index/login/signin/signin.component";

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
    path: 'register',
    component: SigninComponent
  },
  {
    path: 'forum',
    component: ForumComponent
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

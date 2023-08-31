import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewsFormComponent } from './components/news-form/news-form.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { NewsDisplayComponent } from './components/news-display/news-display.component';

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: "news/:tag-name", component: NewsDisplayComponent },
  { path: 'new-post', component: NewsFormComponent },
  // { path: '', redirectTo: 'landing', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

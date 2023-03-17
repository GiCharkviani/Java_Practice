import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthGuard} from "./guards/auth.guard";
import {ReverseAuthGuard} from "./guards/reverseAuth.guard";

const routes: Routes = [
  {path: "", pathMatch: "full",
    loadComponent: () => import('./pages/todo/todo.component').then(mod => mod.TodoComponent),
    canMatch: [AuthGuard]},
  {path: "login",
    loadComponent: () => import('./pages/login/login.component').then(mod => mod.LoginComponent),
    canMatch: [ReverseAuthGuard]},
  {path: "register",
    loadComponent: () => import('./pages/register/register.component').then(mod => mod.RegisterComponent),
    canMatch: [ReverseAuthGuard]},
  {path: "user",
    loadComponent: () => import('./pages/user/user.component').then(mod => mod.UserComponent),
    canMatch: [AuthGuard]},
  {path: "**", redirectTo: "" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import {Component} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Observable, tap} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'todoList';
  todos$: Observable<any> = this.http.get('http://localhost:8080/api/todo').pipe(tap(data => console.log(data, 'TODOS')));

  public loginForm = this.formBuilder.group({
    email: [],
    password: []
  })

  public registerForm = this.formBuilder.group({
    firstname: [],
    lastname: [],
    email: [],
    password: [],
    image: [],
  })

  public todoForm = this.formBuilder.group({
    id: [null],
    whatTodo: [],
    whenTodo: [],
    isCompleted: [true]
  })

  public imageHere = ''

  constructor(private readonly http: HttpClient, private readonly formBuilder: FormBuilder) {
    this.userData()
  }

  public login() {
    this.http.post('http://localhost:8080/auth/login', this.loginForm.getRawValue()).subscribe((data: any) => {
      localStorage.setItem('authToken', JSON.stringify(data.token))
      console.log(data, 'LOGGED IN')
    })
  }

  public register() {
    const formData = new FormData();
    const entries = Object.entries(this.registerForm.getRawValue())
    entries.forEach(([key, value]: [string, any]) => formData.append(key, value || ''))
    this.http.post('http://localhost:8080/auth/register', formData).subscribe((data: any) => {
      localStorage.setItem('authToken', JSON.stringify(data.token))
      console.log(data, 'REGISTERED')
    })
  }

  public addTodo() {
    this.http.post('http://localhost:8080/api/todo', this.todoForm.getRawValue()).subscribe(data => {
      this.todos$ = this.http.get('http://localhost:8080/api/todo')
      console.log(data, 'DOTO ADDED')
    });
  }

  public deleteTodo(id: number) {
    this.http.delete('http://localhost:8080/api/todo/' + id).subscribe(data => {
      console.log(data, 'DOTO DELETED')
    });
  }

  public editTodo(id: number) {
    this.http.put('http://localhost:8080/api/todo', {id, whatTodo: 'Edited', whenTodo: new Date()}).subscribe(data => {
      console.log(data, 'DOTO EDITED')
    });
  }

  public onFileSelected(event: any) {
    console.log(event.target.files)
    this.registerForm.get('image')?.setValue(event.target.files[0]);
  }

  public userData(): void {
     this.http.get('http://localhost:8080/user').pipe(tap((data: any) => {
       this.imageHere = data.image
       console.log(data, 'USER DATA')
     })).subscribe()
  }

  public logout(): void {
    this.http.post('http://localhost:8080/auth/logout', {}).subscribe((data) => console.log(data, 'LOGOUT'))
  }
}

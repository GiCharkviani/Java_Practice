import {Component} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
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
  editing: boolean = false;

  public loginForm = this.formBuilder.group({
    email: ['', Validators.required],
    password: ['', Validators.required]
  })

  public registerForm = this.formBuilder.group({
    firstname: ['', Validators.required],
    lastname: ['', Validators.required],
    email: ['', Validators.required],
    password: ['', Validators.required],
    image: ['', Validators.required],
  })

  public userEditForm = this.formBuilder.group({
    firstname: ['',],
    lastname: [''],
    email: [''],
    password: [''],
    image: [''],
  })

  public todoForm = this.formBuilder.group({
    id: [null],
    whatTodo: [],
    whenTodo: [],
    isCompleted: [true]
  })

  public userInfo: any = {};

  constructor(private readonly http: HttpClient, private readonly formBuilder: FormBuilder) {
    this.userData()
  }

  public login() {
    this.http.post('http://localhost:8080/auth/login', this.loginForm.getRawValue()).subscribe((data: any) => {
      localStorage.setItem('authToken', JSON.stringify(data.token))
      console.log(data, 'LOGGED IN')
    })
  }

  public register(editing = false) {
    if (editing) {
      this.http.put('http://localhost:8080/user', this.userEditForm.getRawValue()).subscribe((data: any) => {
        console.log(data, 'EDITED')
      })
    } else {
      this.http.post('http://localhost:8080/auth/register', this.registerForm.getRawValue()).subscribe((data: any) => {
        localStorage.setItem('authToken', JSON.stringify(data.token))
        console.log(data, 'REGISTERED')
      })
    }

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

  public onFileSelected(event: any, editing = false) {
    const imageObject = event.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(imageObject);
    reader.onload = () => {
      const imageToUpload = {
        name: imageObject.name,
        type: imageObject.type,
        image: reader.result
      }
      console.log(imageToUpload)
      // @ts-ignore
      editing ? this.userEditForm.get('image')?.setValue(imageToUpload) : this.registerForm.get('image')?.setValue(imageToUpload);
    }
  }

  public userData(): void {
     this.http.get('http://localhost:8080/user').pipe(tap((data: any) => {
       this.userInfo = data
       console.log(data, 'USER DATA')
     })).subscribe()
  }

  public edit() {
    this.editing = !this.editing;

    if(this.editing) {
      this.userEditForm.patchValue({
        firstname: this.userInfo.firstname,
        lastname: this.userInfo.lastname,
        email: this.userInfo.email,
      })
    }
  }

  public logout(): void {
    this.http.post('http://localhost:8080/auth/logout', {}).subscribe((data) => {
      localStorage.removeItem('authToken');
      console.log('LOG OUT')
    })
  }
}

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
  todos$: Observable<any> = this.http.get('http://localhost:8080/api/todo');

  public myForm = this.formBuilder.group({
    firstname: [],
    lastname: [],
    email: [],
    password: [],
    image: [],
  })

  public imageHere = ''

  constructor(private readonly http: HttpClient, private readonly formBuilder: FormBuilder) {
    this.userData()
  }

  public submit() {
    const formData = new FormData();
    const entries = Object.entries(this.myForm.getRawValue())
    entries.forEach(([key, value]: [string, any]) => formData.append(key, value || ''))
    this.http.post('http://localhost:8080/auth/register', formData).subscribe((data) => console.log(data))
  }

  onFileSelected(event: any) {
    console.log(event.target.files)
    this.myForm.get('image')?.setValue(event.target.files[0]);
  }

  public userData(): void {
     this.http.get('http://localhost:8080/user').pipe(tap((data: any) => {
       this.imageHere = data.image
       console.log(data)
     })).subscribe()
  }
}

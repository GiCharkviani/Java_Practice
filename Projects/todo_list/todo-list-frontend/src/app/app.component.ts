import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'todoList';
  public myForm = this.formBuilder.group({
    firstname: [],
    lastname: [],
    email: [],
    password: [],
    image: [],
  })

  constructor(private readonly http: HttpClient, private readonly formBuilder: FormBuilder) {
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
}

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
    for(let [key, value] of Object.entries(this.myForm.value)) {
      formData.append(key, value);
    }
    console.log(this.myForm.value.image, 'IMAGE')
    this.http.post('http://localhost:8080/auth/register', formData).subscribe((data) => console.log(data))
  }

  onFileSelected(event: any) {
    console.log(event.target.files)
    this.myForm.patchValue({
      image: event.target.files[0]
    });
  }
}

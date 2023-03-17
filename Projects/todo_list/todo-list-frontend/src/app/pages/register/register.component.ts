import {ChangeDetectionStrategy, Component, OnDestroy} from '@angular/core';
import {RouterModule} from "@angular/router";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../../services/user/user.service";
import {ImageUser} from "../../services/user/interfaces/image-user";
import {RegisterUser} from "../../services/user/interfaces/register-user";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [RouterModule, ReactiveFormsModule],
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegisterComponent implements OnDestroy {
  private subscription!: Subscription;

  public readonly registrationForm = this.fb.group({
    email: ['', [Validators.required]],
    password: ['', [Validators.required]],
    firstname: ['', [Validators.required]],
    lastname: ['', [Validators.required]],
    image: this.fb.group({
      name: [''],
      type: [''],
      image: ['']
    }),
  })

  constructor(private readonly fb: FormBuilder, private readonly userService: UserService) { }

  public register(): void {
    this.subscription = this.userService.register(this.registrationForm.getRawValue() as RegisterUser)
        .subscribe()
  }

  public imageSelected(data: Event): void {
    const filesList: FileList | null = (data.target as HTMLInputElement).files;
    const file = filesList?.item(0);
    file && this.convertToBase64((file as File))
        .then(imageBase64 => this.registrationForm.get('image')?.setValue({
          name: file.name,
          type: file.type,
          image: imageBase64
        }));
  }

  private convertToBase64(file: File): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = () => {
        const base64image = reader.result;
        base64image && resolve((base64image.toString() as string));
      }
      reader.readAsDataURL(file);
    })
  }

  ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

}

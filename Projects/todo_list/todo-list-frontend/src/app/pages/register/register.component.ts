import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy} from '@angular/core';
import {Router, RouterModule} from "@angular/router";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../../services/user/user.service";
import {catchError, Subscription} from "rxjs";
import {DropImageDirective} from "../../directives/dropImage.directive";
import {RegisterUser} from "../../services/user/interfaces/register-user";
import {HttpErrorResponse} from "@angular/common/http";
import {ErrorComponent} from "../../components/error/error.component";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [RouterModule, ReactiveFormsModule, DropImageDirective, ErrorComponent],
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegisterComponent implements OnDestroy {
  public errorMessages: string[] = [];
  public loading: boolean = false;
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

  constructor(private readonly fb: FormBuilder,
              private readonly userService: UserService,
              private readonly cd: ChangeDetectorRef,
              private readonly router: Router) { }

  public register(): void {
    this.subscription = this.userService.register(this.registrationForm.getRawValue() as RegisterUser)
        .pipe(
            catchError((httpErrorResponse: HttpErrorResponse) => {
              this.cd.markForCheck();
              const {error} = httpErrorResponse;
              this.errorMessages = [];
              httpErrorResponse.status === 400 ?
                  this.errorMessages.push(...error.messages)
                  :
                  this.errorMessages.push(error.message);

              this.loading = false;
              throw httpErrorResponse;
            })
        )
        .subscribe(this.handleSuccess.bind(this))
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

    private handleSuccess(): void {
        this.router.navigate(['']).then(() => {
            this.registrationForm.reset();
            this.errorMessages = [];
            this.loading = false;
        });
    }

}

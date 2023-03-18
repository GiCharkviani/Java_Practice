import {ChangeDetectionStrategy, Component, OnDestroy} from '@angular/core';
import {RouterModule} from "@angular/router";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../../services/user/user.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [RouterModule, ReactiveFormsModule],
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginComponent implements OnDestroy {
  private subscription!: Subscription;

  public readonly loginForm = this.fb.group({
    email: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  constructor(private readonly fb: FormBuilder, private readonly userService: UserService) { }

  public login(): void {
    this.subscription = this.userService.login(this.loginForm.getRawValue())
        .subscribe()
  }

  ngOnDestroy() {
    this.subscription?.unsubscribe();
  }
}

import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy} from '@angular/core';
import {Router, RouterModule} from "@angular/router";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../../services/user/user.service";
import {catchError, Subscription} from "rxjs";
import {CommonModule} from "@angular/common";
import {HttpErrorResponse} from "@angular/common/http";
import {ErrorComponent} from "../../components/error/error.component";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
    imports: [RouterModule, ReactiveFormsModule, CommonModule, ErrorComponent],
    standalone: true,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginComponent implements OnDestroy {
    public errorMessages: string[] = [];
    public loading: boolean = false;
    private subscription!: Subscription;

    public readonly loginForm = this.fb.group({
        email: ['', [Validators.required]],
        password: ['', [Validators.required]]
    });

    constructor(private readonly fb: FormBuilder,
                private readonly userService: UserService,
                private readonly cd: ChangeDetectorRef,
                private readonly router: Router) {
    }

    public login(): void {
        this.cd.markForCheck();
        this.loading = true;
        this.subscription = this.userService.login(this.loginForm.getRawValue())
            .pipe(
                catchError((httpErrorResponse: HttpErrorResponse) => {
                    this.cd.markForCheck();
                    const {error} = httpErrorResponse;
                    this.errorMessages = [];
                    if(httpErrorResponse.status === 500) {
                        this.errorMessages.push(httpErrorResponse.message);
                    } else
                        this.errorMessages.push(...error.messages);

                    this.loading = false;
                    throw httpErrorResponse;
                })
            )
            .subscribe(this.handleSuccess.bind(this))
    }

    ngOnDestroy() {
        this.subscription?.unsubscribe();
    }

    private handleSuccess(): void {
        this.router.navigate(['']).then(() => {
            this.loginForm.reset();
            this.errorMessages = [];
            this.loading = false;
        });
    }
}

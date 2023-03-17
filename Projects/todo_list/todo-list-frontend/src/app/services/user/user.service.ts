import {Injectable} from "@angular/core";
import jwtDecode from "jwt-decode";
import {JWT} from "./interfaces/JWT";
import {Observable, tap} from "rxjs";
import {TokenResponse} from "./interfaces/token-response";
import {HttpService} from "../http/http.service";
import {LoginUser} from "./interfaces/login-user";
import {RegisterUser} from "./interfaces/register-user";
import {Token} from "@angular/compiler";

@Injectable({providedIn: 'root'})
export class UserService {
    private readonly JWT_NAME: string = 'TodoAuthJWT';

    constructor(private readonly httpService: HttpService) {
    }

    public login(loginUser: LoginUser): Observable<TokenResponse> {
        return this.httpService.login(loginUser)
            .pipe(tap(({token}: TokenResponse) => localStorage.setItem(this.JWT_NAME, JSON.stringify(token))));
    }

    public register(registerUser: RegisterUser): Observable<TokenResponse> {
        return this.httpService.register(registerUser)
            .pipe(tap(({token}: TokenResponse) => localStorage.setItem(this.JWT_NAME, JSON.stringify(token))));;
    }

    public logout(): Observable<any> {
        return this.httpService.logout()
            .pipe(tap(() => this.JWT && localStorage.removeItem(this.JWT_NAME)));
    }

    public isLoggedIn(): boolean {
        const decodedJWT: JWT | null = this.decodeJWT(this.JWT);
        return !!decodedJWT && !this.isSessionExpired(decodedJWT?.exp);
    }

    private decodeJWT(jwt: string | null): JWT | null {
        try {
            return jwt ? jwtDecode(jwt) : null;
        } catch (error: any) {
            console.error("Couldn't decode JWT: ", error)
            return null;
        }
    }

    public get JWT(): string | null {
        return localStorage.getItem(this.JWT_NAME);
    }

    private isSessionExpired(exp: number | undefined) {
        return exp && exp < new Date().getTime();
    }
}

import {Injectable} from "@angular/core";
import {LoginUser} from "../user/interfaces/login-user";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TokenResponse} from "../user/interfaces/token-response";
import {environment} from "../../../environments/environment";
import {RegisterUser} from "../user/interfaces/register-user";

@Injectable({providedIn: 'root'})
export class HttpService {

    constructor(private readonly http: HttpClient) {
    }

    public login(loginUser: LoginUser): Observable<TokenResponse> {
        return this.http.post<TokenResponse>(`${environment.baseUrl}/auth/login`, loginUser);
    }

    public register(registerUser: RegisterUser): Observable<TokenResponse> {
        return this.http.post<TokenResponse>(`${environment.baseUrl}/auth/register`, registerUser);
    }

    public logout(): Observable<any> {
        return this.http.post(`${environment.baseUrl}/auth/logout`, {});
    }
}

import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserService} from "../services/user/user.service";
import {Injectable} from "@angular/core";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private readonly userService: UserService) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const JWT = this.userService.JWT;
        const modifiedRequest = JWT ?
            req.clone({ headers: req.headers.set('Authorization', `Bearer ${JWT}`)}) :
            req;

        return next.handle(modifiedRequest);
    }

}

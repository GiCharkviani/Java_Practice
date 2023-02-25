import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaS5jaGFya3ZpMUBnbWFpbC5jb20iLCJpYXQiOjE2NzczNTMzNjQsImV4cCI6MjQxNTM4ODg0NDI3ODA4MH0.HIyg2eYgy7ynAiKZNbVjtoEl-l3FAIEL3O3w8scrY9Q';

export class CustomInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const modifiedRequest = req.clone({ headers: req.headers.set('Authorization', 'Bearer ' + token)});
        return next.handle(modifiedRequest);
    }

}

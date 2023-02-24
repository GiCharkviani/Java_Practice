import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaS5jaGFya3ZpMkBnbWFpbC5jb20iLCJpYXQiOjE2NzcyNTk5MzIsImV4cCI6MjQxNTI1NDMwMjc1NjgwMH0.fre647ixNa23RuSEip1NM_OEu6mviUf3eMVWoMoX_Ns';

export class CustomInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const modifiedRequest = req.clone({ headers: req.headers.set('Authorization', 'Bearer ' + token)});
        return next.handle(modifiedRequest);
    }

}

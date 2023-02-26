import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaS5jaGFya3ZpMkBnbWFpbC5jb20iLCJpYXQiOjE2Nzc0MzM3NzgsImV4cCI6MjQxNTUwNDY0MDgwMDk2MH0.OFHWDcg2xLvahGiECt0ogXzLBXaw2FA-1pmUk5IEK-4';

export class CustomInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const modifiedRequest = req.clone({ headers: req.headers.set('Authorization', 'Bearer ' + token)});
        return next.handle(modifiedRequest);
    }

}

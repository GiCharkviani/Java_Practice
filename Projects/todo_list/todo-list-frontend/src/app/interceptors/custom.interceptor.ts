import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaS5jaGFya3ZpMkBnbWFpbC5jb20iLCJpYXQiOjE2NzczNTM4MzUsImV4cCI6MjQxNTM4OTUyMzQxOTUyMH0.MYARwOyB5fZwMuHBREXvigyAOvf_qBpGeihVVJy5zak';

export class CustomInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const modifiedRequest = req.clone({ headers: req.headers.set('Authorization', 'Bearer ' + token)});
        return next.handle(modifiedRequest);
    }

}

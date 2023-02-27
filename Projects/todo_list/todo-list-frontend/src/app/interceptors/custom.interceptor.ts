import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";


export class CustomInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = localStorage.getItem('authToken');
        const modifiedRequest = req.clone({ headers: req.headers.set('Authorization', `Bearer ${token ? JSON.parse(token) : ''}`)});
        return next.handle(req.url.includes('auth') && !req.url.includes('logout')? req : modifiedRequest);
    }

}

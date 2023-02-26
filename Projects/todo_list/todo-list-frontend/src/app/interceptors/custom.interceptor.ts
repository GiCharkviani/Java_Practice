import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";


export class CustomInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const modifiedRequest = req.clone({ headers: req.headers.set('Authorization', `Bearer ${JSON.parse(localStorage.getItem('authToken') || '')}`)});
        return next.handle(req.url.includes('auth') && !req.url.includes('logout')? req : modifiedRequest);
    }

}

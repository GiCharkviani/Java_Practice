import {
    CanMatch,
    Route, Router,
    UrlSegment,
    UrlTree
} from "@angular/router";
import {Observable} from "rxjs";
import {UserService} from "../services/user/user.service";
import {Injectable} from "@angular/core";

@Injectable({providedIn: "root"})
export class AuthGuard implements CanMatch {
    constructor(private readonly userService: UserService, private readonly router: Router) {
    }
    canMatch(route: Route, segments: UrlSegment[]): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        return this.userService.isLoggedIn() || this.router.createUrlTree(['/login']);
    }

}

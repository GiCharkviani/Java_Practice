import {CanMatch, Route, UrlSegment, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {UserService} from "../services/user/user.service";
import {Injectable} from "@angular/core";

@Injectable({providedIn: 'root'})
export class ReverseAuthGuard implements CanMatch {
    constructor(private readonly userService: UserService) {
    }
    canMatch(route: Route, segments: UrlSegment[]): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        return !this.userService.isLoggedIn();
    }
}

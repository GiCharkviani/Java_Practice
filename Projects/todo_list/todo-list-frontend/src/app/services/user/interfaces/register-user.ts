import {ImageUser} from "./image-user";

export interface RegisterUser {
    email: string;
    password: string;
    firstname: string;
    lastname: string;
    image: ImageUser | null;
}

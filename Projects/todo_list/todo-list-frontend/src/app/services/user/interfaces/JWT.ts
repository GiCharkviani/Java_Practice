export interface JWT {
    id: string;
    sub: string;
    email: string;
    firstname: string;
    lastname: string;
    exp: number;
    iat: number;
}

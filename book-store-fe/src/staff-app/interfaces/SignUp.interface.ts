
export interface SignUpReq {
    staffFullName: string;
    staffUsername: string;
    staffPassword: string;
    staffEmail: string;
    role: string;
}

export interface SignUpRes {
    token: string;
    refreshToken: string;
    userName: string;
    roles: string[];
}
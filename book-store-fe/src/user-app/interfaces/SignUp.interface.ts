export interface SignUpReq {
    userFullName: string;
    username: string;
    password: string;
    userEmail: string;
}

export interface SignUpRes {
    token: string;
    refreshToken: string;
    userName: string;
}
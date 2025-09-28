export interface SignInReq {
    username: string;
    password: string;
}

export interface SignInRes {
    token: string;
    refreshToken: string;
    staffId: number;
    staffUserName: string;
    staffRoles: string[];
    type: string;
}

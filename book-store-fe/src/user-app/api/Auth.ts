import axios from "axios";
import { REST_API_BASE_URL_USER_AUTH } from "../../config";

export const api = axios.create({
    baseURL: REST_API_BASE_URL_USER_AUTH,
    withCredentials: true
});

export interface SignInReq {
    username: string;
    password: string;
}

export interface SignInRes {
    token: string;
    refreshToken: string;
    userId: number;
    userName: string;
    type: string;
}

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

export async function signInAuthUser(data: SignInReq): Promise<{ data: SignInRes | null }> {
    try{
        console.log("username : "+data.username);
        console.log("password : "+data.password);
        const response = await axios.post<SignInRes>(`${REST_API_BASE_URL_USER_AUTH}/signin`, data);
        console.log(response);
        return { data: response.data };
    }catch (error){
        console.error("Login failed:", error);
        return { data: null };
    }
}


export async function signUpAuthUser(data: SignUpReq): Promise<{ data: SignUpRes | null }> {
    try{
        const response = await axios.post<SignUpRes>(`${REST_API_BASE_URL_USER_AUTH}/signup`, data);
        console.log(response);
        return { data: response.data };
    }catch (error){
        console.error("Login failed:", error);
        return { data: null };
    }
}





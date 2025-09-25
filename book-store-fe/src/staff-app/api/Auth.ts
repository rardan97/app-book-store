import axios from "axios";
import { REST_API_BASE_URL_STAFF_AUTH } from "../../config";
import type { Role } from "../interfaces/Role.interface";

export const api = axios.create({
    baseURL: REST_API_BASE_URL_STAFF_AUTH,
    withCredentials: true
});

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

export async function signInAuth(data: SignInReq): Promise<{ data: SignInRes | null }> {
    try{
        const response = await axios.post<SignInRes>(`${REST_API_BASE_URL_STAFF_AUTH}/signin`, data);
        console.log(response);
        return { data: response.data };
    }catch (error){
        console.error("Login failed:", error);
        return { data: null };
    }
}


export async function signUpAuth(data: SignUpReq): Promise<{ data: SignUpRes | null }> {
    try{
        const response = await axios.post<SignUpRes>(`${REST_API_BASE_URL_STAFF_AUTH}/signup`, data);
        console.log(response);
        return { data: response.data };
    }catch (error){
        console.error("Login failed:", error);
        return { data: null };
    }
}

export async function getListRoleAuth() : Promise<Role[]>{
    try{
        const response = await api.get<Role[]>(`${REST_API_BASE_URL_STAFF_AUTH}/getRoleListAll`, {
            headers: {
                "Content-Type": "application/json"
            },
        });
        return response.data;
    }catch(error){
        console.error("Error during user fetch:", error);
        throw new Error("Failed to fetch users");
    }
}



import axios from "axios";
import { REST_API_BASE_URL_STAFF_AUTH } from "../../config";
import type { Role } from "../interfaces/Role.interface";
import type { SignInReq, SignInRes } from "../interfaces/SignIn.interface";
import type { ApiResponse } from "../interfaces/ApiResponse.interface";
import type { SignUpReq, SignUpRes } from "../interfaces/SignUp.interface";

export const api = axios.create({
    baseURL: REST_API_BASE_URL_STAFF_AUTH,
    withCredentials: true
});


export async function signInAuth(data: SignInReq): Promise<ApiResponse<SignInRes> | null > {
    try{
        const response = await axios.post<ApiResponse<SignInRes>>(`/signin`, data);
        console.log(response);
        return response.data;
    }catch (error){
        console.error("Login failed:", error);
        return null;
    }
}


export async function signUpAuth(data: SignUpReq): Promise<ApiResponse<SignUpRes> | null > {
    try{
        const response = await axios.post<ApiResponse<SignUpRes>>(`/signup`, data);
        console.log(response);
        return response.data;
    }catch (error){
        console.error("Login failed:", error);
        return null ;
    }
}

export async function getListRoleAuth() : Promise<ApiResponse<Role[]>>{
    try{
        const response = await api.get<ApiResponse<Role[]>>(`/getRoleListAll`, {
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



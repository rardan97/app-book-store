import axios from "axios";
import { REST_API_BASE_URL_USER_AUTH } from "../../config";
import type { SignInReq, SignInRes } from "../interfaces/SignIn.interface";
import type { ApiResponse } from "../interfaces/ApiResponse.interface";
import type { SignUpReq, SignUpRes } from "../interfaces/SignUp.interface";

export const api = axios.create({
    baseURL: REST_API_BASE_URL_USER_AUTH,
    withCredentials: true
});



export async function signInAuthUser(data: SignInReq): Promise<ApiResponse<SignInRes> | null> {
    try{
        console.log("username : "+data.username);
        console.log("password : "+data.password);
        const response = await axios.post<ApiResponse<SignInRes>>(`/signin`, data);
        console.log(response);
        return response.data;
    }catch (error){
        console.error("Login failed:", error);
        return null;
    }
}


export async function signUpAuthUser(data: SignUpReq): Promise<ApiResponse<SignUpRes> | null> {
    try{
        const response = await axios.post<ApiResponse<SignUpRes>>(`/signup`, data);
        console.log(response);
        return response.data;
    }catch (error){
        console.error("Login failed:", error);
        return null;
    }
}





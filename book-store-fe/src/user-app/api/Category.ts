import axios from "axios";
import { REST_API_BASE_URL_USER_PUBLIC } from "../../config";
import type { Category } from "../interfaces/CategoryPublic.interface";
import type { ApiResponse } from "../interfaces/ApiResponse.interface";


export const api = axios.create({
    baseURL: REST_API_BASE_URL_USER_PUBLIC,
    withCredentials: true
});

export async function getPublicListCategory() : Promise<ApiResponse<Category[]>>{
    try{
        const response = await api.get<ApiResponse<Category[]>>(`/category/getCategoryListAll`, {
            headers: {
                "Content-Type": "application/json"
            },
        });
        console.log("checckk");
        console.log(response);
        return response.data;
    }catch(error){
        console.error("Error during user fetch:", error);
        throw new Error("Failed to fetch users");
    }
}

export async function getPublicCategoryValueById(id : number) : Promise<ApiResponse<Category>>{
    try{
        const response = await api.get<ApiResponse<Category>>(`/category/getCategoryFindById/${id}`, {
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

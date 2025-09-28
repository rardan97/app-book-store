import axios from "axios";
import { REST_API_BASE_URL_STAFF } from "../../config";
import type { Category, CategoryDto } from "../interfaces/Category.interface";
import type { ApiResponse } from "../interfaces/ApiResponse.interface";

export const api = axios.create({
    baseURL: REST_API_BASE_URL_STAFF,
    withCredentials: true
});

export async function getListCategories(token: string) : Promise<ApiResponse<Category[]>>{
    console.log("Data Token : "+token);
    try{
        const response = await api.get<ApiResponse<Category[]>>(`/category/getCategoryListAll`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    }catch(error){
        console.error("Error during user fetch:", error);
        throw new Error("Failed to fetch users");
    }
}

export async function addCategories(token: string, data: CategoryDto) : Promise<ApiResponse<Category>>{
    try{
        const response = await api.post<ApiResponse<Category>>(`/category/addCategory`, data, {
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            }, 
        });
        return response.data;
    }catch(error){
        console.error("Error during user fetch:", error);
        throw new Error("Failed to fetch users");
    }
}

export async function editCategories(token: string, id : number, data: Category) : Promise<ApiResponse<Category>>{
    try{
        const response = await api.put<ApiResponse<Category>>(`/category/updateCategory/${id}`, data, {
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            }, 
        });
        return response.data;
    }catch(error){
        console.error("Error during user fetch:", error);
        throw new Error("Failed to fetch users");
    }
}


export async function getCategoryValueById(token: string, id : number) : Promise<ApiResponse<Category>>{
    try{
        const response = await api.get<ApiResponse<Category>>(`/category/getCategoryFindById/${id}`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    }catch(error){
        console.error("Error during user fetch:", error);
        throw new Error("Failed to fetch users");
    }
}

export async function delCategoryValueById(token: string, id : number) : Promise<ApiResponse<string>>{
    try{
        const response = await api.delete<ApiResponse<string>>(`/category/deleteCategoryById/${id}`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        });
        console.log("test delete");
        console.log(response);
        return response.data;
    }catch(error){
        console.error("Error during user fetch:", error);
        throw new Error("Failed to fetch users");
    }
}
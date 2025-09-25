import axios from "axios";
import { REST_API_BASE_URL_STAFF } from "../../config";
import type { Category, CategoryDto } from "../interfaces/Category.interface";

export const api = axios.create({
    baseURL: REST_API_BASE_URL_STAFF,
    withCredentials: true
});

export async function getListCategories(token: string) : Promise<Category[]>{
    console.log("Data Token : "+token);
    try{
        const response = await api.get<Category[]>(`${REST_API_BASE_URL_STAFF}/category/getCategoryListAll`, {
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

export async function addCategories(token: string, data: CategoryDto) : Promise<Category>{
    try{
        const response = await api.post<Category>(`${REST_API_BASE_URL_STAFF}/category/addCategory`, data, {
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

export async function editCategories(token: string, id : number, data: Category) : Promise<Category>{
    try{
        const response = await api.put<Category>(`${REST_API_BASE_URL_STAFF}/category/updateCategory/${id}`, data, {
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


export async function getCategoryValueById(token: string, id : number) : Promise<Category>{
    try{
        const response = await api.get<Category>(`${REST_API_BASE_URL_STAFF}/category/getCategoryFindById/${id}`, {
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

export async function delCategoryValueById(token: string, id : number) : Promise<string>{
    try{
        const response = await api.delete<string>(`${REST_API_BASE_URL_STAFF}/category/deleteCategoryById/${id}`, {
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
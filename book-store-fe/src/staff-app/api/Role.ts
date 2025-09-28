import axios from "axios";
import { REST_API_BASE_URL_STAFF } from "../../config";
import type { Role, RoleDto } from "../interfaces/Role.interface";
import type { ApiResponse } from "../interfaces/ApiResponse.interface";

export const api = axios.create({
    baseURL: REST_API_BASE_URL_STAFF,
    withCredentials: true
});

export async function getListRole(token: string) : Promise<ApiResponse<Role[]>>{
    try{
        const response = await api.get<ApiResponse<Role[]>>(`/role/getRoleListAll`, {
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

export async function addRole(token: string, data: RoleDto) : Promise<ApiResponse<Role>>{
    try{
        const response = await api.post<ApiResponse<Role>>(`/role/addRole`, data, {
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

export async function editRole(token: string, id : number, data: Role) : Promise<ApiResponse<Role>>{
    console.log("Check id : "+data.roleStaffId);
    console.log("Check name: "+data.roleStaffName);
    try{
        const response = await api.put<ApiResponse<Role>>(`/role/updateRole/${id}`, data, {
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


export async function getRoleValueById(token: string, id : number) : Promise<ApiResponse<Role>>{
    try{
        const response = await api.get<ApiResponse<Role>>(`/role/getRoleFindById/${id}`, {
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

export async function delRoleValueById(token: string, id : number) : Promise<ApiResponse<string>>{
    try{
        const response = await api.delete<ApiResponse<string>>(`/role/deleteRoleById/${id}`, {
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
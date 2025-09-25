import axios from "axios";
import { REST_API_BASE_URL_STAFF } from "../../config";
import type { UserManagement } from "../interfaces/UserManagement.interface";

export const api = axios.create({
    baseURL: REST_API_BASE_URL_STAFF,
    withCredentials: true
});

export async function getListUserManagement(token: string) : Promise<UserManagement[]>{
    try{
        const response = await api.get<UserManagement[]>(`${REST_API_BASE_URL_STAFF}/userManagement/getUserManagementListAll`, {
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

export async function getUserManagementValueById(token: string, id : number) : Promise<UserManagement>{
    try{
        const response = await api.get<UserManagement>(`${REST_API_BASE_URL_STAFF}/userManagement/getUserManagementFindById/${id}`, {
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

import axios from "axios";
import { REST_API_BASE_URL_USER } from "../../config";

export const api = axios.create({
    baseURL: REST_API_BASE_URL_USER,
    withCredentials: true
});

interface CartItem {
  bookId: number;
  bookTitle: string;
  price: string;
  bookImage: string;
  quantity: number;
}

export async function getCart(token: string, userId : number) : Promise<CartItem[]>{
    try{
        const response = await api.get<CartItem[]>(`${REST_API_BASE_URL_USER}/cartTransaction/getCart/${userId}`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            }, 
        });

        console.log(response.data);
        return response.data;
    }catch(error){
        console.error("Error during user fetch:", error);
        throw new Error("Failed to fetch users");
    }
}

export async function addCart(token: string, userId : number, data: CartItem) : Promise<CartItem>{
    console.log("token : "+token);
    console.log("bookImage : "+data.bookImage);
    try{
        const response = await api.post<CartItem>(`${REST_API_BASE_URL_USER}/cartTransaction/addCart/${userId}`, data, {
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


export async function removeCart(token: string, userId : number, bookId : number) : Promise<string>{
    try{
        const response = await api.delete<string>(`${REST_API_BASE_URL_USER}/cartTransaction/removeCart/${userId}/${bookId}`, {
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

export async function clearCart(token: string, userId : number) : Promise<CartItem>{
    try{
        const response = await api.post<CartItem>(`${REST_API_BASE_URL_USER}/cartTransaction/clearCart/${userId}`, null, {
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


export async function updateCartQty(token: string, userId : number, bookId : number, quantity : number) : Promise<CartItem>{
    try{
        const response = await api.post<CartItem>(`${REST_API_BASE_URL_USER}/cartTransaction/updateCartQty/${userId}/${bookId}/${quantity}`, null, {
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


// export async function getCategoryValueById(token: string, id : number) : Promise<Category>{
//     try{
//         const response = await api.get<Category>(`${REST_API_BASE_URL_USER}/category/getCategoryFindById/${id}`, {
//             headers: {
//                 "Content-Type": "application/json",
//                 Authorization: `Bearer ${token}`,
//             },
//         });
//         return response.data;
//     }catch(error){
//         console.error("Error during user fetch:", error);
//         throw new Error("Failed to fetch users");
//     }
// }


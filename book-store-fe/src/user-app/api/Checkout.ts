import axios from "axios";
import { REST_API_BASE_URL_USER_PUBLIC } from "../../config";
import type { CheckoutPayload } from "../interfaces/Checkout.interface";
import type { TransactionData } from "../interfaces/Transaction.interface";
// import type { BooksPublic } from "../interfaces/BooksPublic.interface";

export const api = axios.create({
    baseURL: REST_API_BASE_URL_USER_PUBLIC,
    withCredentials: true
});




export async function checkoutTransaction(data: CheckoutPayload) : Promise<TransactionData>{
    // console.log("token : "+token);
    console.log("dataProductTransaksi : "+data.dataProductTransaksi);
    try{
        const response = await api.post<TransactionData>(`${REST_API_BASE_URL_USER_PUBLIC}/orderTransaction/checkoutTransaction`, data, {
            headers: {
                'Accept': 'application/json',
                "Content-Type": "application/json",
                // Authorization: `Bearer ${token}`,
            }, 
        });

        console.log("Data Res check : "+response.data);
        return response.data;
    }catch(error){
        console.error("Error during user fetch:", error);
        throw new Error("Failed to fetch users");
    }
}

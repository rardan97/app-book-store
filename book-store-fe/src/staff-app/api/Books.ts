import axios from "axios";
import { REST_API_BASE_URL_STAFF } from "../../config";
import type { AddBooksDto, Books, EditBookDto } from "../interfaces/Book.interface";

export const api = axios.create({
    baseURL: REST_API_BASE_URL_STAFF,
    withCredentials: true
});

export async function getListBooks(token: string) : Promise<Books[]>{
    try{
        const response = await api.get<Books[]>(`${REST_API_BASE_URL_STAFF}/books/getBooksListAll`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
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

export async function addBooks(token: string, data: AddBooksDto) : Promise<Books>{
    console.log(data.categoryId);
    const bookPayLoad = {
        bookTitle: data.bookTitle,
        author: data.author,
        description: data.description,
        price: data.price,
        stock: data.stock,
        categoryId: data.categoryId
    }

    const formData = new FormData();
    formData.append("book", new Blob([JSON.stringify(bookPayLoad)], {
        type: "application/json"
    }));

    if (data.bookImage) {
      formData.append("bookImage", data.bookImage);
    }

    try{
        const response = await api.post<Books>(`${REST_API_BASE_URL_STAFF}/books/addBooks`, formData, {
            headers: {
                Authorization: `Bearer ${token}`,
            }, 
        });
        return response.data;
    }catch(error){
        console.error("Error during user fetch:", error);
        throw new Error("Failed to fetch users");
    }
}

export async function editBooks(token: string, id : number, data: EditBookDto) : Promise<Books>{
    const bookPayLoad = {
        bookTitle: data.bookTitle,
        author: data.author,
        description: data.description,
        price: data.price,
        stock: data.stock,
        categoryId: data.categoryId
    }

    const formData = new FormData();
    formData.append("book", new Blob([JSON.stringify(bookPayLoad)], {
        type: "application/json"
    }));

    if (data.bookImage) {
      formData.append("bookImage", data.bookImage);
    }

    try{
        const response = await api.put<Books>(`${REST_API_BASE_URL_STAFF}/books/updateBooks/${id}`, formData, {
            headers: {
                Authorization: `Bearer ${token}`,
            }, 
        });
        return response.data;
    }catch(error){
        console.error("Error during user fetch:", error);
        throw new Error("Failed to fetch users");
    }
}

export async function getBookValueById(token: string, id : number) : Promise<Books>{
    try{
        const response = await api.get<Books>(`${REST_API_BASE_URL_STAFF}/books/getBooksFindById/${id}`, {
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


export async function getLoadImageBook(token: string, filename : File | string) : Promise<Blob>{
    try{
        const response = await api.get<Blob>(`${REST_API_BASE_URL_STAFF}/books/images/${filename}`, {
            responseType:'blob',
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    }catch(error){
        console.error("Error during user fetch:", error);
        throw new Error("Failed to fetch users");
    }
}

export async function delBookValueById(token: string, id : number) : Promise<string>{
    try{
        const response = await api.delete<string>(`${REST_API_BASE_URL_STAFF}/books/deleteBooksById/${id}`, {
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
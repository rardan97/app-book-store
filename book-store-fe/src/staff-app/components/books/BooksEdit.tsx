import { useCallback, useEffect, useRef, useState } from "react";
import { useModal } from "../../hooks/useModal";
import Input from "../form/input/InputField";
import Label from "../form/Label";
import Button from "../ui/button/Button";
import { Modal } from "../ui/modal";
import Alert from "../ui/alert/Alert";
import { editBooks, getBookValueById, getLoadImageBook } from "../../api/Books";
import Select from "../form/Select";
import { getListCategories } from "../../api/Category";
import type { EditBookDto } from "../../interfaces/Book.interface";
import FileInput from "../form/input/FileInput";

type OptionCategory = {
  value: string;
  label: string;
};


type BooksEditProps = {
    onSuccess: () => void;
    idBook: number;
};

interface Errors {
        bookTitle: string;
        author: string;
        description: string;
        price: string;
        stock: string;
        bookImage:string;
        categoryId: string;
    }

export default function BooksEdit({onSuccess, idBook} : BooksEditProps) {

    const { isOpen, openModal, closeModal } = useModal();
    const [bookId, setBookId] = useState<number>(0);
    const [bookTitle, setBookTitle] = useState<string>("");
    const [author, setAuthor] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [price, setPrice] = useState<string>("");
    const [stock, setStock] = useState<string>("");
    const [bookImage, setBookImage] = useState<File | string>("");
    const [categoryId, setCategoryId] = useState<string>("");
    const [optionsCategory, setOptionsCategory] = useState<OptionCategory[]>([]);
    const [previewUrl, setPreviewUrl] = useState<string>("");

    const [errorsAll, setErrorsAll] = useState<string>("");
    const hasFetched = useRef(false);
    
    

    const [errors, setErrors] = useState<Errors>({
        bookTitle: '',
        author: '',
        description: '',
        price: '',
        stock: '',
        bookImage:'',
        categoryId: '',
    });

    const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];
        if (file && file.type.startsWith('image/')) {
            setBookImage(file);
            setPreviewUrl(URL.createObjectURL(file));
        } else {
            setBookImage("");
            setPreviewUrl("");
            setErrors({ ...errors, bookImage: 'Please select a valid image file.' });
        }
    };

    const getListCategory = useCallback(async (): Promise<void> => {
        const token = localStorage.getItem("staff_accessToken");
        console.log(token);
        if (!token){
            return;
        }
        try {
            const response = await getListCategories(token);   
            
            const mapped = response.map((cat) => ({
                value: cat.categoryId.toString(),
                label: cat.categoryName,
            }));
            setOptionsCategory(mapped);
            console.log("Success processing data");
        } catch (error) {
            console.log("Failed processing data", error);
            throw error;
        }
    }, []);
    
    useEffect(() => {
        console.log(hasFetched);
        if (!hasFetched.current) {
            getListCategory();
            hasFetched.current = true; // Cegah request kedua
        }
    }, [getListCategory]);

 
    function validateForm(): boolean{
        console.log("proccess validation");
        let valid = true;
        const errorsCopy = {... errors}
        if(bookTitle.trim()){
            errorsCopy.bookTitle = '';
        }else{
            errorsCopy.bookTitle = 'bookTitle is required';
            valid = false;
        }
    
        if(author.trim()){
            errorsCopy.author = '';
        }else{
            errorsCopy.author = 'author is required';
            valid = false;
        }
        if(description.trim()){
            errorsCopy.description = '';
        }else{
            errorsCopy.description = 'description is required';
            valid = false;
        }
        if(price.trim()){
            errorsCopy.price = '';
        }else{
            errorsCopy.price = 'price is required';
            valid = false;
        }

        if(stock.trim()){
            errorsCopy.stock = '';
        }else{
            errorsCopy.stock = 'stock is required';
            valid = false;
        }

        if(stock.trim()){
            errorsCopy.bookImage = '';
        }else{
            errorsCopy.bookImage = 'bookImage is required';
            valid = false;
        }

        if(categoryId.trim()){
            errorsCopy.categoryId = '';
        }else{
            errorsCopy.categoryId = 'category is required';
            valid = false;
        }
        setErrors(errorsCopy);
        return valid;
    }


    const getListAllBook = useCallback(async (): Promise<void> => {
        const token = localStorage.getItem("staff_accessToken");
        if (!token){
            return;
        }
        try {
            const response = await getBookValueById(token, idBook);
            console.log("Success processing data");
            if (response.bookImage) {
                const resImage = await getLoadImageBook(token, response.bookImage);
                const url = URL.createObjectURL(resImage);
                setPreviewUrl(url);
                setBookImage(response.bookImage);
            } else {
                setBookImage(""); 
            }
            setBookId(response.bookId);
            setBookTitle(response.bookTitle);
            setAuthor(response.author);
            setDescription(response.description);
            setPrice(response.price);
            setStock(response.stock.toString());
            setCategoryId(response.category.categoryId.toString());
        } catch (error) {
            console.log("Failed processing data", error);
            throw error;
        }
    }, [idBook]);
    



    useEffect(() => {
            if (isOpen) {
                getListAllBook();
            }
    }, [isOpen, getListAllBook]);
    
    const handleSave = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const token = localStorage.getItem("staff_accessToken");
        if (!token) {
            return;
        }
        if (validateForm()) {
            try {
                if (bookId === undefined) {
                    throw new Error("categoryId is undefined");
                }

                const newBooks: EditBookDto = {
                    bookId,
                    bookTitle,
                    author,
                    description,
                    price,
                    stock: Number(stock),
                    bookImage,
                    categoryId,
                };
            
                const result = await editBooks(token, bookId, newBooks);
                if(result){
                    console.log("success add data", result);
                    setBookId(0);
                    setBookTitle('');
                    setAuthor('');
                    setDescription('');
                    setPrice('');
                    setStock('');
                    setBookImage("");
                    setCategoryId('');
                    setErrorsAll("");
                    onSuccess();
                    closeModal();
                }else{
                    setErrorsAll("Login gagal. Cek email/password.");
                }
            } catch (err) {
                console.error("Gagal login", err);
                setErrorsAll("Login gagal. Cek email/password.");
            }
        }

        console.log("Saving changes...");
        closeModal();
    };
    

    return (
    <>
    <div className=" border-gray-200 rounded-2xl dark:border-gray-800">
        <div className="flex flex-col gap-6 lg:flex-row lg:items-start lg:justify-between">
            <button
                onClick={openModal}
                className="flex w-full items-center justify-center gap-2 rounded-full border border-gray-300 bg-white px-4 py-3 text-sm font-medium text-gray-700 shadow-theme-xs hover:bg-gray-50 hover:text-gray-800 dark:border-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:hover:bg-white/[0.03] dark:hover:text-gray-200 lg:inline-flex lg:w-auto"
            >
                <svg
                    className="fill-current"
                    width="18"
                    height="18"
                    viewBox="0 0 18 18"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                >
                    <path
                        fillRule="evenodd"
                        clipRule="evenodd"
                        d="M15.0911 2.78206C14.2125 1.90338 12.7878 1.90338 11.9092 2.78206L4.57524 10.116C4.26682 10.4244 4.0547 10.8158 3.96468 11.2426L3.31231 14.3352C3.25997 14.5833 3.33653 14.841 3.51583 15.0203C3.69512 15.1996 3.95286 15.2761 4.20096 15.2238L7.29355 14.5714C7.72031 14.4814 8.11172 14.2693 8.42013 13.9609L15.7541 6.62695C16.6327 5.74827 16.6327 4.32365 15.7541 3.44497L15.0911 2.78206ZM12.9698 3.84272C13.2627 3.54982 13.7376 3.54982 14.0305 3.84272L14.6934 4.50563C14.9863 4.79852 14.9863 5.2734 14.6934 5.56629L14.044 6.21573L12.3204 4.49215L12.9698 3.84272ZM11.2597 5.55281L5.6359 11.1766C5.53309 11.2794 5.46238 11.4099 5.43238 11.5522L5.01758 13.5185L6.98394 13.1037C7.1262 13.0737 7.25666 13.003 7.35947 12.9002L12.9833 7.27639L11.2597 5.55281Z"
                        fill=""
                    />
                </svg>
            Edit Book
            </button>
    </div>

    <Modal isOpen={isOpen} onClose={closeModal} className="max-w-[700px] m-4">
        <div className="no-scrollbar relative w-full max-w-[700px] overflow-y-auto rounded-3xl bg-white p-4 dark:bg-gray-900 lg:p-11">
            <div className="px-2 pr-14">
                <h4 className="mb-2 text-2xl font-semibold text-gray-800 dark:text-white/90">
                    Edit Book
                </h4>
                <p className="mb-6 text-sm text-gray-500 dark:text-gray-400 lg:mb-7">
                    Edit your data book.
                </p>
            </div>
            <form className="flex flex-col" onSubmit={handleSave}>
                {errorsAll && 
                    <Alert
                        variant="error"
                        title="Error Authentication"
                        message={errorsAll}
                    />
                }

                <div className="custom-scrollbar h-[570px] overflow-y-auto px-2 pb-3">
                    <div className="grid grid-cols-1 gap-x-6 gap-y-5 lg:grid-cols-2">
                        <Input placeholder="category ID"
                            type="hidden"
                            value={bookId ?? ''}
                            onChange={(e) => setBookId(Number(e.target.value))}
                        />
                        <div className="col-span-2">
                            <Label>Book Title</Label>
                            <Input placeholder="Book Title"
                                type="text"
                                value={bookTitle}
                                onChange={(e) => setBookTitle(e.target.value)}
                            />
                            {errors.bookTitle && <p className="text-red-500 text-sm">{errors.bookTitle}</p>}
                        </div>
                        <div className="col-span-2">
                            <Label>Book Author</Label>
                            <Input placeholder="Book Author"
                                type="text"
                                value={author}
                                onChange={(e) => setAuthor(e.target.value)}
                            />
                            {errors.author && <p className="text-red-500 text-sm">{errors.author}</p>}
                        </div>
                        <div className="col-span-2">
                            <Label>Book Description</Label>
                            <Input placeholder="Book Description"
                                type="text"
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                            />
                            {errors.description && <p className="text-red-500 text-sm">{errors.description}</p>}
                        </div>
                        <div className="col-span-2">
                            <Label>Book Price</Label>
                            <Input placeholder="Book Price"
                                type="text"
                                value={price}
                                onChange={(e) => setPrice(e.target.value)}
                            />
                            {errors.price && <p className="text-red-500 text-sm">{errors.price}</p>}
                        </div>
                        <div className="col-span-2">
                            <Label>Book Stock</Label>
                            <Input placeholder="Book Stock"
                                type="text"
                                value={stock}
                                onChange={(e) => setStock(e.target.value)}
                            />
                            {errors.stock && <p className="text-red-500 text-sm">{errors.stock}</p>}
                        </div>
                        <div className="col-span-2">
                            <Label>Book Image</Label>
                                <FileInput
                                    onChange={handleImageChange} 
                                    className="custom-class" 
                                />
                            {bookImage && (
                                <div className='my-2'>
                                    <img 
                                        alt='not found'
                                        width={"150"}
                                        src={previewUrl}
                                    />
                                </div>
                            )}
                            {errors.bookImage && <div className='invalid-feedback'>{errors.bookImage}</div>}
                        </div>
                        <div className="col-span-2">
                            <Label>Book Category</Label>
                            <Select
                                    options={optionsCategory}
                                    placeholder="Select Option"
                                    onChange={(value) => setCategoryId(value)}
                                    className="dark:bg-dark-900"
                            />
                            
                            {errors.categoryId && <p className="text-red-500 text-sm">{errors.categoryId}</p>}
                        </div>
                    </div>
                </div>
                <div className="flex items-center gap-3 px-2 py-5 lg:justify-end">
                    <Button size="sm" variant="outline" onClick={closeModal}>Close</Button>
                    <Button size="sm" type="submit">Update</Button>
                </div>
            </form>
        </div>
    </Modal>
</div>
    
    </>
    );
}
import React, { useCallback, useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import type { Category } from "../../interfaces/CategoryPublic.interface";
import { getPublicListCategory } from "../../api/Category";

// interface Category {
// id: number;
// name: string;
// iconUrl: string;
// }

// const categories: Category[] = [
// { id: 1, name: "Fiction", iconUrl: "https://source.unsplash.com/80x80/?fiction,book" },
// { id: 2, name: "Science", iconUrl: "https://source.unsplash.com/80x80/?science,book" },
// { id: 3, name: "History", iconUrl: "https://source.unsplash.com/80x80/?history,book" },
// { id: 4, name: "Fantasy", iconUrl: "https://source.unsplash.com/80x80/?fantasy,book" },
// { id: 5, name: "Romance", iconUrl: "https://source.unsplash.com/80x80/?romance,book" },
// ];








const CategoryBook: React.FC = () => {


const hasFetched = useRef(false);
    const [categorys, setCategorys] = useState<Category[]>([]);

    const getListAllCategory = useCallback(async (): Promise<void> => {
        
        try {
            const response = await getPublicListCategory();
            console.log("Success processing data");
            setCategorys(response);
        } catch (error) {
            console.log("Failed processing data", error);
            throw error;
        }
    }, []);

    useEffect(() => {
        console.log(hasFetched);
        if (!hasFetched.current) {
            getListAllCategory();
            hasFetched.current = true; // Cegah request kedua
        }
    }, [getListAllCategory]);
return (
    <section className="max-w-7xl mx-auto px-4 py-12 text-center">
    <h2 className="text-3xl font-bold text-gray-800 mb-6">Explore by Category</h2>

    {/* Desktop Grid */}
    <div className="hidden md:flex flex-wrap justify-center gap-8">
        {categorys.map((category) => (
        <Link
            key={category.categoryId}
            to={`/category/`}
            className="bg-white w-40 h-45 rounded-lg shadow hover:shadow-lg hover:scale-105 transition-transform duration-300 p-4 flex flex-col items-center justify-center"
        >
            <img src="" alt={category.categoryName} className="w-16 h-16 rounded-full object-cover mb-3" />
            <span className="text-gray-700 font-medium">{category.categoryName}</span>
        </Link>
        ))}
    </div>

    {/* Mobile Grid */}
    <div className="grid grid-cols-2 gap-4 md:hidden justify-center">
        {categorys.map((category) => (
        <Link
            key={category.categoryId}
            to={`/category/${category.categoryName.toLowerCase()}`}
            className="bg-white rounded-lg shadow hover:shadow-md hover:scale-105 transition-transform duration-300 p-4 h-36 flex flex-col items-center justify-center"
        >
            <img src="" alt={category.categoryName} className="w-14 h-14 rounded-full object-cover mb-2" />
            <span className="text-sm text-gray-700 font-medium">{category.categoryName}</span>
        </Link>
        ))}
    </div>
    </section>
);
};

export default CategoryBook;
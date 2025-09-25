import { useCallback, useEffect, useRef, useState } from "react";
import { getListCategories } from "../../api/Category";
import { Table, TableBody, TableCell, TableHeader, TableRow } from "../ui/table";
import CategoryAdd from "./CategoryAdd";
import CategoryEdit from "./CategoryEdit";
import CategoryDelete from "./CategoryDelete";
import type { Category } from "../../interfaces/Category.interface";


export default function CategoryList() {
    const hasFetched = useRef(false);
    const [categorys, setCategorys] = useState<Category[]>([]);

    const getListAllUser = useCallback(async (): Promise<void> => {
        const token = localStorage.getItem("staff_accessToken");
        console.log(token);
        if (!token){
            return;
        }
        try {
            const response = await getListCategories(token);
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
            getListAllUser();
            hasFetched.current = true; // Cegah request kedua
        }
    }, [getListAllUser]);

    return (
        <>
        <CategoryAdd onSuccess={getListAllUser}/>
        <div className="overflow-hidden rounded-xl border border-gray-200 bg-white dark:border-white/[0.05] dark:bg-white/[0.03]">  
            <div className="max-w-full overflow-x-auto">
                <Table>
                    {/* Table Header */}
                    <TableHeader className="border-b border-gray-100 dark:border-white/[0.05]">
                        <TableRow>
                            <TableCell
                                isHeader
                                className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                            >
                                ID
                            </TableCell>
                            <TableCell
                                isHeader
                                className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                            >
                                Category Name
                            </TableCell>
                            <TableCell
                                isHeader
                                className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                            >
                                Category Desk
                            </TableCell>
                            <TableCell
                                isHeader
                                className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                            >
                                Action
                            </TableCell>
                        </TableRow>
                    </TableHeader>

                    {/* Table Body */}
                    <TableBody className="divide-y divide-gray-100 dark:divide-white/[0.05]">
                        {categorys
                        .filter((category) => category.categoryId !== null && category.categoryId !== undefined)
                        .map((category) => (
                            <TableRow key={category.categoryId}>
                            <TableCell className="px-5 py-4 sm:px-6 text-gray-500 text-start text-theme-sm dark:text-gray-400">{category.categoryId}</TableCell>
                            <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">{category.categoryName}</TableCell>
                            <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">{category.categoryDescription}</TableCell>
                            <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">
                                <div className="flex flex-col gap-2 lg:flex-row lg:items-start"> 
                                <CategoryEdit onSuccess={getListAllUser} idCat={category.categoryId as number} />
                                <CategoryDelete onSuccess={getListAllUser} idCat={category.categoryId as number} />
                                </div>
                            </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </div>
        </div>
        </>
    );
}
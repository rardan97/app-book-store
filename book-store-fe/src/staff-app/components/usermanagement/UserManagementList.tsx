import { useCallback, useEffect, useRef, useState } from "react";
import { Table, TableBody, TableCell, TableHeader, TableRow } from "../ui/table";
import type { UserManagement } from "../../interfaces/UserManagement.interface";
import { getListUserManagement } from "../../api/UserManagement";




export default function UserManagementList() {
    const hasFetched = useRef(false);
    const [userManagement, setUserManagement] = useState<UserManagement[]>([]);

    const getListAllUserManagement = useCallback(async (): Promise<void> => {
        const token = localStorage.getItem("staff_accessToken");
        console.log(token);
        if (!token){
            return;
        }
        try {
            const response = await getListUserManagement(token);
            console.log("Success processing data");
            setUserManagement(response);
        } catch (error) {
            console.log("Failed processing data", error);
            throw error;
        }
    }, []);

    useEffect(() => {
        console.log(hasFetched);
        if (!hasFetched.current) {
            getListAllUserManagement();
            hasFetched.current = true; // Cegah request kedua
        }
    }, [getListAllUserManagement]);

    return (
        <>
        {/* <CategoryAdd onSuccess={getListAllUser}/> */}
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
                                User Full Name
                            </TableCell>
                            <TableCell
                                isHeader
                                className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                            >
                                User Email
                            </TableCell>
                             <TableCell
                                isHeader
                                className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                            >
                                UserName
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
                        {userManagement
                        .filter((userManagement) => userManagement.userId !== null && userManagement.userId !== undefined)
                        .map((userManagement) => (
                            <TableRow key={userManagement.userId}>
                            <TableCell className="px-5 py-4 sm:px-6 text-gray-500 text-start text-theme-sm dark:text-gray-400">{userManagement.userId}</TableCell>
                            <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">{userManagement.userFullName}</TableCell>
                            <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">{userManagement.userEmail}</TableCell>
                            <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">{userManagement.userName}</TableCell>
                            <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">
                                <div className="flex flex-col gap-2 lg:flex-row lg:items-start"> 
                                {/* <CategoryEdit onSuccess={getListAllUser} idCat={category.categoryId as number} />
                                <CategoryDelete onSuccess={getListAllUser} idCat={category.categoryId as number} /> */}
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
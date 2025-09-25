import { useCallback, useEffect, useRef, useState } from "react";
import { Table, TableBody, TableCell, TableHeader, TableRow } from "../ui/table";
import type { Role } from "../../interfaces/Role.interface";
import { getListRole } from "../../api/Role";
import RoleAdd from "./RoleAdd";
import RoleEdit from "./RoleEdit";
import RoleDelete from "./RoleDelete";

export default function RoleList() {
    const hasFetched = useRef(false);
    const [roles, setRoles] = useState<Role[]>([]);

    const getListAllRole = useCallback(async (): Promise<void> => {
        const token = localStorage.getItem("staff_accessToken");
        console.log(token);
        if (!token){
            return;
        }
        try {
            const response = await getListRole(token);
            console.log("Success processing data");
            setRoles(response);
        } catch (error) {
            console.log("Failed processing data", error);
            throw error;
        }
    }, []);

    useEffect(() => {
        console.log(hasFetched);
        if (!hasFetched.current) {
            getListAllRole();
            hasFetched.current = true; // Cegah request kedua
        }
    }, [getListAllRole]);

    return (
        <>
        <RoleAdd onSuccess={getListAllRole}/>
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
                                Role Name
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
                        {roles
                        .filter((role) => role.roleStaffId !== null && role.roleStaffId !== undefined)
                        .map((role) => (
                            <TableRow key={role.roleStaffId}>
                            <TableCell className="px-5 py-4 sm:px-6 text-gray-500 text-start text-theme-sm dark:text-gray-400">{role.roleStaffId}</TableCell>
                            <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">{role.roleStaffName}</TableCell>
                            <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">
                                <div className="flex flex-col gap-2 lg:flex-row lg:items-start"> 
                                <RoleEdit onSuccess={getListAllRole} idRole={role.roleStaffId as number} />
                                <RoleDelete onSuccess={getListAllRole} idRole={role.roleStaffId as number} />
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
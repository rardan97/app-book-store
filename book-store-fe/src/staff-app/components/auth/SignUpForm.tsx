import { useCallback, useEffect, useRef, useState } from "react";
import { Link } from "react-router";
import { ChevronLeftIcon, EyeCloseIcon, EyeIcon } from "../../icons";
import Label from "../form/Label";
import Input from "../form/input/InputField";
import Checkbox from "../form/input/Checkbox";
import Select from "../form/Select";
import { getListRoleAuth, signUpAuth } from "../../api/Auth";
import Alert from "../ui/alert/Alert";

type OptionRole = {
    value: string;
    label: string;
};

interface SignUp {
    staffFullName: string;
    staffUsername: string;
    staffPassword: string;
    staffEmail: string;
    role: string;
}

interface Errors {
    staffFullName: string;
    staffUsername: string;
    staffPassword: string;
    staffEmail: string;
    role: string;
}


export default function SignUpForm() {

    const [staffFullName, setStaffFullName] = useState<string>("");
    const [staffUsername, setStaffUsername] = useState<string>("");
    const [staffPassword, setStaffPassword] = useState<string>("");
    const [staffEmail, setStaffEmail] = useState<string>("");
    const [role, setRole] = useState<string>("");

    const [showPassword, setShowPassword] = useState(false);
    const [isChecked, setIsChecked] = useState(false);
    const [errorsAll, setErrorsAll] = useState<string>("");
    const [optionsRole, setOptionsRole] = useState<OptionRole[]>([]);


    const hasFetched = useRef(false);
    

    const [errors, setErrors] = useState<Errors>({
        staffFullName: '',
        staffUsername: '',
        staffPassword: '',
        staffEmail: '',
        role: '',
    });


    const getListAllRole = useCallback(async (): Promise<void> => {
        
        try {
            const response = await getListRoleAuth();
            const mapped = response.map((cat) => ({
                value: cat.roleStaffId.toString(),
                label: cat.roleStaffName,
            }));
            setOptionsRole(mapped);
            console.log("Success processing data");
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


    function validateForm(): boolean{
        console.log("proccess validation");
        let valid = true;
        const errorsCopy = {... errors}
        if(staffFullName.trim()){
            errorsCopy.staffFullName = '';
        }else{
            errorsCopy.staffFullName = 'staffFullName is required';
            valid = false;
        }
    
        if(staffUsername.trim()){
            errorsCopy.staffUsername = '';
        }else{
            errorsCopy.staffUsername = 'staffUsername is required';
            valid = false;
        }
        if(staffPassword.trim()){
            errorsCopy.staffPassword = '';
        }else{
            errorsCopy.staffPassword = 'staffPassword is required';
            valid = false;
        }
        if(staffEmail.trim()){
            errorsCopy.staffEmail = '';
        }else{
            errorsCopy.staffEmail = 'staffEmail is required';
            valid = false;
        }

        if(role.trim()){
            errorsCopy.role = '';
        }else{
            errorsCopy.role = 'role is required';
            valid = false;
        }

        setErrors(errorsCopy);
        return valid;
    }

    const handleSave = async (e: React.FormEvent<HTMLFormElement>) => {
            e.preventDefault();
           
            if (validateForm()) {
                try {
                    const newSignUp: SignUp = {
                        staffFullName,
                        staffUsername,
                        staffPassword,
                        staffEmail,
                        role
                    };
                
                    const result = await signUpAuth(newSignUp);
                    if(result){
                        console.log("success add data", result);
                        setStaffFullName("");
                        setStaffUsername("");
                        setStaffPassword("");
                        setStaffEmail("");
                        setRole("");
                        setErrorsAll("");
                    }else{
                        setErrorsAll("Login gagal. Cek email/password.");
                    }
                } catch (err) {
                    console.error("Gagal login", err);
                    setErrorsAll("Login gagal. Cek email/password.");
                }
            }
    
            console.log("Saving changes...");
        };




    return (
        <div className="flex flex-col flex-1 w-full overflow-y-auto lg:w-1/2 no-scrollbar">
        <div className="w-full max-w-md mx-auto mb-5 sm:pt-10">
            <Link
            to="/"
            className="inline-flex items-center text-sm text-gray-500 transition-colors hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-300"
            >
            <ChevronLeftIcon className="size-5" />
            Back to dashboard
            </Link>
        </div>
        <div className="flex flex-col justify-center flex-1 w-full max-w-md mx-auto">
            <div>
            <div className="mb-5 sm:mb-8">
                <h1 className="mb-2 font-semibold text-gray-800 text-title-sm dark:text-white/90 sm:text-title-md">
                Sign Up
                </h1>
                <p className="text-sm text-gray-500 dark:text-gray-400">
                Enter your email and password to sign up!
                </p>
            </div>
            <div>
                <div className="grid grid-cols-1 gap-3 sm:grid-cols-1 sm:gap-5">
                <button className="inline-flex items-center justify-center gap-3 py-3 text-sm font-normal text-gray-700 transition-colors bg-gray-100 rounded-lg px-7 hover:bg-gray-200 hover:text-gray-800 dark:bg-white/5 dark:text-white/90 dark:hover:bg-white/10">
                    <svg
                    width="20"
                    height="20"
                    viewBox="0 0 20 20"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                    >
                    <path
                        d="M18.7511 10.1944C18.7511 9.47495 18.6915 8.94995 18.5626 8.40552H10.1797V11.6527H15.1003C15.0011 12.4597 14.4654 13.675 13.2749 14.4916L13.2582 14.6003L15.9087 16.6126L16.0924 16.6305C17.7788 15.1041 18.7511 12.8583 18.7511 10.1944Z"
                        fill="#4285F4"
                    />
                    <path
                        d="M10.1788 18.75C12.5895 18.75 14.6133 17.9722 16.0915 16.6305L13.274 14.4916C12.5201 15.0068 11.5081 15.3666 10.1788 15.3666C7.81773 15.3666 5.81379 13.8402 5.09944 11.7305L4.99473 11.7392L2.23868 13.8295L2.20264 13.9277C3.67087 16.786 6.68674 18.75 10.1788 18.75Z"
                        fill="#34A853"
                    />
                    <path
                        d="M5.10014 11.7305C4.91165 11.186 4.80257 10.6027 4.80257 9.99992C4.80257 9.3971 4.91165 8.81379 5.09022 8.26935L5.08523 8.1534L2.29464 6.02954L2.20333 6.0721C1.5982 7.25823 1.25098 8.5902 1.25098 9.99992C1.25098 11.4096 1.5982 12.7415 2.20333 13.9277L5.10014 11.7305Z"
                        fill="#FBBC05"
                    />
                    <path
                        d="M10.1789 4.63331C11.8554 4.63331 12.9864 5.34303 13.6312 5.93612L16.1511 3.525C14.6035 2.11528 12.5895 1.25 10.1789 1.25C6.68676 1.25 3.67088 3.21387 2.20264 6.07218L5.08953 8.26943C5.81381 6.15972 7.81776 4.63331 10.1789 4.63331Z"
                        fill="#EB4335"
                    />
                    </svg>
                    Sign up with Google
                </button>
                </div>
                <div className="relative py-3 sm:py-5">
                <div className="absolute inset-0 flex items-center">
                    <div className="w-full border-t border-gray-200 dark:border-gray-800"></div>
                </div>
                <div className="relative flex justify-center text-sm">
                    <span className="p-2 text-gray-400 bg-white dark:bg-gray-900 sm:px-5 sm:py-2">
                    Or
                    </span>
                </div>
                </div>
                <form onSubmit={handleSave}>
                <div className="space-y-5">
                    {errorsAll && 
                    <Alert
                        variant="error"
                        title="Error Authentication"
                        message={errorsAll}
                    />
                }
                    {/* <!-- Full Name --> */}
                     <div>
                    <Label>
                        Full Name<span className="text-error-500">*</span>
                    </Label>
                    <Input
                        type="text"
                        id="fullName"
                        name="fullName"
                        placeholder="Enter your Full Name"
                        onChange={(e) => setStaffFullName(e.target.value)}
                    />
                    {errors.staffFullName && <p className="text-red-500 text-sm">{errors.staffFullName}</p>}
                    </div>
                    
                    {/* <!-- Email --> */}
                    <div>
                    <Label>
                        Email<span className="text-error-500">*</span>
                    </Label>
                    <Input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="Enter your email"
                        onChange={(e) => setStaffEmail(e.target.value)}
                    />
                    {errors.staffEmail && <p className="text-red-500 text-sm">{errors.staffEmail}</p>}
                    </div>
                     {/* <!-- Username --> */}
                    <div>
                    <Label>
                        Username<span className="text-error-500">*</span>
                    </Label>
                    <Input
                        type="text"
                        id="username"
                        name="username"
                        placeholder="Enter your username"
                        onChange={(e) => setStaffUsername(e.target.value)}
                    />
                    {errors.staffUsername && <p className="text-red-500 text-sm">{errors.staffUsername}</p>}
                    </div>

                    {/* <!-- Password --> */}
                    <div>
                        <Label>
                            Password<span className="text-error-500">*</span>
                        </Label>
                        <div className="relative">
                            <Input
                            placeholder="Enter your password"
                            type={showPassword ? "text" : "password"}
                            onChange={(e) => setStaffPassword(e.target.value)}
                            />
                            {errors.staffPassword && <p className="text-red-500 text-sm">{errors.staffPassword}</p>}
                            <span
                            onClick={() => setShowPassword(!showPassword)}
                            className="absolute z-30 -translate-y-1/2 cursor-pointer right-4 top-1/2"
                            >
                            {showPassword ? (
                                <EyeIcon className="fill-gray-500 dark:fill-gray-400 size-5" />
                            ) : (
                                <EyeCloseIcon className="fill-gray-500 dark:fill-gray-400 size-5" />
                            )}
                            </span>
                        </div>
                    </div>


                    <div>
                    <Label>
                        Role<span className="text-error-500">*</span>
                    </Label>
                    <Select
                        options={optionsRole}
                        placeholder="Select Option"
                        onChange={(val) => setRole(val)}
                        className="dark:bg-dark-900"

                    />
                    {errors.role && <p className="text-red-500 text-sm">{errors.role}</p>}
                    
                    </div>


                   

                    {/* <!-- Checkbox --> */}
                    <div className="flex items-center gap-3">
                    <Checkbox
                        className="w-5 h-5"
                        checked={isChecked}
                        onChange={setIsChecked}
                    />
                    <p className="inline-block font-normal text-gray-500 dark:text-gray-400">
                        By creating an account means you agree to the{" "}
                        <span className="text-gray-800 dark:text-white/90">
                        Terms and Conditions,
                        </span>{" "}
                        and our{" "}
                        <span className="text-gray-800 dark:text-white">
                        Privacy Policy
                        </span>
                    </p>
                    </div>
                    {/* <!-- Button --> */}
                    <div>
                    <button className="flex items-center justify-center w-full px-4 py-3 text-sm font-medium text-white transition rounded-lg bg-brand-500 shadow-theme-xs hover:bg-brand-600">
                        Sign Up
                    </button>
                    </div>
                </div>
                </form>

                <div className="mt-5">
                <p className="text-sm font-normal text-center text-gray-700 dark:text-gray-400 sm:text-start">
                    Already have an account? {""}
                    <Link
                    to="/staff/signin"
                    className="text-brand-500 hover:text-brand-600 dark:text-brand-400"
                    >
                    Sign In
                    </Link>
                </p>
                </div>
            </div>
            </div>
        </div>
        </div>
    );
    }

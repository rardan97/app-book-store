import React, { useState } from "react";
import { FaUser, FaEnvelope, FaLock } from "react-icons/fa";
import Button from "../../../staff-app/components/ui/button/Button";
import Alert from "../../../staff-app/components/ui/alert/Alert";
import { signUpAuthUser } from "../../api/Auth";

interface SignUpReq {
    userFullName: string;
    username: string;
    userEmail: string;
    password: string;
}

interface SignUp {
    userFullName: string;
    username: string;
    userEmail: string;
    password: string;
    confirmPassword: string;
}

interface Errors {
    userFullName: string;
    username: string;
    userEmail: string;
    password: string;
    confirmPassword: string;
}

const SignUp: React.FC = () => {
    const [userFullName, setUserFullName] = useState("");
    const [username, setUsername] = useState("");
    const [userEmail, setUserEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [errorsAll, setErrorsAll] = useState<string>("");

    const [errors, setErrors] = useState<Errors>({
        userFullName: '',
        username: '',
        userEmail: '',
        password: '',
        confirmPassword: '',
    });


    function validateForm(): boolean{
        console.log("proccess validation");
        let valid = true;
        const errorsCopy = {... errors}
        if(userFullName.trim()){
            errorsCopy.userFullName = '';
        }else{
            errorsCopy.userFullName = 'userFullName is required';
            valid = false;
        }
    
        if(username.trim()){
            errorsCopy.username = '';
        }else{
            errorsCopy.username = 'username is required';
            valid = false;
        }
        if(userEmail.trim()){
            errorsCopy.userEmail = '';
        }else{
            errorsCopy.userEmail = 'userEmail is required';
            valid = false;
        }
        if(password.trim()){
            errorsCopy.password = '';
            
        }else{
            errorsCopy.password = 'password is required';
            valid = false;
        }

        if(confirmPassword.trim()){
            errorsCopy.confirmPassword = '';
            if (password !== confirmPassword) {
                errorsCopy.password = 'Password dan konfirmasi password tidak cocok';
                valid = false;
            }
        }else{
            errorsCopy.confirmPassword = 'password is required';
            valid = false;
        }

        setErrors(errorsCopy);
        return valid;
    }

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        
        if (validateForm()) {
            try {
                const newSignUp: SignUpReq = {
                    userFullName,
                    username,
                    userEmail,
                    password
                };
            
                const result = await signUpAuthUser(newSignUp);
                if(result){
                    console.log("success add data", result);
                    setUserFullName("");
                    setUsername("");
                    setUserEmail("");
                    setPassword("");
                    setConfirmPassword("");
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
        <div className="min-h-screen bg-gradient-to-r from-purple-700 via-indigo-800 to-blue-900 flex items-center justify-center px-6 py-12">
        <div className="bg-white bg-opacity-90 backdrop-blur-lg rounded-3xl shadow-2xl max-w-lg w-full p-10 space-y-8">
            <h2 className="text-4xl font-extrabold text-center text-indigo-900 select-none">
            Create Account
            </h2>
            <p className="text-center text-indigo-700 font-medium tracking-wide">
            Join our BookStore community
            </p>

            {errorsAll && 
                <Alert
                    variant="error"
                    title="Error Authentication"
                    message={errorsAll}
                />
            }

            <form onSubmit={handleSubmit} className="space-y-6">
                <div className="relative">
                    <FaUser className="absolute left-3 top-1/2 -translate-y-1/2 text-indigo-600" />
                    <input
                    type="text"
                    id="name"
                    placeholder="Full Name"
                    value={userFullName}
                    onChange={(e) => setUserFullName(e.target.value)}
                    required
                    className="w-full pl-10 pr-4 py-3 rounded-lg border border-indigo-300 focus:border-indigo-600 focus:ring-2 focus:ring-indigo-400 transition outline-none text-indigo-900 font-semibold placeholder-indigo-400"
                    />
                    {errors.userFullName && <p className="text-red-500 text-sm">{errors.userFullName}</p>}
                </div>
                <div className="relative">
                    <FaUser className="absolute left-3 top-1/2 -translate-y-1/2 text-indigo-600" />
                    <input
                    type="text"
                    id="name"
                    placeholder="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                    className="w-full pl-10 pr-4 py-3 rounded-lg border border-indigo-300 focus:border-indigo-600 focus:ring-2 focus:ring-indigo-400 transition outline-none text-indigo-900 font-semibold placeholder-indigo-400"
                    />
                    {errors.username && <p className="text-red-500 text-sm">{errors.username}</p>}
                </div>

                <div className="relative">
                    <FaEnvelope className="absolute left-3 top-1/2 -translate-y-1/2 text-indigo-600" />
                    <input
                    type="email"
                    id="email"
                    placeholder="Email address"
                    value={userEmail}
                    onChange={(e) => setUserEmail(e.target.value)}
                    required
                    className="w-full pl-10 pr-4 py-3 rounded-lg border border-indigo-300 focus:border-indigo-600 focus:ring-2 focus:ring-indigo-400 transition outline-none text-indigo-900 font-semibold placeholder-indigo-400"
                    />
                    {errors.userEmail && <p className="text-red-500 text-sm">{errors.userEmail}</p>}
                </div>

                <div className="relative">
                    <FaLock className="absolute left-3 top-1/2 -translate-y-1/2 text-indigo-600" />
                    <input
                    type="password"
                    id="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    minLength={6}
                    className="w-full pl-10 pr-4 py-3 rounded-lg border border-indigo-300 focus:border-indigo-600 focus:ring-2 focus:ring-indigo-400 transition outline-none text-indigo-900 font-semibold placeholder-indigo-400"
                    />
                    {errors.password && <p className="text-red-500 text-sm">{errors.password}</p>}
                </div>

                <div className="relative">
                    <FaLock className="absolute left-3 top-1/2 -translate-y-1/2 text-indigo-600" />
                    <input
                    type="password"
                    id="confirmPassword"
                    placeholder="Confirm Password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    required
                    minLength={6}
                    className="w-full pl-10 pr-4 py-3 rounded-lg border border-indigo-300 focus:border-indigo-600 focus:ring-2 focus:ring-indigo-400 transition outline-none text-indigo-900 font-semibold placeholder-indigo-400"
                    />
                    {errors.confirmPassword && <p className="text-red-500 text-sm">{errors.confirmPassword}</p>}
                </div>

                <Button 
                    type="submit"
                    className="w-full" 
                    size="sm"
                    >
                    Sign Up
                </Button>
            </form>

            <p className="text-center text-indigo-700 font-semibold">
            Already have an account?{" "}
            <a href="/signin" className="text-indigo-900 hover:underline">
                Sign In
            </a>
            </p>
        </div>
        </div>
    );
};

export default SignUp;
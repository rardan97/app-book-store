import React, { useState } from "react";
import { FaEnvelope, FaLock } from "react-icons/fa";
import { useNavigate } from "react-router";
import { useAuthUser } from "../../context/AuthProviderUser";
import Alert from "../../../staff-app/components/ui/alert/Alert";
import Button from "../../../staff-app/components/ui/button/Button";
import { signInAuthUser } from "../../api/Auth";

interface Errors {
    username: string;
    password: string;
}

interface UserInfo {
  userName: string;
}


const SignIn: React.FC = () => {


  const { loginUser } = useAuthUser();
  const navigate = useNavigate();


  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [errors, setErrors] = useState<Errors>({
      username: '',
      password: ''
  });
  const [errorsAll, setErrorsAll] = useState<string>("");

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
      e.preventDefault();
      if (validateForm()) {
          try {
              const result = await signInAuthUser({ username, password});
              if(result.data){

                  const userData: UserInfo = {
                      userName: result.data.userName
                  };


                  localStorage.setItem("user_accessToken", result.data.token);
                  localStorage.setItem("user_refreshToken", result.data.refreshToken);
                  localStorage.setItem("user_data", JSON.stringify(userData));
                  loginUser(result.data);
                  navigate("/");
              }else{
                  setErrorsAll("Login gagal. Cek email/password.");
              }
          } catch (err) {
              console.error("Gagal login", err);
              setErrorsAll("Login gagal. Cek email/password.");
          }
      }
  }

  function validateForm(): boolean{
      console.log("proccess validation");
      let valid = true;
      const errorsCopy = {... errors}
      if(username.trim()){
          errorsCopy.username = '';
      }else{
          errorsCopy.username = 'username is required';
          valid = false;
      }

      if(password.trim()){
          errorsCopy.password = '';
      }else{
          errorsCopy.password = 'password is required';
          valid = false;
      }
      setErrors(errorsCopy);
      return valid;
  }



    return (
      <div className="min-h-screen bg-gradient-to-r from-purple-700 via-indigo-800 to-blue-900 flex items-center justify-center px-6 py-12">
        <div className="bg-white bg-opacity-90 backdrop-blur-lg rounded-3xl shadow-2xl max-w-lg w-full p-10 space-y-8">
          <h2 className="text-4xl font-extrabold text-center text-indigo-900 select-none">
            Welcome Back!
          </h2>
          <p className="text-center text-indigo-700 font-medium tracking-wide">
            Sign in to your BookStore account
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
              <FaEnvelope className="absolute left-3 top-1/2 -translate-y-1/2 text-indigo-600" />
              <input
                type="text"
                id="username"
                placeholder="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
                className="w-full pl-10 pr-4 py-3 rounded-lg border border-indigo-300 focus:border-indigo-600 focus:ring-2 focus:ring-indigo-400 transition outline-none text-indigo-900 font-semibold placeholder-indigo-400"
              />
              
                {errors.username && <p className="text-red-500 text-sm">{errors.username}</p>}
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
                className="w-full pl-10 pr-4 py-3 rounded-lg border border-indigo-300 focus:border-indigo-600 focus:ring-2 focus:ring-indigo-400 transition outline-none text-indigo-900 font-semibold placeholder-indigo-400"
              />
              
                {errors.username && <p className="text-red-500 text-sm">{errors.username}</p>}
            </div>


            <Button 
                    type="submit"
                    className="w-full" 
                    size="sm"
                    >
                    Sign in
                </Button>
          </form>

          <p className="text-center text-indigo-700 font-semibold">
            Don't have an account?{" "}
            <a href="/signup" className="text-indigo-900 hover:underline">
              Sign Up
            </a>
          </p>
        </div>
      </div>
    );
};

export default SignIn;
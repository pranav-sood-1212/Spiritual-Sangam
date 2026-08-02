import { Link , useNavigate } from "react-router-dom";
import { useState } from "react";
import { loginUser } from "../../api/authApi";


const LoginForm = () => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const [errors, setErrors] = useState({
    userName: "",
    password: "",
  });

  const validateForm = () => {
    const newErrors = {
      userName: "",
      password: "",
    };

    if (!userName.trim()) {
      newErrors.userName = "user name is required.";
    }

    if (!password) {
      newErrors.password = "Password is required.";
    }

    setErrors(newErrors);

    return Object.values(newErrors).every((error) => error === "");
  };

  const handleSubmit = async(event) => {
    event.preventDefault();

    if (!validateForm()) {
      return;
    }
    try {
      setIsLoading(true);
      const response=await loginUser({
        userName,
        password,
      }) ;
      localStorage.setItem("token", response.token);
      alert(response.message);
      navigate("/home");

    } catch (error) {
      if (error.response) {
        alert(error.response.data.message);
      } else {
        alert("Unable to connect to the server.");
      }

    } finally {
      setIsLoading(false);
    }
  

  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="space-y-5">
        <div>
          <label className="mb-2 block text-sm font-medium text-gray-700">
            userName
          </label>

          <input
            type="text"
            placeholder="Enter your user name"
            value={userName}
            onChange={(event) => setUserName(event.target.value)}
            className="w-full rounded-lg border border-gray-300 px-4 py-3 outline-none focus:border-orange-500"
          />

          {errors.userName && (
            <p className="mt-1 text-sm text-red-600">
              {errors.userName}
            </p>
          )}
        </div>


        <div>
          <label className="mb-2 block text-sm font-medium text-gray-700">
            Password
          </label>

          <input
            type="password"
            placeholder="Enter your password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            className="w-full rounded-lg border border-gray-300 px-4 py-3 outline-none focus:border-orange-500"
          />

          {errors.password && (
            <p className="mt-1 text-sm text-red-600">
              {errors.password}
            </p>
          )}
        </div>

        <button
          type="submit"
          disabled={isLoading}
          className="w-full rounded-lg bg-orange-600 py-3 font-semibold text-white transition hover:bg-orange-700"
        >
          {isLoading ? "Logging In..." : "Log In"}
        </button>

        <p className="text-center text-sm text-gray-600">
          Don't have an account?{" "}
          <Link
            to="/register"
            className="font-medium text-orange-600 hover:underline"
          >
            Create Account
          </Link>
        </p>
      </div>
    </form>
  );
};

export default LoginForm;
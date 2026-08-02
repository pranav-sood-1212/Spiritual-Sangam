import { useState } from "react";
import { Link } from "react-router-dom";
import { registerUser } from "../../api/authApi";

const RegisterForm = () => {
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [isLoading, setIsLoading] = useState(false);


const [errors, setErrors] = useState({
  userName: "",
  email: "",
  phoneNumber: "",
  password: "",
  confirmPassword: "",
});

  const validateForm = () => {
    const newErrors = {
      userName: "",
      email: "",
      password: "",
      phoneNumber: "",
      confirmPassword: "",
    };

    if (!userName.trim()) {
      newErrors.userName = "user name is required.";
    }

    if (!email.trim()) {
      newErrors.email = "Email is required.";
    } else if (!email.includes("@")) {
      newErrors.email = "Please enter a valid email address.";
    }

    if (!password.trim()) {
      newErrors.password = "Password is required.";
    }

    if (!confirmPassword.trim()) {
      newErrors.confirmPassword = "Please confirm your password.";
    } else if (password.trim() !== confirmPassword.trim()) {
      newErrors.confirmPassword = "Passwords do not match.";
    }

    if (!phoneNumber.trim()) {
      newErrors.phoneNumber = "Phone number is required.";
    } else if (!/^\d{10}$/.test(phoneNumber.trim())) {
      newErrors.phoneNumber = "Please enter a valid 10-digit phone number.";
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

      const response = await registerUser({
        userName,
        email,
        phoneNumber,
        password,
      });

      console.log(response);

      alert(response.message);
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
            Email Address
          </label>

          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            className="w-full rounded-lg border border-gray-300 px-4 py-3 outline-none focus:border-orange-500"
          />

          {errors.email && (
            <p className="mt-1 text-sm text-red-600">
              {errors.email}
            </p>
          )}
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-gray-700">
            Phone Number
          </label>

          <input
            type="tel"
            placeholder="Enter your phone number"
            value={phoneNumber}
            onChange={(event) => setPhoneNumber(event.target.value)}
            className="w-full rounded-lg border border-gray-300 px-4 py-3 outline-none focus:border-orange-500"
          />

          {errors.phoneNumber && (
            <p className="mt-1 text-sm text-red-600">
              {errors.phoneNumber}
            </p>
          )}
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-gray-700">
            Password
          </label>

          <input
            type="password"
            placeholder="Create a password"
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

        <div>
          <label className="mb-2 block text-sm font-medium text-gray-700">
            Confirm Password
          </label>

          <input
            type="password"
            placeholder="Confirm your password"
            value={confirmPassword}
            onChange={(event) => setConfirmPassword(event.target.value)}
            className="w-full rounded-lg border border-gray-300 px-4 py-3 outline-none focus:border-orange-500"
          />

          {errors.confirmPassword && (
            <p className="mt-1 text-sm text-red-600">
              {errors.confirmPassword}
            </p>
          )}
        </div>

        <button
          type="submit"
          disabled={isLoading}
          className="w-full rounded-lg bg-orange-600 py-3 font-semibold text-white transition hover:bg-orange-700 disabled:cursor-not-allowed disabled:opacity-60"
        >
          {isLoading ? "Creating Account..." : "Create Account"}
        </button>

        <p className="text-center text-sm text-gray-600">
          Already have an account?{" "}
          <Link
            to="/login"
            className="font-medium text-orange-600 hover:underline"
          >
            Log In
          </Link>
        </p>
      </div>
      
      


        
    </form>
  );
};

export default RegisterForm;
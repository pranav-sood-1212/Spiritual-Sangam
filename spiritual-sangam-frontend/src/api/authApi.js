import axiosInstance from "./axiosInstance";


export const registerUser = async (userData) => {
  const response = await axiosInstance.post(
    "/user/signUp",
    userData
  );

  return response.data;
};

export const loginUser = async (credentials) => {
  const response = await axiosInstance.post(
   "/user/login",
    credentials
  );


  return response.data;
};



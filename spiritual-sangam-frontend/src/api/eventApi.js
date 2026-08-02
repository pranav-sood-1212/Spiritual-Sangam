import axiosInstance from "./axiosInstance";


export const getUpcomingEvents = async () => {
  const response = await axiosInstance.get(
   "/event/upcoming",
  );

  return response.data;
};

export const getAllEvents = async () => {
  const response = await axiosInstance.get(
   "/event/allEvents",
  );

  return response.data;
};


export const detailsOfEvent = async (eventId) => {
  const response = await axiosInstance.get(
   `/event/${eventId}`,
  );

  return response.data;
};


export const registerInEvent = async (eventId) => {
    const response = await axiosInstance.post(
        `/event/${eventId}/register`
    );

    return response.data;
};
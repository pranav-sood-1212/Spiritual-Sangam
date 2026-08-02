import axiosInstance from "./axiosInstance";

export const getFeaturedCommunities = async () => {
    const response = await axiosInstance.get(
        "/community/featured"
    )

    return response.data;

}


export const getAllCommunities = async () => {
    const response = await axiosInstance.get(
        "/community/allCommunities"
    )

    return response.data;

}


export const searchCommunities = async (query) => {
    const response = await axiosInstance.get(
        `/community/search?query=${query}`
    )

    return response.data;

}


export const detailsOfCommunity = async (communityId) => {
    const response = await axiosInstance.get(
        `/community/${communityId}`
    )

    return response.data;

}


export const joinCommunity = async (communityId) => {
    const response = await axiosInstance.post(
        `/community/${communityId}/join`
    );

    return response.data;
};
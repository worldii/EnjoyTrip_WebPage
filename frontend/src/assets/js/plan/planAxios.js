import axios from "axios";

const ACCESS_TOKEN_HEADER=process.env.VUE_APP_ACCESS_TOKEN_HEADER;

function createPlanAxios(){
    return axios.create({
        baseURL: process.env.VUE_APP_KAKAO_MAP_API_BASE_URL,
        headers: {
            "Authorization": process.env.VUE_APP_KAKAO_REST_API_KEY,
        }
    });
}

function createMobilityAxios(){
    return axios.create({
        baseURL: process.env.VUE_APP_KAKAO_MOBILITY_API_BASE_URL,
        headers: {
            "Authorization": process.env.VUE_APP_KAKAO_REST_API_KEY,
            "Content-Type" : "application/json",
        }
    });
}

function createUserPlanAxios(){
    return axios.create({
        baseURL: process.env.VUE_APP_API_BASE_URL,
        headers:{
            "Content-Type": "application/json;charset=utf-8",
            [`${ACCESS_TOKEN_HEADER}`] : sessionStorage.getItem(ACCESS_TOKEN_HEADER),
        }
    });
}
export {createPlanAxios,createMobilityAxios,createUserPlanAxios};
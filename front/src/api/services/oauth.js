import api from "../api";

export const oauthAPI = {
    googleSignUp : (code) => api.get(`/oauth/google?code=${code}`),
    kakaoSignUp : (code) => api.get(`/oauth/kakao?code=${code}`),
    emailLogin : (data) => api.post(`/oauth/login`, data),
}
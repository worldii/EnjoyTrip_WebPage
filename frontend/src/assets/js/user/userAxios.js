import axios from "axios";
import { regenerateToken } from "@/assets/js/authorization/jwtAxios";
function createUserAxios() {
  let userAxios = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
      "access-token": sessionStorage.getItem("access-token"),
    },
  });

  userAxios.interceptors.response.use(
    (response) => response,
    async (error) => {
      const {
        config,
        response: { status },
      } = error;

      if (status !== 401 || config.sent) {
        return Promise.reject(error);
      }

      config.sent = true;
      let refreshAccessToken = await regenerateToken();
      if (refreshAccessToken) {
        config.headers["access-token"] = sessionStorage.getItem("access-token");
        return userAxios(config);
      }
      // Todo: 재로그인
      return Promise.reject(error);
    }
  );

  return userAxios;
}

export { createUserAxios };

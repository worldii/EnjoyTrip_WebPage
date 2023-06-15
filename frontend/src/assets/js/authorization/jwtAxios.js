import axios from "axios";
import store from "@/store";

const jwtAxios = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL,
});
const REFRESH_URI = "/user/refresh";
const REFRESH_TOKEN_HEADER = process.env.VUE_APP_REFRESH_TOKEN_HEADER;
const ACCESS_TOKEN_HEADER = process.env.VUE_APP_ACCESS_TOKEN_HEADER;

async function regenerateToken() {
  jwtAxios.defaults.headers.common[REFRESH_TOKEN_HEADER] =
    sessionStorage.getItem(REFRESH_TOKEN_HEADER);

  return await jwtAxios
    .post(REFRESH_URI, {
      userId: JSON.parse(sessionStorage.getItem("vuex")).userStore.userId,
    })
    .then(({ data, status }) => {
      console.log(status);
      if (status === 200 || status === 201 || status === 202) {
        sessionStorage.setItem(ACCESS_TOKEN_HEADER, data[ACCESS_TOKEN_HEADER]);
        return data[ACCESS_TOKEN_HEADER];
      }
      alert("세션이 만료되었습니다 1. 다시 로그인 해주세요!");
      store.commit("userStore/SET_IS_LOGIN", false);
      store.commit("userStore/SET_USER_INFO", null);
      store.commit("userStore/SET_USER_ID", "");
      sessionStorage.removeItem(ACCESS_TOKEN_HEADER);
      sessionStorage.removeItem(REFRESH_TOKEN_HEADER);
      return "";
    })
    .catch(() => {
      alert("세션이 만료되었습니다2. 다시 로그인 해주세요!");
      store.commit("userStore/SET_IS_LOGIN", false);
      store.commit("userStore/SET_USER_INFO", null);
      store.commit("userStore/SET_USER_ID", "");
      sessionStorage.removeItem(ACCESS_TOKEN_HEADER);
      sessionStorage.removeItem(REFRESH_TOKEN_HEADER);
      return "";
    });
}

export { regenerateToken };

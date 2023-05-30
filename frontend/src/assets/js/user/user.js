import { createUserAxios } from "@/assets/js/user/userAxios";

const axios = createUserAxios();
const USER_URI = "/user";

const ACCESS_TOKEN_HEADER = process.env.VUE_APP_ACCESS_TOKEN_HEADER;
async function join(user, success, fail) {
  await axios.post(USER_URI, JSON.stringify(user)).then(success).catch(fail);
}
async function login(user, success, fail) {
  await axios
    .post(USER_URI + `/login`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}
async function logout(userId, success, fail) {
  await axios
    .get(USER_URI + `/${userId}`)
    .then(success)
    .catch(fail);
}

async function modify(user, success, fail) {
  axios.defaults.headers[ACCESS_TOKEN_HEADER] =
    sessionStorage.getItem(ACCESS_TOKEN_HEADER);
  await axios.put(USER_URI, JSON.stringify(user)).then(success).catch(fail);
}

async function leave(userId, success, fail) {
  axios.defaults.headers[ACCESS_TOKEN_HEADER] =
    sessionStorage.getItem(ACCESS_TOKEN_HEADER);
  await axios
    .delete(USER_URI + `/${userId}`)
    .then(success)
    .catch(fail);
}

async function info(userId, success, fail) {
  axios.defaults.headers[ACCESS_TOKEN_HEADER] =
    sessionStorage.getItem(ACCESS_TOKEN_HEADER);
  await axios
    .get(USER_URI + `/info` + `/${userId}`)
    .then(success)
    .catch(fail);
}

export { login, logout, modify, leave, join, info };

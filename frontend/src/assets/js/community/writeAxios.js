import { createUserAxios } from "@/assets/js/user/userAxios";

const writeAxios = createUserAxios();
writeAxios.defaults.headers["Content-Type"] = "multipart/form-data";
// export axios

export default writeAxios;

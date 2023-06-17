import { createUserAxios } from "@/assets/js/user/userAxios";

const axios = createUserAxios();
import { createPlanAxios } from "@/assets/js/plan/planAxios";

const planAxios = createPlanAxios();

// axios 객체 생성
export default axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});

async function searchMapByCategory(params, success, fail) {
  await planAxios
    .get("/v2/local/search/category.json", {
      params: params,
    })
    .then(success)
    .catch(fail);
}
export { searchMapByCategory };

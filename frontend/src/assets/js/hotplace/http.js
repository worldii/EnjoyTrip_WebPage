import axios from "axios";
import { createPlanAxios } from "@/assets/js/plan/planAxios";

const planAxios = createPlanAxios();

// axios 객체 생성
export default axios.create({
  baseURL: "http://localhost:4029/",
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

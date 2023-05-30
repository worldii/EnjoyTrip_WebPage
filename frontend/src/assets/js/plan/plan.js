import {createMobilityAxios, createPlanAxios, createUserPlanAxios} from "@/assets/js/plan/planAxios";

const planAxios = createPlanAxios();
const mobilityAxios = createMobilityAxios();
const userPlanAxios = createUserPlanAxios();
async function searchMap(params, success, fail){
    await planAxios.get("/v2/local/search/keyword.json",{
       params:params
    }).then(success).catch(fail);
}

async function searchBlog(params, success, fail) {
  await planAxios
    .get("/v2/search/blog", {
      params: params,
    })
    .then(success)
    .catch(fail);
}

async function searchRoute(origin,destination,success,fail){
    await mobilityAxios.get("/v1/directions",{
        params:{
            origin:origin,
            destination:destination,
        }
    }).then(success).catch(fail);
}

async function savePlanBoard(planBoard,success,fail){
    await userPlanAxios.post("/plan",JSON.stringify(planBoard)).then(success).catch(fail);
}

async function selectPlanBoard(planBoardId,success,fail){
    await userPlanAxios.get("/plan/detail"+`/${planBoardId}`).then(success).catch(fail);
}

async function selectPlanBoardList(userId,success,fail){
    await userPlanAxios.get("/plan/list"+`/${userId}`).then(success).catch(fail);
}
export {searchMap,searchBlog,searchRoute,savePlanBoard,selectPlanBoard,selectPlanBoardList};


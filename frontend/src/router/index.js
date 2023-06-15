import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import HotPlaceView from "@/views/HotPlaceView.vue";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/community",
    name: "community",
    component: () =>
      import(/* webpackChunkName: "community" */ "../views/CommunityView.vue"),
    redirect: "/community/list/1",
    children: [
      {
        path: "list",
        name: "boardList",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/community/CommunityList.vue"
          ),
      },
      {
        path: "list/:currentPage",
        name: "boardListPage",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/community/CommunityList.vue"
          ),
      },
      {
        path: "write",
        name: "boardWrite",
        beforeEnter: (to, from, next) => {
          if (sessionStorage.getItem("access-token")) {
            next();
          } else {
            alert("로그인이 필요합니다.");
          }
        },
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/community/CommunityWrite"
          ),
      },
      {
        path: "view/:boardId",
        name: "boardview",
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/community/CommunityDetail"
          ),
      },
      {
        path: "modify/:boardId",
        name: "boardmodify",
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/community/CommunityModify"
          ),
      },
    ],
  },
  {
    path: "/user",
    name: "user",
    component: () =>
      import(/* webpackChunkName: "community" */ "../views/UserView.vue"),
    redirect: "/user/login",
    children: [
      {
        path: "login",
        name: "userLogin",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/user/UserLogin.vue"
          ),
      },
      {
        path: "join",
        name: "userJoin",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/user/UserJoin.vue"
          ),
      },
      {
        path: "information",
        name: "userInformation",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/user/UserInformation.vue"
          ),
      },
      {
        path: "userplan",
        name: "userPlan",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/user/UserPlan.vue"
          ),
      },
      {
        path: "userplanlist",
        name: "UserPlanList",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/user/UserPlanList.vue"
          ),
      },
    ],
  },
  {
    path: "/plan",
    name: "plan",
    component: () =>
      import(/* webpackChunkName: "community" */ "../views/PlanVue.vue"),
    redirect: "/plan/subject",
    children: [
      {
        path: "subject",
        name: "subject",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/plan/PlanSubject.vue"
          ),
      },
      {
        path: "date",
        name: "date",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/plan/PlanDate.vue"
          ),
      },
      {
        path: "detail",
        name: "detail",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/plan/PlanDetail.vue"
          ),
      },
      {
        path: "save",
        name: "save",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/plan/PlanSave.vue"
          ),
      },
    ],
  },
  {
    path: "/hotplace",
    name: "hotPlace",
    component: HotPlaceView,
    redirect: "/hotplace/list",
    children: [
      {
        path: "list",
        name: "hotPlaceList",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/hotplace/HotPlaceList"
          ),
      },
      {
        path: "view/:hotPlaceId",
        name: "hotPlaceDetail",
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/hotplace/HotPlaceDetail"
          ),
      },
      {
        path: "write",
        name: "hotPlaceWrite",
        component: () =>
          import(
            /* webpackChunkName: "community" */ "@/components/hotplace/HotPlaceWrite"
          ),
      },
      {
        path: "view/hotPlace/:hotPlaceArticleId",
        name: "hotPlaceArticleView",
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/hotplace/HotPlaceArticlesItemDetail"
          ),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;

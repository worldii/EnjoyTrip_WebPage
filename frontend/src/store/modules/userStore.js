// import jwtDecode from "jwt-decode";
import {
  login,
  logout,
  info,
  modify,
  join,
  leave,
} from "@/assets/js/user/user";
import router from "@/router";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    userId: "",
    userInfo: null,
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_USER_ID: (state, userId) => {
      state.userId = userId;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo;
    },
  },
  actions: {
    async userLogin({ commit }, user) {
      await login(
        user,
        ({ data }) => {
          if (data.success) {
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];

            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);

            console.log(accessToken);
            console.log(refreshToken);
            commit("SET_USER_ID", user.userId);
            commit("SET_IS_LOGIN", true);
          } else {
            commit("SET_IS_LOGIN", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userLogout({ commit }, userId) {
      await logout(
        userId,
        ({ data }) => {
          if (data.success) {
            commit("SET_IS_LOGIN", false);
            commit("SET_USER_ID", "");
            commit("SET_USER_INFO", null);
            sessionStorage.removeItem("access-token");
            sessionStorage.removeItem("refresh-token");

            // router.push({ name: "home" });
          } else {
            alert("잠시후 다시 실행해주세요!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getUserInfo({ commit }, userId) {
      await info(
        userId,
        ({ data }) => {
          if (data.success) {
            data.userInfo.password = "안알랴줌";
            commit("SET_USER_INFO", data.userInfo);
            router.push({ name: "userInformation" });
          } else {
            alert("유저 정보 조회 실패!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userModify({ commit }, user) {
      await modify(
        user,
        ({ data }) => {
          if (data.success) {
            commit("SET_USER_INFO", user);
          } else {
            alert("유저 정보 수정 실패!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userJoin(_, user) {
      await join(
        user,
        ({ data }) => {
          console.log(data);
          if (data.success) {
            router.push({ name: "userLogin" });
            alert("회원 가입 성공!");
          } else {
            alert("회원 가입 실패!");
          }
        },
        (error) => {
          alert("아이디가 중복됩니다. 회원 가입 실패!");
          console.log(error);
        }
      );
    },
    async userLeave({ commit }, userId) {
      await leave(
        userId,
        async ({ data }) => {
          if (data.success) {
            alert("가라...");
            commit("SET_IS_LOGIN", false);
            commit("SET_USER_ID", "");
            commit("SET_USER_INFO", null);
            sessionStorage.removeItem("access-token");
            sessionStorage.removeItem("refresh-token");
            router.push({ name: "home" });
          } else {
            alert("들어올때는 마음대로지만 나갈때는 아니란다..^^");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default userStore;

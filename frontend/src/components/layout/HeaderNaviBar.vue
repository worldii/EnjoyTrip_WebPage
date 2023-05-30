<template>
    <div>
        <b-navbar class="navbar" toggleable="sm" type="light" >
            <b-navbar-brand href="#!"></b-navbar-brand>
            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item href="#" router-link to="/">홈</b-nav-item>

                    <b-nav-item href="#" router-link to="/hotPlace">핫플공유</b-nav-item>
                    <b-nav-item href="#" router-link to="/plan">여행계획</b-nav-item>
                    <b-nav-item href="#" router-link to="/community/list/1">커뮤니티</b-nav-item>
                </b-navbar-nav>

                <b-navbar-nav class="ml-auto">
                    <b-nav-item-dropdown text="시작" v-if="!isLogin" right>
                        <b-dropdown-item href="#" router-link to="/user/login">로그인</b-dropdown-item>
                        <b-dropdown-item href="#" router-link to="/user/join">회원가입</b-dropdown-item>
                    </b-nav-item-dropdown>
                    <b-nav-item-dropdown text="마이페이지" v-else right>
                        <b-dropdown-item href="#" @click="moveUserInformation">내정보 조회</b-dropdown-item>
                        <b-dropdown-divider></b-dropdown-divider>
                        <b-dropdown-item href="#" router-link to="/user/userplanlist">My Plan</b-dropdown-item>
                        <b-dropdown-item href="#" router-link to="/hotplace">Hotplace</b-dropdown-item>
                        <b-dropdown-divider></b-dropdown-divider>
                        <b-dropdown-item href="#" @click="logoutConfirm">로그아웃</b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>

            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
import {mapActions, mapState} from "vuex";

export default {
    name: "HeaderNaviBar",
    computed:{
        ...mapState("userStore", ["isLogin","userId"])
    },
    methods:{
        ...mapActions("userStore",["userLogout","getUserInfo"]),
        async logoutConfirm(){
            await this.userLogout(this.userId);
            alert("로그아웃 되었습니다.");
        },
        async moveUserInformation(){
            await this.getUserInfo(this.userId);
        }
    }
};
</script>

<style >
.navbar {
    font-size: 15px;
}

</style>

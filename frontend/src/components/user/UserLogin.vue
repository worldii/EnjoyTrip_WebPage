<script>
import {mapActions, mapState} from "vuex";

const userStore = "userStore";

export default {
    name: "UserLogin",
    
    data() {
        return {
            user:{userId:'',password:''}
        }
    },
    computed: {
        ...mapState(userStore, ["isLogin"]),
        invalidFeedback() {
            return 'Validate Result'
        }
    },
    methods: {
        ...mapActions(userStore, ["userLogin"]),
        async loginConfirm(){

            await this.userLogin(this.user);

            if(this.isLogin){
                this.$router.push({ name: "home" });
                alert("로그인 되었습니다.");
            }else{
                alert("Id, Password 를 확인하세요");
            }

        },
        moveFindPassword(){
            this.$router.push({ name: "findPassword" });
        }
    },

}
</script>

<template>
    <b-container class="mt-5" id="loginContainer">
        <b-row>
            <b-col></b-col>
            <b-col cols="6">
                <p class="text-center font-weight-bold" id="login-text">로그인</p>
            </b-col>
            <b-col></b-col>
        </b-row>

        <b-row class="mt-5">
            <b-col></b-col>
            <b-col cols="4">
                <b-form>
                    <b-form-group :invalid-feedback="invalidFeedback">
                        <b-form-input id="user-login-id-input" type="text" placeholder="아이디를 입력하세요" v-model="user.userId" trim></b-form-input>
                    </b-form-group>
                    <b-form-group :invalid-feedback="invalidFeedback">
                        <b-form-input id="user-login-password-input" type="password" v-model="user.password" placeholder="비밀번호를 입력하세요"
                                      trim></b-form-input>
                    </b-form-group>
                </b-form>
                <router-link to="/user/findPassword">
                    <p class="text-right" id="user-login-find-link" @click="moveFindPassword"> 비밀 번호 찾기 ></p>
                </router-link>
            </b-col>
            <b-col></b-col>
        </b-row>

        <b-row class="mt-3">
            <b-col></b-col>
            <b-col>
                <b-button id="user-login-button" @click="loginConfirm" block>로그인</b-button>
            </b-col>
            <b-col></b-col>
        </b-row>
        <b-row class="mt-5">
            <b-col></b-col>
            <b-col>
                <div class="well">
                    <h4 class="text-divider"><span>Or continue with</span></h4>
                </div>
            </b-col>
            <b-col></b-col>
        </b-row>
        <b-row class="mt-4">
            <b-col></b-col>
            <b-col>
                <b-container>
                    <b-row>
                        <b-col>
                            <b-button class="user-icon-login-button" block>
                                <b-icon icon="google"></b-icon>
                            </b-button>
                        </b-col>
                        <b-col>
                            <b-button class="user-icon-login-button" block>
                                <b-icon icon="twitter"></b-icon>
                            </b-button>
                        </b-col>
                        <b-col>
                            <b-button class="user-icon-login-button" block>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                     class="bi bi-apple" viewBox="0 0 16 16">
                                    <path d="M11.182.008C11.148-.03 9.923.023 8.857 1.18c-1.066 1.156-.902 2.482-.878 2.516.024.034 1.52.087 2.475-1.258.955-1.345.762-2.391.728-2.43Zm3.314 11.733c-.048-.096-2.325-1.234-2.113-3.422.212-2.189 1.675-2.789 1.698-2.854.023-.065-.597-.79-1.254-1.157a3.692 3.692 0 0 0-1.563-.434c-.108-.003-.483-.095-1.254.116-.508.139-1.653.589-1.968.607-.316.018-1.256-.522-2.267-.665-.647-.125-1.333.131-1.824.328-.49.196-1.422.754-2.074 2.237-.652 1.482-.311 3.83-.067 4.56.244.729.625 1.924 1.273 2.796.576.984 1.34 1.667 1.659 1.899.319.232 1.219.386 1.843.067.502-.308 1.408-.485 1.766-.472.357.013 1.061.154 1.782.539.571.197 1.111.115 1.652-.105.541-.221 1.324-1.059 2.238-2.758.347-.79.505-1.217.473-1.282Z"/>
                                    <path d="M11.182.008C11.148-.03 9.923.023 8.857 1.18c-1.066 1.156-.902 2.482-.878 2.516.024.034 1.52.087 2.475-1.258.955-1.345.762-2.391.728-2.43Zm3.314 11.733c-.048-.096-2.325-1.234-2.113-3.422.212-2.189 1.675-2.789 1.698-2.854.023-.065-.597-.79-1.254-1.157a3.692 3.692 0 0 0-1.563-.434c-.108-.003-.483-.095-1.254.116-.508.139-1.653.589-1.968.607-.316.018-1.256-.522-2.267-.665-.647-.125-1.333.131-1.824.328-.49.196-1.422.754-2.074 2.237-.652 1.482-.311 3.83-.067 4.56.244.729.625 1.924 1.273 2.796.576.984 1.34 1.667 1.659 1.899.319.232 1.219.386 1.843.067.502-.308 1.408-.485 1.766-.472.357.013 1.061.154 1.782.539.571.197 1.111.115 1.652-.105.541-.221 1.324-1.059 2.238-2.758.347-.79.505-1.217.473-1.282Z"/>
                                </svg>
                            </b-button>
                        </b-col>
                    </b-row>
                </b-container>

            </b-col>

            <b-col>
            </b-col>
        </b-row>
    </b-container>
</template>

<style scoped>
@import "@/assets/css/global.css";

#loginContainer {
    font-family: "IBM-Plex-Sans-KR-regular";

}
p {
    font-size: 1.2em;
    color: var(--primary-color);
}

input {
    background-color: var(--form-background-color);
    border: none;
    border-radius: 7px;
}

input::placeholder {
    font-size: 0.7rem;
}



#user-login-find-link {
    font-size: 0.7em;
    color: var(--light-gray-color);
}

.user-icon-login-button {
    background-color: white;
    border-color: var(--light-gray-color);
    border-radius: 7px;
    color: black;
}

img {
    object-fit: fill;
    height: 10px;
}

.text-divider{
    margin: 2em 0;
    font-size: 0.5em;
    line-height: 0;
    text-align: center;
}

.text-divider span{
    background-color: white;
    padding: 1em;
    color: var(--light-gray-color);
}


.text-divider:before{ content: " "; display: block; border-top: 1px solid #e3e3e3; border-bottom: 1px solid #f7f7f7;}


#user-login-button{
    background-color: var(--primary-color);
    border: none;
    border-radius: 7px;
    color: white;
}
</style>
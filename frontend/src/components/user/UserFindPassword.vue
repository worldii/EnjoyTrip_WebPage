<template>
    <b-container class="mt-5" id="password-container">
          <b-row>
                <b-col></b-col>
                <b-col cols="6">
                    <p class="text-center font-weight-bold" id="login-text">비밀 번호 찾기</p>
                </b-col>
                <b-col></b-col>
            </b-row>


            <b-row class="mt-5">
                <b-col></b-col>
                <b-col cols="4">
                    <b-form>
                        <b-form-group>
                            <b-form-input id="user-login-id-input" type="text" placeholder="아이디를 입력하세요" v-model="user.userId" trim></b-form-input>
                        </b-form-group>
                        <b-form-group >
                            <b-form-input id="user-login-password-input" type="password" v-model="user.name" placeholder="이름을 입력하세요" trim></b-form-input>
                        </b-form-group>
                    </b-form>
                </b-col>
                <b-col></b-col>
            </b-row>
            <b-row class="mt-3">
                <b-col></b-col>
                <b-col>
                    <b-button id="user-login-button" @click="findPasswordConfirm" block>비밀번호 찾기</b-button>
                </b-col>
                <b-col></b-col>
            </b-row>
    </b-container>

</template>

<script>
import { mapActions, mapState } from "vuex";

export default {
    data() {
        return {
            user : {
                userId: "",
                name: "",
            },
        };
    },
    
    methods: {
        ...mapActions("userStore", ["getUserInfo"]),
        async findPasswordConfirm() {

            console.log(this.user.userId);
            const res = await this.getUserInfo(this.user.userId);

            console.log(res);
        }
    },
      computed: {
        ...mapState("userStore", ["isLogin", "userId"])
    }, 
};
</script>

<style scoped>

@import "@/assets/css/global.css";

#password-container {
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



</style>


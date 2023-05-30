<script >
import {mapActions, mapState} from "vuex";

export default {
    name:"UserInformation",
    data() {
        return {
            modifyUser:{
                userId:"",
                password:"",
                address:"",
                name:"",
                email:"",
            },
            check:"",
            modify : false,
        }
    },
    methods: {
        ...mapActions("userStore",["userModify","userLeave"]),
        modifyStart() {
            this.modifyUser.userId = this.userInfo.userId;
            this.modifyUser.password = "";
            this.modifyUser.address = this.userInfo.address;
            this.modifyUser.name = this.userInfo.name;
            this.modifyUser.email = this.userInfo.name;
            this.modify = true;
        },
        async modifyEnd(){
            await this.userModify(this.modifyUser);
            this.modify = false;
        },
        async userLeaveConfirm(){
            await this.userLeave(this.userInfo.userId);
        }

    },
    computed: {
        ...mapState("userStore", ["userInfo"])
    }
}
</script>

<template>
    <b-container class="mt-5">
        <b-row>
            <b-col></b-col>
            <b-col cols="6">
                <p class="text-center font-weight-bold" id="user-information-title">
                    내 정보 보기
                </p>
            </b-col>
            <b-col></b-col>
        </b-row>

        <b-row class="mt-5">
            <b-col></b-col>
            <b-col cols="6">
                <b-form>
                    <b-form-group label="아이디">
                        <b-form-input type="text" v-model="this.userInfo.userId" trim disabled></b-form-input>
                    </b-form-group>

                    <b-form-group label="이름">
                        <b-form-input type="text" v-model="modifyUser.name" trim v-if="modify"></b-form-input>
                        <b-form-input type="text" v-model="this.userInfo.name" trim v-else disabled></b-form-input>
                    </b-form-group>

                    <b-form-group label="이메일">
                        <b-form-input type="email" v-model="modifyUser.email" v-if="modify" trim></b-form-input>
                        <b-form-input type="text" v-model="this.userInfo.email" trim v-else disabled></b-form-input>
                    </b-form-group>

                    <b-form-group label="주소">
                        <b-form-input type="email" v-model="modifyUser.address" v-if="modify" trim></b-form-input>
                        <b-form-input type="email" v-model="this.userInfo.address" v-else trim disabled></b-form-input>
                    </b-form-group>

                    <b-form-group label="비밀번호">
                        <b-form-input type="password" v-model="modifyUser.password" v-if="modify" trim></b-form-input>
                        <b-form-input type="password" v-model="this.userInfo.password" v-else trim disabled></b-form-input>
                    </b-form-group>

                    <b-form-group label="비밀번호 확인" v-if="modify">
                        <b-form-input type="password" v-model="modifyUser.check" trim></b-form-input>
                    </b-form-group>

                    <b-button block v-if="modify" @click="modifyEnd">수정완료</b-button>
                </b-form>
            </b-col>
            <b-col></b-col>
        </b-row>

        <b-row v-if="!modify">
            <b-col></b-col>
            <b-col cols="6">
                <b-row>
                    <b-col cols="6"></b-col>
                    <b-col>
                        <b-button @click="modifyStart" block>수정</b-button>
                    </b-col>
                    <b-col>
                        <b-button @click="userLeaveConfirm" block>탈퇴</b-button>
                    </b-col>
                </b-row>
            </b-col>
            <b-col>
            </b-col>
        </b-row>
    </b-container>
</template>

<style scoped>
button {
    background-color: var(--primary-color);
    border: none;
    border-radius: 7px;
    color: white;
}

p {
    font-size: 1.2em;
    color: var(--primary-color);
}

input:disabled {
    background-color: var(--form-background-color);
    border: none;
    border-radius: 7px;
}

input{
    background-color: white;
}

fieldset{
    font-size: 0.7rem;
}
</style>
<template>
    <b-container class="p-5">
        <BeforeSite></BeforeSite>
        <b-row>
            <b-col></b-col>
            <b-col cols="6">
                <p class="text-center font-weight-bold" id="article-detail">세부 사항</p>
            </b-col>
            <b-col></b-col>
        </b-row>
        <b-row>
            <b-col></b-col>
            <b-col cols="10">
                <div id="form-title">{{ article.subject }}</div>
            </b-col>
            <b-col>
            </b-col>
        </b-row>
        <b-row class="pt-3">
            <b-col></b-col>
            <b-col cols="5" class="text-left">{{ article.currentUpdate }}</b-col>
            <b-col cols="5" class="text-right">작성자 : {{ article.userId }}</b-col>
            <b-col></b-col>
        </b-row>
        <b-row class="pt-3">
            <b-col></b-col>
            <b-col cols="10">
                <b-form-textarea  readonly id="textarea-large" size="lg" :value="article.content"></b-form-textarea>
            </b-col>
            <b-col></b-col>
        </b-row>
        <b-row class="pt-3">
            <b-col></b-col>
               <b-col cols="10">
                    <div v-if="imageUrl && imageUrl.length> 0" id="imageUrlTemplate">
                        <b-col cols="10">
                                <img v-for="(image, index) in imageUrl" :key="index" :src="image" id="imageShow">
                        </b-col>
                    </div>
                </b-col>
            <b-col></b-col>
        </b-row>
        <!-- 작성자 일때만 수정 및 삭제기 가능해야 함 -->
        <b-row class="mt-2">
            <b-col></b-col>
            <b-col  cols="10" class="text-right">
                <b-button class="m-1" id="modifyBoardButton" @click="modifyBoard">수정</b-button>
                <b-button id="deleteBoardButton" @click="deleteBoard">삭제</b-button>
            </b-col>
            <b-col></b-col>
        </b-row>
        <!-- Comment Part -->
        <CommentList :boardId="parseInt(this.$route.params.boardId)"></CommentList>
</b-container>
</template>

<script>
import http from "@/assets/js/community/http";
import CommentList from '@/components/community/CommentList.vue';
import { mapState } from "vuex";
import BeforeSite from "../common/BeforeSite.vue";
export default {

    computed: {
        ...mapState("userStore", ["isLogin", "userId"]),
    },
    name: "CommunityDetail",
     data() {
        return {
            article: {
                boardId: 0,
                subject: "",
                content: "",
                userId: "",
                hit: 0,
                currentUpdate: "",
            },
            imageUrl: [],

        };
    },
    methods: {
        modifyBoard() {
            this.$router.push({
                name: "boardmodify",
                params: {
                    boardId: this.$route.params.boardId,
                },
            });
        },
        deleteBoard() {
            http.delete(`/board/${this.$route.params.boardId}?userId=${this.userId}`).then(() => {
                alert("삭제되었습니다.");
                this.$router.push("/community/list/1");
            }).catch((error) => {
                alert(error.response.data.error.message);
            })
        },
    },
    created() { 
        http.get(`/board/${this.$route.params.boardId}`).then((response) => {
            this.article = response.data.response;
        });

        http.post(`/board/hit/${this.$route.params.boardId}`).then(() => {
        }).catch((error) => {
            console.log(error);
        })

        http.get(`/board/file/${this.$route.params.boardId}`).then((response) => {
            this.imageUrl = []

            response.data.response.map((image) => {
                this.imageUrl.push(image.fileUrl);
            })
        }).catch((error) => {
            console.log(error);
            this.imageUrl = [];
        })
    },
    components: {
    CommentList,
    BeforeSite
}
};
</script>

<style scoped>
@import "@/assets/css/global.css";
#article-detail {
    color: var(--primary-color);
    font-family: "IBM-Plex-Sans-KR-regular";
}

#form-title {
    border: none;
    border-bottom: solid 1px;
    font-weight: bold;
    font-size: 20px;
    border-radius: 0px;
    font-family: 'IBM-Plex-Sans-KR-regular';
}

#textarea-large {
    font-family: 'IBM-Plex-Sans-KR-regular';
    height: 400px;
    font-size: 15px;
    background-color: #f8f9fa;
}

#imageUrlTemplate {
    margin-top: 10px;
    border: solid 1px;
    border-radius: 5px;
}#imageShow {
    width: 100px;
}
</style>


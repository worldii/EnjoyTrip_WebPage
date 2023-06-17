<template>
    <b-container class="p-5">
        <b-row>
            <b-col></b-col>
            <b-col cols="6">
                <p class="text-center font-weight-bold" id="article-modify">글 수정 </p>
            </b-col>
            <b-col></b-col>
        </b-row>
        <b-row>
            <b-col></b-col>
            <b-col cols="10">
                <b-form-input :placeholder="article.subject" id="form-title" v-model="article.subject"></b-form-input>
            </b-col>
            <b-col></b-col>
        </b-row>
        <b-row class="p-3"> </b-row>
        <b-row>
            <b-col></b-col>
            <b-col cols="10">
                <b-form-textarea id="textarea-large" size="lg" :placeholder="article.content" v-model="article.content"></b-form-textarea>
            </b-col>
            <b-col></b-col>
        </b-row>
        <UploadButton></UploadButton>
        <b-row class="mt-2">
            <b-col></b-col>
            <b-col cols="10">
                <b-button id="submitButton" @click="saveArticle">저장</b-button>
            </b-col>
            <b-col></b-col>
        </b-row>
    </b-container>
</template>

<script>
import http from "@/assets/js/community/http";
import UploadButton from "@/components/common/UploadButton.vue";
export default {
    data() {
        return {
            userId : this.$store.state.userId,
            article: {
                title: "",
                content: "",
                userId: "",
                hit: 0,
                boardType: "",
            }
        };
    },
    components: {
        UploadButton,
    },
    methods: {
        saveArticle() {
            http.put(`/board/${this.$route.params.boardId}?userId=${this.userId}`, this.article).then(() => {
                alert("게시글이 수정되었습니다.");
                this.$router.push({ name: "boardview", params: { boardId: this.$route.params.boardId } });
            }).catch((error) => {
                alert(error?.response?.data?.error?.message);
                this.$router.push({ name: "boardview", params: { boardId: this.$route.params.boardId } });
            });
        }
    },
    created() {
        http.get(`/board/${this.$route.params.boardId}`).then((response) => {
            this.article = response.data.response;
        });
    },
};
</script>

<style scoped>
#article-modify {
    color: var(--primary-color);
    font-family: "IBM-Plex-Sans-KR-regular";
}

#textarea-large {
    font-family: 'IBM-Plex-Sans-KR-regular';
    height: 400px;
    font-size: 15px;
}

#form-title {
    border: none;
    border-bottom: solid 1px;
    font-weight: bold;
    font-size: 20px;
    border-radius: 0px;
    font-family: 'IBM-Plex-Sans-KR-regular';
}

#submitButton {
    border: none;
    width: 100%;
    background-color: var(--light-gray-color);
}

#submitButton:hover {
    background-color: var(--primary-color);
    color: white;
}
</style>


<template>
    <b-container class="p-5">
    <BeforeSite></BeforeSite>
        <b-row>
            <b-col></b-col>
            <b-col cols="6">
                <p class="text-center font-weight-bold" id="article-write">글쓰기</p>
            </b-col>
            <b-col></b-col>
        </b-row>
        <b-row>
            <b-col></b-col>
            <b-col cols="10">
                <b-form-input v-model="article.subject" placeholder="제목을 입력해주세요" id="form-title"></b-form-input>
            </b-col>
            <b-col></b-col>
        </b-row>
    
        <b-row class="pt-3">
            <b-col></b-col>
            <b-col cols="10">
                <b-form-select v-model="selected" :options="options"></b-form-select>
            </b-col>
            <b-col></b-col>
        </b-row>
        <b-row class="pt-3">
            <b-col></b-col>
            <b-col cols="10">
                <b-form-textarea id="textarea-large" v-model="article.content" size="lg" placeholder="내용을 입력해주세요"></b-form-textarea>
            </b-col>
            <b-col></b-col>
        </b-row>
    
    
        <!-- UPLOAD BUTTON -->
        <b-row class="mt-2" id="uploadButtonTemplate">
            <b-col></b-col>
            <b-col cols="10">
                <input id="uploadButton" accept="image/*" type="file" ref="fileInput" @change="handleFileInputChange" multiple >
            </b-col>
            <b-col></b-col>
        </b-row>
        <b-row>
            <b-col></b-col>
            <b-col cols="10">
                <div v-if="imageUrl != []" id="imageUrlTemplate">
                    <b-col cols="10">
                        <img v-for="(image,index) in imageUrl" :key="index" :src="image" id="imageShow">
                    </b-col>
                </div>
            </b-col>
            <b-col></b-col>
        </b-row>
    
        <!-- UPload Button End -->
    
        <b-row class="mt-2">
            <b-col></b-col>
            <b-col cols="10">
                <b-button id="submitButton" @click="submitArticle">저장</b-button>
            </b-col>
            <b-col></b-col>
        </b-row>
    </b-container>
</template>

<script>
import BeforeSite from "../common/BeforeSite.vue";
import writeAxios from "@/assets/js/community/writeAxios.js";
import { mapState } from "vuex";
export default {
    components: {
        BeforeSite,
    },
    data() {
        return {
            selectedFile: [],
            imageUrl: [],
            fileList: [],
            selected: null,
            options: [
                { value: null, text: '선택해주세요' },
                { value: 'COMMUNITY', text: '커뮤니티' },
                { value: 'NOTICE', text: '공지사항' },
            ],
            article: {
                subject: "",
                content: "",
                userId: "",
            }

        }
    },
    created() {
        this.article.userId = this.userId;
    },
    methods: {
        clickFunc() {

        },

        handleFileInputChange(event) {
            this.selectedFile = event.target.files;
            this.imageUrl = [];
            for (let i = 0; i < this.selectedFile.length; i++) {
                this.imageUrl.push(URL.createObjectURL(this.selectedFile[i]));
            }
        },
        submitArticle() {
            this.article.boardType = this.selected;
            const serializedData = JSON.stringify(this.article);
            console.log(serializedData);
            const formData = new FormData();
            formData.append("json", serializedData);
            formData.append("files", null);

            for (let i = 0; i < this.selectedFile.length; i++) {
                formData.append(`files`, this.selectedFile[i]);
            }

            console.log(sessionStorage.getItem("access-token"));
            writeAxios
                .post("/board", formData, {
                    headers: {
                        "Content-Type": "multipart/form-data",
                    },
                })
                .then(() => {
                    alert("게시글이 등록되었습니다.");
                    this.$router.push("/community/list/1");
                })
                .catch(() => {
                    alert("게시판 오류가 발생하였습니다.");
                });
        },
    },

    computed: {
        ...mapState("userStore", ["isLogin", "userId"]),
    },
};
</script>

<style scoped>
#article-write {
    color: var(--primary-color);
    font-family: 'IBM-Plex-Sans-KR-regular';
    font-weight: bold;
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

#uploadButtonTemplate {
    font-family: 'IBM-Plex-Sans-KR-regular';
}

#uploadImageButton {
    background-color: var(--light-gray-color);
    border: none;
    width: 100%;
}

#uploadImageButton:hover {
    background-color: var(--primary-color);
    color: white;
}

#imageShow {
    width: 100px;
}

#imageUrlTemplate {
    margin-top: 10px;
    border: solid 1px;
    border-radius: 5px;
}
</style>


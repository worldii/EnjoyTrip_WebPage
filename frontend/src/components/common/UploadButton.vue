<template>
    <div>
        <b-row class="mt-2" id="uploadButtonTemplate">
            <b-col></b-col>
            <b-col cols="5">
                <input accept="image/*" type="file" ref="fileInput" @change="handleFileInputChange" multiple>
            </b-col>
            <b-col cols="5">
                <b-button id="uploadImageButton" @click="uploadImage">사진 업로드</b-button>
            </b-col>
            <b-col></b-col>
        </b-row>
        <b-row>
            <b-col></b-col>
            <b-col cols="10">
                <div v-if="imageUrl!=[]" id="imageUrlTemplate">
                    <b-col cols="10">
                        <img v-for="(image,index) in imageUrl" :key="index" :src="image" id="imageShow">
                    </b-col>
                </div>
            </b-col>
            <b-col></b-col>
        </b-row>
    </div>
</template>

<script>

import http from "@/assets/js/community/http";
export default {
    data() {
        return {
            selectedFile: [],
            imageUrl: [],
            fileList: [],
        };
    },

    methods: {
        handleFileInputChange(event) {
            this.selectedFile = event.target.files;
        },
        uploadImage() {
            if (this.selectedFile.length==0) {
                alert("이미지가 없습니다.")
                return;
            }
                        
            const formData = new FormData();
            for (let i = 0; i < this.selectedFile.length; i++) { 
                formData.append(`files`, this.selectedFile[i]);
            }
            http.post("/board/multiFileUploadTest", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            }).then(() => {
            }).catch((error) => {
                console.log(error);
            });
        }
    }
}
</script>

<style scoped>
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
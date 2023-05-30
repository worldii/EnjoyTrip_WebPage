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
            console.log(this.selectedFile);
        },
        uploadImage() {
            if (this.selectedFile.length==0) {
                alert("이미지가 없습니다.")
                return;
            }
            console.log(this.selectedFile);
            
            const formData = new FormData();
            for (let i = 0; i < this.selectedFile.length; i++) { 
                formData.append(`files`, this.selectedFile[i]);

                console.log(this.selectedFile[i]);
            }
            console.log(formData);

            http.post("/board/multiFileUploadTest", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            }).then((response) => {
                console.log("Response Data", response.data);
               // this.fileList = response.data;
            
                // // image url 로 보여주기 위해 변환
                // this.fileList.map((file) => {
                //     this.imageUrl.push("file:"+file.filePath);
                // });

                // console.log(this.imageUrl);

            }).catch((error) => {
                console.log(error);
            });
            // 현재는 그냥 보여주기만.
            //this.imageUrl = URL.createObjectURL(this.selectedFile);
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
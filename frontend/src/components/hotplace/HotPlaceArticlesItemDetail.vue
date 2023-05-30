<template>
    <b-container id="hot-place-article-item-detail">
      
        <BeforeSite></BeforeSite>
        <b-row>
            <b-col></b-col>
            <b-col cols="6">
                <p class="text-center font-weight-bold" id="hot-place-text">핫플 공유</p>
            </b-col>
            <b-col></b-col>
        </b-row>
    
        <b-row class="p-3">
            <b-col class="text-center" id="file-upload" style="font-size: 30px;">
                {{ article.hotPlaceName }}
            </b-col>
        </b-row>
        <b-row>
            <b-col> 작성자 : {{ article.userId }}</b-col>
            <b-col cols="6"></b-col>
            <b-col class="text-right">{{ article.createAt }}</b-col>
        </b-row>
        <b-row class="pt-3">
            <b-col class="text-center" v-if="article.imageUrl">
                <img :src="article.imageUrl" id="imageShow">
            </b-col>
    
        </b-row>
        <b-row class="pt-3" v-if="article.content">
            <b-col>
                <b-form-textarea readonly id="textarea-large" size="sm" :value="article.content"></b-form-textarea>
            </b-col>
        </b-row>
    
    </b-container>
</template>

<script>
import http from "@/assets/js/hotplace/http";
import BeforeSite from "../common/BeforeSite.vue";

export default {
    data() {
        return {
            article: {},
        };
    },
    methods: {
   
    },
    created() {
        // article created router id 를 통해서
        http.get(`/hotplace/article/${this.$route.params.hotPlaceArticleId}`).then((response) => {
            console.log(response.data);
            this.article = response.data.response;
        });
    },
    components: { BeforeSite }
};
</script>

<style scoped>
#hot-place-text {
    color: var(--primary-color);
    font-family: "IBM-Plex-Sans-KR-regular";
}

#file-upload {
    border: none;
    border-bottom: 1px solid var(--light-gray-color);
    font-size: 1rem;
}

#hot-place-article-item-detail {
    font-family: "IBM-Plex-Sans-KR-regular";
}

#imageShow {
    height: 500px;
}


</style>


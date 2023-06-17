<template>
    <b-container class="m-3" id="hot-place-detail-container">
        <BeforeSite></BeforeSite>
    
        <b-row>
            <b-col></b-col>
            <b-col>
                <HotPlaceItem :hotPlace="hotPlace"></HotPlaceItem>
            </b-col>
            <b-col></b-col>
        </b-row>
    
        <b-row>
            <b-col></b-col>
            <b-col cols="8" class="text-center" id="vote-text">
                좋아요 수를 {{ this.hotPlace.vote }}개나 받았어요 !
            </b-col>
            <b-col></b-col>
        </b-row>
    
        <b-row class="pt-3">
            <b-col></b-col>
            <b-col class="text-center">
                <b-button style="width: 200px;height: 50px;" @click="clickVote">
                    투표하기
                </b-button>
            </b-col>
            <b-col></b-col>
        </b-row>
    
        <b-row class="p-3">
            <b-col id="file-upload">
                상위 태그 5개
            </b-col>
        </b-row>
    
        <b-row>
            <b-col></b-col>
            <b-col cols="2" class="tag p-1 text-center" v-for="tag in tags" :key="tag.tagName" @click="selectTag">
                <b-button id="hot-place-button">
                    {{ tag.tagName }}
                </b-button>
            </b-col>
            <b-col></b-col>
        </b-row>
    
        <b-row class="p-3">
            <b-col id="file-upload">
                핫플 바로 가기
            </b-col>
        </b-row>
    
        <b-row>
            <b-button style="width:100%; " @click="goToKakaoMapUrl()">카카오맵 바로가기</b-button>
        </b-row>
    
        <b-row class="p-3 mt-2">
            <b-col id="file-upload">
                유저들의 의견
            </b-col>
        </b-row>
    
        <HotPlaceArticlesItem v-for="(item, index) in hotPlaceArticles" :key="index" :article="item"></HotPlaceArticlesItem>
    </b-container>
</template>

<script>
import HotPlaceItem from './HotPlaceItem.vue';
import HotPlaceArticlesItem from './HotPlaceArticlesItem.vue';
import http from '@/assets/js/hotplace/http';
import BeforeSite from '../common/BeforeSite.vue';
export default {
    data() {
        return {
            hotPlace: {},
            vote: 100,
            isVote: false,
            tags: [],
            hotPlaceArticles: []
        };
    },
    methods: {
        selectTag() {

        },


        clickVote(e) {
            if (this.isVote) {
                http.post(`/hotplace/${this.$route.params.hotPlaceId}/unvote`).then((response) => {
                    console.log(response.data);
                    alert("투표를 취소합니다.");
                    this.isVote = !this.isVote;
                    if (this.isVote) {
                        e.target.style.backgroundColor = "var(--primary-color)";
                    } else {
                        e.target.style.backgroundColor = "#6c757d";
                    }
                    http.get(`/hotplace/${this.$route.params.hotPlaceId}`).then((response) => {
                        console.log(response.data.response);
                        this.hotPlace = response.data.response;
                    });
                }).catch((error) => {
                    console.log(error);
                });
            } else {

                http.post(`/hotplace/${this.$route.params.hotPlaceId}/vote`).then((response) => {
                    console.log(response.data);
                    alert("투표했습니다.");
                    this.isVote = !this.isVote;
                    if (this.isVote) {
                        e.target.style.backgroundColor = "var(--primary-color)";
                    } else {
                        e.target.style.backgroundColor = "#6c757d";
                    }
                    http.get(`/hotplace/${this.$route.params.hotPlaceId}`).then((response) => {
                        console.log(response.data.response);
                        this.hotPlace = response.data.response;
                    });
                }).catch((error) => {
                    console.log(error);
                    alert(error.response.data.error.message)
                });
            }
        },

        goToKakaoMapUrl() {
            console.log(this.hotPlace.placeUrl);
            window.open(this.hotPlace.placeUrl);
        }
    },
    components: { HotPlaceItem, HotPlaceArticlesItem, BeforeSite },
    created() {
        console.log(this.$route.params.hotPlaceId);
        http.get(`/hotplace/${this.$route.params.hotPlaceId}`).then((response) => {
            console.log(response.data.response);
            this.hotPlace = response.data.response;
        });

        http.get(`/hotplace/articleAll/${this.$route.params.hotPlaceId}`).then((response) => {
            console.log(response.data);
            this.hotPlaceArticles = response.data.response;
        });

        http.get(`/hotplace/${this.$route.params.hotPlaceId}/tag`).then((response) => {
            console.log(response.data);
            this.tags = response.data.response;
        });
    },
};
</script>

<style scoped>
@import "@/assets/css/global.css";


#vote-text {
    font-size: 40px;
}

#file-upload {
    border: none;
    border-bottom: 1px solid var(--light-gray-color);
    font-size: 1rem;
    font-weight: bold;
}

#hot-place-button {
    background-color: var(--primary-color);
}

#hot-place-detail-container {
    font-family: "IBM-Plex-Sans-KR-regular";
}
</style>


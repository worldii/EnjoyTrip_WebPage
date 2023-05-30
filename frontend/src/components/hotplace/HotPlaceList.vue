<template>
    <b-col>
        <b-row>
            <b-col></b-col>
            <b-col cols="6">
                <p class="text-center font-weight-bold" id="hot-place-text">핫플 공유</p>
            </b-col>
            <b-col></b-col>
        </b-row>
    
        <!-- 핫플 추가 버튼 -->
        <b-row>
            <b-col></b-col>
            <b-col class="text-center">
                <b-icon id="circle-button" icon="plus-circle-fill" @click="addArticle"></b-icon>
            </b-col>
            <b-col></b-col>
        </b-row>

         <b-row id="searchBar" class="p-5">
            <b-col></b-col>
            <b-col>
                <b-input type="text" placeholder="게시글을 검색합니다."  v-model="searchText"/>
            </b-col>
            <b-button id="searchBarButton" @click="searchList">검색</b-button>
            <b-col></b-col>
        </b-row>
        
        <b-row v-if="items&& items.length>0" cols="1" cols-md="3" >
            <HotPlaceItem v-for="(item, index) in items" :key="index" :hotPlace="item" >
            </HotPlaceItem>
        </b-row>
        <b-row v-else>
            <b-col class="text-center" > 게시글이 없습니다.</b-col>
        </b-row>
    </b-col>
</template>

<script>
import http from '@/assets/js/hotplace/http';
import HotPlaceItem from '@/components/hotplace/HotPlaceItem.vue';
export default {
    name: 'HotPlaceList',
    data() {
        return {
            items: [],
            searchText: "",
        };
    },
 
    methods: {
        addArticle() {
            this.$router.push({ name: "hotPlaceWrite" });
        },
        searchList() {
            http.get(`/hotplace/search?keyword=${this.searchText}`).then((response) => {
                console.log(response.data);
                console.log(this.searchText);
                this.items = response.data.response;
            });
        },

    },

    components: {  HotPlaceItem },
    created() {
        http.get('/hotplace/').then((response) => {
            console.log(response.data);
            this.items = response.data.response;
            console.log(this.items);
        });
    }

};
</script>

<style scoped>
@import "@/assets/css/global.css";
#hot-place-text {
    color: var(--primary-color);
    font-family: "IBM-Plex-Sans-KR-regular";
}

#circle-button {
    color: var(--primary-color);
    font-size: 50px;
}
</style>


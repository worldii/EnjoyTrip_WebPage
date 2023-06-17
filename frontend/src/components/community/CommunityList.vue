<template>
    <b-container class="m-3">
        <b-row>
            <b-col></b-col>
            <b-col cols="6">
                <p class="text-center font-weight-bold" id="community-text">게시판</p>
            </b-col>
            <b-col></b-col>
        </b-row>

        <!-- 게시글 추가 버튼 -->
        <b-row>
            <b-col cols="1"></b-col>
            <b-col class="text-center">
                <b-icon id="circle-button" icon="plus-circle-fill" @click="addArticle"></b-icon>
            </b-col>
            <b-col cols="1"></b-col>
        </b-row>

        <b-row class="p-3">
                    <b-col cols="1"></b-col>
                    <b-col>
                    <b-input-group>
                        <b-form-select v-model="search.category" :options="options" ></b-form-select>
                        <b-form-input class="w-75"  placeholder="검색어를 입력해주세요" v-model="search.keyword">
                        </b-form-input>
                        <b-input-group-append>
                            <b-button id="plan-search-input-btn" @click="searchConfirm">
                                <b-icon-search></b-icon-search>
                            </b-button>
                        </b-input-group-append>
                    </b-input-group>
                    </b-col>
                    <b-col cols="1"></b-col>
        </b-row>
      

        <b-row>
            <b-col cols="1"></b-col>
            <b-col>
                <b-table id="community-table" hover :items="articles" :fields="fields" @row-clicked="viewArticle" :current-page="currentPage">
                  <template #cell(boardType)="data">
                    <span>{{formatName(data.item.boardType)}}</span>
                <router-link :to="{ name: 'boardview', params: { boardId: data.item.boardId } }">
                </router-link>
              </template>
                </b-table>
            </b-col>
            <b-col cols="1"></b-col>
        </b-row>
        <b-row class="p-4">
            <b-col cols="1"></b-col>
            <b-col @click="getList">
                <b-row>
                    <b-col></b-col>
                    <b-col>
                        <b-pagination pills  v-if="rows > 0" id="community-pagination" v-model="currentPage" :total-rows="rows" :per-page="perPage" aria-controls="community-table" ></b-pagination>
                    </b-col>
                    <b-col></b-col>
                </b-row>
            </b-col>
            <b-col cols="1"></b-col>
        </b-row>
    </b-container>
</template>

<script>
import http from "@/assets/js/community/http";
import { mapState } from "vuex";

export default {
    name: 'CommunityList',
 
    data() {
        return {
            search : {
                keyword : null,
                category : null,
            },
            perPage: 10,
            currentPage: 1,
            endPage : 0,
            articles: [
            ],
            fields: [
                { key: "boardId", label: "글번호", tdClass: "tdClass" },
                { key: "boardType", label: "종류", tdClass: "tdClass" },
                { key: "subject", label: "제목", tdClass: "tdSubject" },
                { key: "userId", label: "작성자", tdClass: "tdClass" },
                { key: "currentUpdate", label: "작성일", tdClass: "tdClass" },
                { key: "hit", label: "조회수", tdClass: "tdClass" },
            ],
               options: [
                { value: null, text: "카테고리" },
                { value: "NOTICE", text: "공지사항" },
                { value: "COMMUNITY", text: "커뮤니티" },
            ],
        };
    },

    created() {
        this.currentPage = this.$route.params.currentPage;
          http.get(`/board/list/${this.currentPage}`).then((response) => {
            this.articles = response.data?.boards?.page;
              this.currentPage = this.$route.params.currentPage;
              this.endPage = response.data?.boards?.endPage;            
        }).catch((error) => {
            console.log(error);
        });
    },

    computed: {
         ...mapState("userStore", ["isLogin", "userId"]),

        rows() {
            return this.endPage * this.perPage;
        }
    },

    methods: {
        formatName(value) {
            if (value === "NOTICE") {
                return "공지사항";
            } else if (value === "COMMUNITY") {
                return "커뮤니티";
            }
        },

        searchConfirm() {
            console.log(this.search);
            console.log(this.currentPage)
            let url = "/board/list/search?";
            if (this.search.category != null) {
                url += `category=${this.search.category}&`;
            }
            if (this.search.keyword != null) {
                url += `keyword=${this.search.keyword}`;
            }
            http.get(url).then((response) => {
                console.log(response.data);
                this.articles = response.data?.boards?.page;
            });
        },
        addArticle() {
            this.$router.push('/community/write');
        },
        viewArticle(article) {
            this.$router.push({
                name: "boardview",
                params: { boardId: article.boardId },
            });
        },
        getList() { 
            http.get(`/board/list/${this.currentPage}`
            ).then((response) => {
                console.log(response.data);
                this.articles = response.data?.boards?.page;

            });
            this.$router.push({
                name: "boardListPage",
                params: { currentPage: this.currentPage },
            }).catch(() => {
            });
        },
    },
   
};
</script>

<style scoped>
@import "@/assets/css/global.css";
#community-text {
    color: var(--primary-color);
    font-family: "IBM-Plex-Sans-KR-regular";
}

#circle-button {
    color: var(--primary-color);
    font-size: 50px;
}

#community-table {
    font-family: 'IBM-Plex-Sans-KR-regular';
}

#community-pagination {
    color: var(--primary-color);
}
</style>
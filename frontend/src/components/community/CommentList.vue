<template>
    <div id="commentContainer">
        <b-row class="p-3">
            <b-col></b-col>
            <b-col cols="10" id="commentTitle"> 댓글 {{ commentList.length }}개
            </b-col>
            <b-col></b-col>
        </b-row>
        <commentItem v-for="comment in commentList" :key="comment.id" :comment="comment" @deleteComment="deleteComment" @modifyCommentComplete="modifyCommentComplete">
        </commentItem>
        <b-row class="pt-2">
            <b-col></b-col>
            <b-col cols="9">
                <b-form-textarea id="textarea-large" size="lg" placeholder="댓글을 입력해주세요" style="width: 100%;" v-model="content"></b-form-textarea>
            </b-col>
            <b-col cols="1">
                <b-button style="height: 100%;" @click="submitComment"> 입력 </b-button>
            </b-col>
            <b-col></b-col>
        </b-row>
    </div>
</template>

<script>
import commentItem from "@/components/community/CommentListItem.vue";
import http from "@/assets/js/community/http";
import { mapState } from "vuex";
export default {
    props: {
        boardId: {
            type: Number,
            required: true,
        },
    },
    data() {
        return {
            commentList: [],
            content: "",

        };
    },
    components: {
        commentItem,
    },
    methods: {
        submitComment() {
            if (!this.isLogin) {
                alert("로그인이 필요한 서비스입니다.");
                this.$router.push("/user/login");
                return;
            }
            if (this.content == "") {
                alert("댓글을 입력해주세요");
                return;
            }
            http.post(`/comment/${this.boardId}`,
                {
                    boardId: this.boardId,
                    content: this.content,
                    userId: this.$store.state.userStore.userId,
                }, {
                    headers: {
                    "access-token": sessionStorage.getItem("access-token"),
                },
                }).then(() => {
                http.get(`/comment/${this.boardId}`).then((response) => {
                    this.commentList = response.data.response;
                    alert("댓글이 등록되었습니다.");
                    this.content = "";
                }).catch((error) => {
                    console.log(error);
                });
            }).catch((error) => {
                console.log(error);
            });
        },
        deleteComment(commentId) {
            http.delete(`/comment/${this.boardId}/${commentId}`).then(() => {
                alert("댓글이 삭제되었습니다.");
                http.get(`/comment/${this.boardId}`).then((response) => {
                    this.commentList = response.data.response;
                }).catch((error) => {
                    console.log(error);
                });
            }).catch((error) => {
                console.log(error);
            });
        },

        modifyCommentComplete(commentId, content) {
            http.put(`/comment/${commentId}`, {
                content: content,
                boardId: this.boardId,

                userId: this.$store.state.userStore.userId,
            }).then(() => {
                alert("댓글이 수정되었습니다.");
                http.get(`/comment/${this.boardId}`).then((response) => {
                    this.commentList = response.data.response;
                }).catch((error) => {
                    console.log(error);
                });
            }).catch((response) => {
                alert(response);
            });
        },

    },
    created() {

        http.get(`/comment/${this.boardId}`).then((response) => {
            this.commentList = response.data.response;
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
};
</script>

<style scoped>
#commentContainer {
    font-family: "IBM-Plex-Sans-KR-regular";
}

#commentTitle {
    font-size: 20px;
    font-weight: bold;
    border: none;
    border-bottom: 1px solid
}
</style>


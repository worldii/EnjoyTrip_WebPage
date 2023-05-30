<template>
    <b-container class="m-3">
        <b-row>
            <b-col></b-col>
            <!-- profile Picture -->
            <b-col cols="2">
    
                <b-row>프로필 사진</b-row>
                <b-row>{{ comment.userId }}</b-row>

            </b-col>
            <b-col cols="8">
                <b-row> 
                    <span v-if="isModify">
                        <b-form-textarea id="textarea-large"  size="sm" placeholder="내용을 입력해주세요" v-model="content"></b-form-textarea>
                        <b-button id="submitButton" @click="modifyCommentComplete">저장</b-button>
                    </span>
                    <span v-else>
                        {{ comment.content }}
                    </span>
                </b-row>
                <b-row style="font-size: 15px;">
                    {{ comment.currentUpdate }}
                </b-row>
                <b-row style="font-size: 12px;"  v-if="comment.userId == this.$store.state.userStore.userId">
                    <span class="mr-1" @click="modifyComment">수정 </span>
                    <span @click="deleteComment">삭제</span>
                </b-row>
            </b-col>
            <b-col></b-col>
        </b-row>
    </b-container>
</template>

<script>
export default {
     data() {
        return {
            isModify: false,
            content : "",
        };
    },
    props: {
        comment: {
            type: Object,
            required: true,
        },
    },
    methods: {
        deleteComment() {
            console.log("deleteComment");
            this.$emit("deleteComment", this.comment.commentId);
        },
        modifyComment () {
            console.log("modifyComment");
            this.isModify = !this.isModify;
        },
        modifyCommentComplete () {
            console.log("modifyCommentComplete");
            console.log(this.comment.commentId, this.content)
            this.$emit("modifyCommentComplete", this.comment.commentId, this.content);
            this.isModify = !this.isModify;
        },

    },
};
</script>

<style scoped>

</style>


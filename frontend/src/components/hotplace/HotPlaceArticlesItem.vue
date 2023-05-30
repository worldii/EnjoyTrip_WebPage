<template>
    <b-container id="hot-place-article-container" class="p-2" @click="articleDetail">
        <b-row>
            <b-col id="hot-place-article-userid"> {{ article.userId }} 님의 의견
            </b-col>
            <b-col cols="6"></b-col>
            <b-col class="text-right">
                {{ article.createAt }}
            </b-col>
        </b-row>
    
    </b-container>
</template>

<script>
export default {
    methods: {
        articleDetail() {
            console.log(this.article.hotPlaceArticleId);
            this.$router.push({ name: 'hotPlaceArticleView', params: { hotPlaceArticleId: this.article.hotPlaceArticleId } });
        },
    },
    props: {
        article: {
            type: Object,
            required: true,
        },
    },
};
</script>

<style scoped>

#hot-place-article-container {
    font-size: 20px;
    border: none;
    border-bottom: 1px solid #e9ecef;
    font-family: "IBM-Plex-Sans-KR-regular";
}

#hot-place-article-userid {
    font-weight: bold;
    color: var(--primary-color);
}
</style>


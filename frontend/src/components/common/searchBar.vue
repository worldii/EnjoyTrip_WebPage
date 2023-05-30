<template>
    <b-row id="searchBar" class="p-5">
        <b-col></b-col>
        <b-col>
            <b-input type="text" placeholder="게시글을 검색합니다." />
        </b-col>
        <b-button id="searchBarButton" @click="search">검색</b-button>
        <b-col></b-col>
    </b-row>
</template>

<script>
export default {
    data() {
        return {

        };
    },
    methods: {
        search() {},
    },
};
</script>

<style scoped>
#searchBar {
    font-family: "IBM-Plex-Sans-KR-regular";
    font-size: 10px;
}

#searchBarButton {
    background-color: var(--primary-color);
}
</style>
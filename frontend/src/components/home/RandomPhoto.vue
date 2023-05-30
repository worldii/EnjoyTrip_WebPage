<template>
        <b-img :src="currentPhoto" alt="Random Photo"> </b-img>
</template>

<script>
export default {
    data() {
        return {
            photosSize : 37,
            photos: [],
            currentPhoto: null
        };
    },
    mounted() {
        this.changePhoto();
    },
    computed: {
        randomPhoto() {
            return this.currentPhoto;
        }
    },
    methods: {
        changePhoto() {
            const randomIndex = Math.floor((Math.random() * this.photosSize) + 1);
            this.currentPhoto = `https://junyoung-jongha-bucket.s3.ap-northeast-2.amazonaws.com/home/picture${randomIndex }.jpg`;
        }
    },

    
};
</script>

<style scoped>
    img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }
</style>
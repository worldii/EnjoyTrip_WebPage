<template>
    <div class="p-2">
        <div id="circle-item" @click="hotplaceDetail">
            <RandomPhoto alt="이미지" id="hotPlaceImage" class="circle-image"></RandomPhoto>
            <div class="circle-text">{{ hotPlace.hotPlaceName }}</div>
        </div>
    </div>
</template>

<script>
import RandomPhoto from '../home/RandomPhoto.vue';
export default {
    data() {
        return {
            imageUrl: "",
        };
    },
    components: {
        RandomPhoto,
    },
    methods: {
        hotplaceDetail() {
            this.$router.push({
                name: 'hotPlaceDetail',
                params: {
                    hotPlaceId: this.hotPlace.hotPlaceId,
                },
            }).catch(err => {
                console.log(err);
            });
        },
    },
    props: {
        hotPlace: {
            type: Object,
            required: true,
        },
    },
};
</script>

<style scoped>
@import "@/assets/css/global.css";
#circle-item {
    position: relative;
    font-family: "IBM-Plex-Sans-KR-regular";
    width: 300px;
    aspect-ratio: 1/1;
    /* 가로:세로 비율 설정 */
    border-radius: 50%;
    overflow: hidden;
    box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.3);

}

.circle-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.circle-text {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    text-align: center;
    color: white;
    font-size: 30px;
}
</style>


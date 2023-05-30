<template>
    <div>
                <b-row class="mid-align">
                  <b-col class="pl-5 m-4">
                    <span class="title-text">한샘과 함께, EnjoyTrip</span>
                  </b-col>
                  <b-col class="m-4">
                      <random-photo></random-photo>
                  </b-col>   
                </b-row>
    </div>
</template>

<script>
import RandomPhoto from '@/components/home/RandomPhoto.vue';

export default {
  name: 'HomeView',
  components: {
    RandomPhoto
  }
}
</script>
<style scoped>

@import "../assets/css/global.css";

  .mid-align {
    display: flex;
    align-items: center;
    justify-content: center;
  
  }
  .title-text {
      font-family: 'lato-extra-bold';
      font-size: 55px;
      color: #000000;
    }
</style>
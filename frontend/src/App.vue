<template>
    <div id="app">
        <header-navi-bar></header-navi-bar>
        <router-view/>
    </div>
</template>

<style>

</style>
<script>
import HeaderNaviBar from "@/components/layout/HeaderNaviBar.vue";

export default {
    name:"app",
    components: {HeaderNaviBar},
}
</script>
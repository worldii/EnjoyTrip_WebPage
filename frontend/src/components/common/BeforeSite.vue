<template>
     <b-row>
                <b-icon id="before-page" icon="arrow-left-circle-fill" @click="beforeSite"></b-icon>
            </b-row>
</template>

<script>
export default {
    data() {
        return {

        };
    },
    methods: {
        beforeSite() {
            this.$router.go(-1);
        },
    },
};
</script>

<style scoped>

#before-page {
    color: var(--primary-color);
    font-size: 40px;
}
</style>


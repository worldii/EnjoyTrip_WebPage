<script>
import { savePlanBoard } from "@/assets/js/plan/plan";
import { mapState } from "vuex";
import router from "@/router";

export default {
    name: "PlanSave",
    computed: {
        ...mapState("userStore", ["userId"])
    },
    created() {
        console.log(this.$route.params.planBoard);
        this.planBoard.planBoardId = this.$route.params.planBoard.planBoardId;
        this.planBoard.title = this.$route.params.planBoard.title;
        this.planBoard.startDate = this.$route.params.planBoard.startDate;
        this.planBoard.endDate = this.$route.params.planBoard.endDate;
        this.planBoard.planDateMap = this.$route.params.planBoard.planDateMapTmp;
    },
    data() {
        return {
            planBoard: {
                userId: "",
                planBoardId: 0,
                title: "",
                startDate: '',
                endDate: '',
                planDateMap: {

                },
            },
        }
    },
    methods: {
        async saveConfrim() {
            this.planBoard.userId = this.userId;
            console.log(JSON.stringify(this.planBoard));
            await savePlanBoard(
                this.planBoard,
                ({ data }) => {
                    console.log(data);
                    if (data.success) {
                        alert("저장되었습니다.")
                        router.push({ name: "home" });
                    } else {
                        alert("저장에 실패하였습니다.")
                        router.push({ name: "home" });
                    }
                },
                (error) => {
                    alert("에러가 발생하였습니다");
                    console.log(error);
                    router.push({ name: "home" });

                }
            );
        },
    }
}
</script>

<template>
    <div class="p-5">
        <b-row class="mt-5">
            <b-col cols="2">
                <div class="plan-step-section">
                    <b-button id="plan-prev-step-btn" pill>
                        <b-icon-calendar-minus></b-icon-calendar-minus>
                    </b-button>
                </div>
            </b-col>
            <b-col></b-col>
            <b-col cols="2">
                <div class="plan-step-section">
                    <b-button id="plan-prev-step-btn" @click="saveConfrim" pill>
                        <b-icon-calendar-plus></b-icon-calendar-plus>
                    </b-button>
                </div>
            </b-col>
        </b-row>
        <b-row class="mt-5">
            <b-col cols="2"></b-col>
            <b-col>
                <p id="plan-title-label">{{ planBoard.title }}</p>
                <p id="plan-title-date">
                    {{planBoard.startDate}} ~ {{planBoard.endDate}}
                </p>
                <hr class="m-0">
            </b-col>
            <b-col cols="2"></b-col>
        </b-row>
        <b-row v-for="(planDate,date) in planBoard.planDateMap" :key="date">
            <b-col>
                <b-row class="mt-5">
                    <b-col cols="2"></b-col>
                    <b-col>
                        <span class="plan-date-label">
                                    <b-icon-calendar-event></b-icon-calendar-event>
                                    {{date}}
                                </span>
                    </b-col>
                    <b-col cols="2"></b-col>
                </b-row>
                <b-row class="mt-3">
                    <b-col cols="2"></b-col>
                    <b-col style="width: 100em; overflow-x: scroll;">
                        <b-list-group style="width: 100em;" horizontal>
                            <b-list-group-item v-for="(plan,index) in planDate" :key="index" class="p-2">
                                <p class="plan-place-label">
                                    {{plan.place}}
                                </p>
                                <b-input-group class="mt-1">
                                    <b-input-group-prepend>
                                        <b-input-group-text class="plan-time-label">출발</b-input-group-text>
                                    </b-input-group-prepend>
                                    <b-form-timepicker v-model="plan.startTime" class="plan-time-label"></b-form-timepicker>
                                </b-input-group>
                                <b-input-group class="mt-1">
                                    <b-input-group-prepend>
                                        <b-input-group-text class="plan-time-label">도착</b-input-group-text>
                                    </b-input-group-prepend>
                                    <b-form-timepicker v-model="plan.endTime" class="plan-time-label"></b-form-timepicker>
                                </b-input-group>
                            </b-list-group-item>
                        </b-list-group>
                    </b-col>
                    <b-col cols="2"></b-col>
                </b-row>
            </b-col>
        </b-row>
    </div>
</template>

<style scoped>
#plan-prev-step-btn {
    color: white;
    background-color: var(--primary-color);
    border-radius: 80%;
    border: none;
    text-align: center;
    text-decoration: none;
    display: inline-block;
}

#plan-next-step-btn {
    color: white;
    background-color: var(--primary-color);
    border-radius: 80%;
    border: none;
    text-align: center;
    text-decoration: none;
    display: inline-block;
}

#plan-title-label {
    font-size: 1.5em;
    font-weight: bold;
    color: black;
}

#plan-title-date {
    font-size: 1em;
    color: var(--placeholder-color);
}

.plan-date-label {
    font-size: 0.9em;
    font-weight: bold;
    color: black;
}

.plan-place-label {
    font-size: 1em;
}

.plan-time-label {
    font-size: 0.8em;
    background-color: white;
}

.plan-step-section {
    text-align: center;
}
</style>

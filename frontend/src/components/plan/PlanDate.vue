<script>
import router from "@/router";

export default {
    name: "PlanDate",
    data() {
        return {
            planBoard:{
                subject: "",
                startDate: null,
                endDate:null,
            },
        }
    },
    created() {
        this.planBoard.subject = this.$route.params.subject;
    },
    methods:{
        prevStep(){
            router.push({name:"subject"});
        },
        resetDate(){
            this.planBoard.startDate= null;
            this.planBoard.endDate= null;
        },
        nextStep(){
            router.push({name:"detail",params:{
                    planBoard:this.planBoard,
                }});
        },
        pickEndDate(){
            console.log(this.planBoard.startDate);
            console.log(this.planBoard.endDate);
        },
    },


}
</script>

<template>
    <b-container class="p-5">
        <template v-if="planBoard.startDate==null">
            <b-row class="mt-5">
                <b-col cols="2">
                    <div class="plan-step-section">
                        <b-button id="plan-prev-step-btn" @click="prevStep" pill> <b-icon-tag></b-icon-tag></b-button>
                    </div>
                </b-col>
                <b-col></b-col>
                <b-col cols="2">
                </b-col>
            </b-row>

            <b-row>
                <b-col cols="2"></b-col>
                <b-col>
                    <p id="plan-subject-label">언제 떠날 예정인가요?</p>
                    <b-calendar
                        locale="Ko"
                        v-model="planBoard.startDate"
                        selected-variant="dark"
                        block>
                    </b-calendar>
                </b-col>
                <b-col cols="2"></b-col>
            </b-row>
        </template>
        <template v-else>
            <b-row class="mt-5">
                <b-col cols="2">
                    <div class="plan-step-section">
                        <b-button id="plan-prev-step-btn" @click="resetDate" pill><b-icon-arrow-clockwise></b-icon-arrow-clockwise>  </b-button>
                    </div>
                </b-col>
                <b-col></b-col>
                <b-col cols="2">
                    <div class="plan-step-section">
                        <b-button id="plan-next-step-btn" @click="nextStep" pill> <b-icon-calendar-check    ></b-icon-calendar-check>  </b-button>
                    </div>
                </b-col>
            </b-row>

            <b-row>
                <b-col cols="2"></b-col>
                <b-col>
                    <p id="plan-subject-label">언제 돌아올 예정인가요?</p>
                    <p id="plan-date-label">
                        <b-icon-calendar-event></b-icon-calendar-event>
                        {{planBoard.startDate}}
                        ~
                        {{planBoard.endDate}}
                    </p>
                    <b-calendar
                        locale="Ko"
                        :min="planBoard.startDate"
                        v-model="planBoard.endDate"
                        selected-variant="dark"
                        @selected="pickEndDate"
                        block>
                    </b-calendar>
                </b-col>
                <b-col cols="2"></b-col>
            </b-row>
        </template>

    </b-container>
</template>

<style scoped>
.plan-step-section{
    text-align: center;
}
#plan-prev-step-btn{
    color: white;
    background-color: var(--primary-color);
    border-radius: 80%;
    border: none;
    text-align: center;
    text-decoration: none;
    display: inline-block;
}

#plan-next-step-btn{
    color: white;
    background-color: var(--primary-color);
    border-radius: 80%;
    border: none;
    text-align: center;
    text-decoration: none;
    display: inline-block;
}

#plan-subject-label{
    font-size: 1.5em;
    text-align: center;
    color: var(--placeholder-color);
}

#plan-date-label{
    font-size: 1em;
    text-align: center;
    color: black;
}

</style>
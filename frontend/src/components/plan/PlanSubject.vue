<script>
import router from "@/router";

export default {
    name: "PlanSubject",
    data() {
        return {
            planBoard: {
                planBoardId: 0,
                title: "hello",
                startDate: '2023-05-23',
                endDate: '2023-05-25',
                planDateMap: {},
                planDateMapTmp:{},
            },
        }
    },
    methods: {
        moveNextStep() {
            router.push({name:"date",params:this.planBoard});
        }
    }
}
</script>

<template>
  <b-container class="p-5">

      <b-row class="mt-5">
      </b-row>

      <b-row class="mt-5">
          <b-col cols="2"></b-col>
          <b-col>
              <p id="plan-subject-label">여행의 타이틀을 입력하세요!</p>
          </b-col>
          <b-col cols="2"></b-col>
      </b-row>

      <b-row>
          <b-col cols="2"></b-col>
          <b-col>
              <b-input-group>
                  <b-form-input v-model="planBoard.subject">
                  </b-form-input>
                  <b-input-group-append>
                      <b-button id="plan-subject-input-btn" @click="moveNextStep"><b-icon-check2></b-icon-check2></b-button>
                  </b-input-group-append>
              </b-input-group>
          </b-col>
          <b-col cols="2"></b-col>
      </b-row>
  </b-container>
</template>

<style scoped>

#plan-subject-label{
    font-size: 1.5em;
    text-align: center;
    color: var(--placeholder-color);
}

#plan-subject-input-btn{
    background-color: var(--primary-color);
    border: none;
}


</style>
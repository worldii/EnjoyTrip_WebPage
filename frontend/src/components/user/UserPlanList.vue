<script>
import {selectPlanBoardList} from "@/assets/js/plan/plan";
import router from "@/router";

export default {
    name:"UserPlanList",
    created() {
        this.setPlanBoardList();
    },
    data() {
        return {
            planBoardList:[],
        }
    },
    methods:{
        async setPlanBoardList(){
            await selectPlanBoardList(
                "test",
                ({data})=>{
                    this.planBoardList = data.list;
                },
                (error)=>{
                    console.log(error);
                }
            );
        },
        onRowSelected(planBoard){
            router.push({name:"userPlan",params:{
                    planBoardId:planBoard[0].planBoardId
                }});
        }
    }
}
</script>

<template>
<b-container>
    <b-row>
        <b-col>
            <b-table hover
                     :items="planBoardList"
                     selectable
                     @row-selected="onRowSelected"
            ></b-table>
        </b-col>
    </b-row>
</b-container>
</template>

<style scoped>

</style>
<script>
import { searchBlog, searchMap, searchRoute } from "@/assets/js/plan/plan";
import draggable from "vuedraggable";
import router from "@/router";

export default {
    name: "PlanDetail",
    components: {
        draggable,
    },
    computed: {
        hourTotalDuration() {
            return Math.floor(this.totalDuration / 3600);
        },
        minuteTotalDuration() {
            return Math.floor(this.totalDuration % 3600 / 60);
        },
        secondTotalDuration() {
            return this.totalDuration % 60;
        },
        hourDurations() {
            let hourDurations = [];
            for (let i = 0; i < this.durations.length; i++) {
                hourDurations[i] = Math.floor(this.durations[i] / 3600);
            }
            return hourDurations;
        },
        minuteDurations() {
            let minuteDurations = [];
            for (let i = 0; i < this.durations.length; i++) {
                minuteDurations[i] = Math.floor(this.durations[i] % 3600 / 60);
            }

            return minuteDurations;
        },
        secondDurations() {
            let secondDurations = [];
            for (let i = 0; i < this.durations.length; i++) {
                secondDurations[i] = this.durations[i] % 60;
            }
            return secondDurations;
        },
    },
    data() {
        return {
            totalDuration: 0,
            durations: [],
            polylines: [],
            drag: false,
            isClose: true,
            map: null,
            markers: [],
            infowindows: [],
            params: {
                query: null,
                category_group_code: null,
            },
            options: [
                { value: null, text: "카테고리" },
                { value: "AT4", text: "관광명소" },
                { value: "AD5", text: "숙박" },
                { value: "FD6", text: "음식점" },
                { value: "CE7", text: "카페" },
                { value: "MT1", text: "대형마트" },
                { value: "CS2", text: "편의점" },
                { value: "PK6", text: "주차장" },
                { value: "OL7", text: "주유소, 충전소" },
                { value: "SW8", text: "지하철역" },
                { value: "BK9", text: "은행" },
                { value: "CT1", text: "문화시설" },
                { value: "PO3", text: "공공기관" },
            ],
            colors: ['red', 'orange', 'yellow', 'green', 'blue', 'navy', 'purple'],
            selectDate: null,
            blogList: [],
            searchList: [],
            searchItem: {
                place_name: "",
            },
            planBoard: {
                planBoardId: 0,
                title: "",
                startDate: '',
                endDate: '',
                planDateMap: {},
                planDateMapTmp: {},
            },
        }
    },
    created() {
        window.addMap = (index) => {
            if (this.selectDate == null) {
                alert("날짜를 선택해 주세요!");
                return;
            }

            var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
            var imageSize = new kakao.maps.Size(24, 35);

            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
            var marker = new kakao.maps.Marker({
                position: this.markers[index].getPosition(),
                title: this.markers[index].getTitle(),
                image: markerImage
            });

            this.planBoard.planDateMap[this.selectDate].push(marker);
            this.openAccordian();
            this.drawLine();
            this.closeAllInfowindow();
        };

        this.planBoard.title = this.$route.params.planBoard.subject;
        this.planBoard.startDate = this.$route.params.planBoard.startDate;
        this.planBoard.endDate = this.$route.params.planBoard.endDate;

        for (let date = new Date(this.planBoard.startDate); date <= new Date(this.planBoard.endDate); date.setDate(date.getDate() + 1)) {
            this.planBoard.planDateMap[date.toISOString().split('T')[0]] = [];
        }
    },
    mounted() {
        if (!window.kakao || !window.kakao.map) {
            const script = document.createElement("script");
            const key = process.env.VUE_APP_KAKAO_KEY;
            script.src = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=" + key;

            /*global kakao*/
            script.addEventListener("load", () => {
                kakao.maps.load(() => {
                    this.initMap();
                });
            });

            document.head.appendChild(script);
        } else {
            kakao.maps.load(() => {
                this.initMap();
            });
        }
    },
    methods: {
        openAccordian() {
            let allSelectMarker = Object.values(this.planBoard.planDateMap);
            console.log(allSelectMarker);

            for (let i = 0; i < allSelectMarker.length; i++) {
                for (let j = 0; j < allSelectMarker[i].length; j++) {
                    allSelectMarker[i][j].setMap(null);
                }
            }

            for (let i = 0; i < this.planBoard.planDateMap[this.selectDate].length; i++) {
                this.planBoard.planDateMap[this.selectDate][i].setMap(this.map);
            }
        },
        initMap() {
            var mapContainer = document.getElementById('map'), // 지도를 표시할 div
                mapOption = {
                    center: new kakao.maps.LatLng(37.56842, 126.97060), // 지도의 중심좌표
                    level: 5, // 지도의 확대 레벨
                    mapTypeId: kakao.maps.MapTypeId.ROADMAP // 지도종류
                };
            this.map = new kakao.maps.Map(mapContainer, mapOption);

            // 지도 타입 변경 컨트롤을 생성한다
            var mapTypeControl = new kakao.maps.MapTypeControl();

            // 지도의 상단 우측에 지도 타입 변경 컨트롤을 추가한다
            this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

            // 지도에 확대 축소 컨트롤을 생성한다
            var zoomControl = new kakao.maps.ZoomControl();

            // 지도의 우측에 확대 축소 컨트롤을 추가한다
            this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
        },
        async searchConfirm() {
            await searchMap(
                this.params,
                ({ data }) => {
                    if (data.documents.length > 0) {
                        this.removeAllMarker();
                        this.closeAllInfowindow();
                        this.infowindows = [];
                        let bounds = new kakao.maps.LatLngBounds();

                        this.searchList = data.documents;

                        for (let index = 0; index < this.searchList.length; index++) {
                            let searchItem = this.searchList[index];

                            let latlng = new kakao.maps.LatLng(searchItem.y, searchItem.x);
                            let marker = new kakao.maps.Marker({
                                map: this.map,
                                position: latlng,
                                title: searchItem["place_name"],
                            });

                            let infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

                            kakao.maps.event.addListener(marker, 'click', async () => {
                                if (this.isClose) {
                                    this.closeAllInfowindow();
                                    let content = `
<div style="width:20em; padding:6px;">
                    <span style="font-size: 1em; font-weight: bold;">${searchItem["place_name"]}</span>
                    <span style="font-size: 0.5em;">${searchItem["category_group_name"]}</span>
                    <button style="border-radius: 50%; background-color: #4461F2; color: white; float: right;" onclick="window.addMap(${index})">+</button>
                    <br>
                    <span style="font-size: 0.7em;">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                             class="bi bi-telephone" viewBox="0 0 16 16">
  <path d="M3.654 1.328a.678.678 0 0 0-1.015-.063L1.605 2.3c-.483.484-.661 1.169-.45 1.77a17.568 17.568 0 0 0 4.168 6.608 17.569 17.569 0 0 0 6.608 4.168c.601.211 1.286.033 1.77-.45l1.034-1.034a.678.678 0 0 0-.063-1.015l-2.307-1.794a.678.678 0 0 0-.58-.122l-2.19.547a1.745 1.745 0 0 1-1.657-.459L5.482 8.062a1.745 1.745 0 0 1-.46-1.657l.548-2.19a.678.678 0 0 0-.122-.58L3.654 1.328zM1.884.511a1.745 1.745 0 0 1 2.612.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.678.678 0 0 0 .178.643l2.457 2.457a.678.678 0 0 0 .644.178l2.189-.547a1.745 1.745 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.634 18.634 0 0 1-7.01-4.42 18.634 18.634 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877L1.885.511z"/>
</svg> &nbsp;&nbsp;${searchItem["phone"]}
                    </span><br>
                    <a style="color:black; font-size: 0.7em;" href="${searchItem["place_url"]}" target="_blank"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-box-arrow-up-right" viewBox="0 0 16 16">
  <path fill-rule="evenodd" d="M8.636 3.5a.5.5 0 0 0-.5-.5H1.5A1.5 1.5 0 0 0 0 4.5v10A1.5 1.5 0 0 0 1.5 16h10a1.5 1.5 0 0 0 1.5-1.5V7.864a.5.5 0 0 0-1 0V14.5a.5.5 0 0 1-.5.5h-10a.5.5 0 0 1-.5-.5v-10a.5.5 0 0 1 .5-.5h6.636a.5.5 0 0 0 .5-.5z"/>
  <path fill-rule="evenodd" d="M16 .5a.5.5 0 0 0-.5-.5h-5a.5.5 0 0 0 0 1h3.793L6.146 9.146a.5.5 0 1 0 .708.708L15 1.707V5.5a.5.5 0 0 0 1 0v-5z"/>
</svg>&nbsp;&nbsp; ${searchItem["place_url"]}</a><br>
                </div>`;
                                    infowindow.setContent(content);
                                    infowindow.open(this.map, marker);
                                    this.searchItem = searchItem;
                                    console.log(this.searchItem);
                                    await this.searchBlogConfirm();

                                    this.isClose = false;
                                } else {
                                    infowindow.close();

                                    this.isClose = true;
                                }
                            });

                            this.infowindows.push(infowindow);
                            this.markers.push(marker);
                            bounds.extend(new kakao.maps.LatLng(searchItem.y, searchItem.x));
                        }

                        this.map.setBounds(bounds);
                    } else {
                        alert("검색 결과가 없습니다!");
                    }
                },
                (error) => {
                    console.log(error);
                }
            );
        },
        async searchBlogConfirm() {
            await searchBlog({
                    query: this.searchItem.place_name,
                },
                ({ data }) => {
                    this.blogList = data.documents;
                },
                (error) => {
                    console.log(error);
                }
            );
        },
        removeAllMarker() {
            for (let marker of this.markers) {
                marker.setMap(null);
            }
            this.markers = [];
        },
        closeAllInfowindow() {
            for (let infowindow of this.infowindows) {
                infowindow.close();
            }
        },
        targetAccordian(date) {
            let list = [];
            list.push(date);
            return list;
        },
        selectDateConfirm(date) {
            this.selectDate = date;
            this.openAccordian();
            this.closeAllInfowindow();
            this.drawLine();
        },
        async drawLine() {
            for (let polyline of this.polylines) {
                polyline.setMap(null);
            }

            this.polylines = [];
            this.durations = [];
            this.totalDuration = 0;
            for (let i = 0; i < this.planBoard.planDateMap[this.selectDate].length - 1; i++) {
                this.polylines.push(
                    new kakao.maps.Polyline({
                        path: await this.getLinePath(this.planBoard.planDateMap[this.selectDate][i], this.planBoard.planDateMap[this.selectDate][i + 1]),
                        strokeWeight: 6,
                        strokeColor: this.colors[i % 7],
                        strokeOpacity: 0.7,
                        strokeStyle: 'solid'
                    })
                );
            }

            for (let polyline of this.polylines) {
                polyline.setMap(this.map);
            }
        },
        async getLinePath(originMarker, destinationMarker) {
            let originPosition = originMarker.getPosition();
            let destinationPosition = destinationMarker.getPosition();
            let origin = originPosition.getLng() + "," + originPosition.getLat();
            let destination = destinationPosition.getLng() + "," + destinationPosition.getLat();

            let linePath = [];
            await searchRoute(
                origin,
                destination,
                ({ data }) => {
                    this.totalDuration += data.routes[0].summary.duration;
                    let roads = data.routes[0].sections[0].roads;
                    this.durations.push(data.routes[0].sections[0].duration);
                    for (let road of roads) {
                        let vertexes = road.vertexes;
                        for (let i = 0; i < vertexes.length; i += 2) {
                            linePath.push(new kakao.maps.LatLng(vertexes[i + 1], vertexes[i]));
                        }
                    }
                },
                (error) => {
                    console.log(error);
                }
            );
            return linePath;
        },
        deletePlace(date, index) {
            this.planBoard.planDateMap[date][index].setMap(null);
            this.planBoard.planDateMap[date].splice(index, 1);
            this.drawLine();
        },
        movePlanSave() {
            this.markerToPlan();

            router.push({
                name: "save",
                params: {
                    planBoard: this.planBoard
                }
            })
        },
        markerToPlan() {
            Object.entries(this.planBoard.planDateMap)
                .forEach(([date, markerList]) => {
                    let planList = [];
                    for (let i = 0; i < markerList.length; i++) {
                        if (i > 0) {
                            planList.push({
                                place: markerList[i].getTitle(),
                                order: i,
                                date: date,
                                duration: this.durations[i - 1],
                            });
                        } else {
                            planList.push({
                                place: markerList[i].getTitle(),
                                order: i,
                                date: date,
                            });
                        }
                    }
                    this.planBoard.planDateMapTmp[date] = planList;
                });
        }
    },
}
</script>

<template>
    <div class="p-3">
        <b-row id="planContainer" class="p-3">
            <b-col cols="3" class="m-1">
                <b-button style="width: 100%;" block>블로그 검색 결과 </b-button>
                <b-list-group v-if="searchItem.place_name" style="height: 50em; overflow-y: scroll;">
                    <b-list-group-item v-for="(blogItem, index) in blogList" :key="index" :href="blogItem.url">
                        <div>
                            <b-row>
                                <b-col>
                                    <span id="blog-title" v-html="blogItem.blogname"></span><br/>
                                    <span id="blog-datetime" v-html="blogItem.datetime.split('T')[0]"></span><br/>
                                    <span id="blog-content" v-html="blogItem.title"></span><br/>
                                </b-col>
                                <b-col>
                                    <b-img right :src="blogItem.thumbnail" alt="Image" rounded>
                                    </b-img>
                                </b-col>
                            </b-row>
                        </div>
                    </b-list-group-item>
    
                </b-list-group>
            </b-col>
            <b-col class="m-1">
                <div>
                    <b-input-group>
                        <b-form-input class="w-75" v-model="params.query">
                        </b-form-input>
                        <b-form-select v-model="params.category_group_code" :options="options"></b-form-select>
                        <b-input-group-append>
                            <b-button variant="outline-dark" @click="searchConfirm" class="plan-search-btn">
                                <b-icon-search></b-icon-search>
                            </b-button>
                            <b-button variant="outline-dark" @click="removeAllMarker" class="plan-search-btn">
                                <b-icon-arrow-repeat></b-icon-arrow-repeat>
                            </b-button>
                        </b-input-group-append>
                    </b-input-group>
                </div>
                <div id="map" style="width:100%; height:47em;"></div>
            </b-col>
    
    
            <b-col cols="3" class="m-1">
                <div class="accordion" role="tablist" style="height: 50em; overflow-y: scroll;">
                    <b-button block @click="movePlanSave" class="plan-save-btn">
                        <b-icon-calendar-plus></b-icon-calendar-plus>
                        저장
                    </b-button>
                    <b-card no-body v-for="(planList, date) in planBoard.planDateMap" :key="date" style="background-color: white;">
                        <b-card-header header-tag="header" class="p-0" role="tab">
                            <b-button block v-b-toggle="targetAccordian(date)" @click="selectDateConfirm(date)" class="plan-accordian-btn">
                                <b-icon-calendar></b-icon-calendar> &nbsp; {{ date }}
                            </b-button>
                        </b-card-header>
    
                        <b-collapse :id="date" accordion="plan-board" role="tabpanel">
                            <b-card-body class="p-3">
                                <div v-if="planList.length === 0" style="text-align: center;">
                                    <span style="color: #4461F2; font-size: 0.7em;">
                                                        <b-icon-plus-circle></b-icon-plus-circle> 장소 추가
                                                    </span>
                                </div>
                                <div v-else>
                                    <b-table-simple hover>
                                        <b-thead>
                                        </b-thead>
                                        <b-tbody>
                                            <draggable :class="{ [`cursor-grabbing`]: drag === true }" v-model="planBoard.planDateMap[date]" group="planList" @start="drag = true" @end="drag = false" @change="drawLine" tag="tbody">
                                                <b-tr v-for="(marker, index) in planBoard.planDateMap[date]" :key="index">
                                                    <b-td class="plan-select-place-td">
                                                        {{ marker.getTitle() }}
                                                    </b-td>
                                                    <b-td class="plan-select-place-td">
                                                        <template v-if="index > 0">
                                                                    <span v-if="durations[index - 1] > 3600">{{ hourDurations[index - 1] }}시간</span>
                                                                    {{ minuteDurations[index - 1] }} 분
                                                                    {{ secondDurations[index - 1] }} 초
</template>
                                                        </b-td>
                                                        <b-td @click="deletePlace(date, index)">
                                                            <b-icon-trash></b-icon-trash>
                                                        </b-td>
                                                    </b-tr>
                                                    <b-tr v-if="planBoard.planDateMap[date].length > 1">
                                                        <b-td class="plan-select-place-td">종합</b-td>
                                                        <b-td class="plan-select-place-td">
                                                            <span v-if="totalDuration > 3600">{{ hourTotalDuration }} 시간</span>
                                                            {{ minuteTotalDuration }} 분
                                                            {{ secondTotalDuration }} 초
                                                        </b-td>
                                                        <b-td></b-td>
                                                    </b-tr>
                                                </draggable>
                                            </b-tbody>
                                        </b-table-simple>
                                    </div>
                                </b-card-body>
                            </b-collapse>
                        </b-card>

                    </div>
                </b-col>
        </b-row>
    </div>
</template>

<style scoped>
.planContainer {
    font-family: "IBM-Plex-Sans-KR-regular";
}

#blog-title {
    font-weight: bold;
}

#blog-datetime {
    font-size: 0.6em;
}

#blog-content {
    font-size: 0.8em;
}

.plan-search-btn:hover {
    background-color: white !important;
}

.plan-accordian-btn {
    border: none;
    background-color: white;
    color: var(--placeholder-color);
    font-weight: bold;
    font-size: 0.9em;
}

.plan-accordian-btn:hover {
    background-color: var(--light) !important;
    color: var(--placeholder-color) !important;
    font-weight: bold;
}

.plan-select-place-td {
    font-size: 0.7em;
}

.plan-save-btn {
    background-color: white;
    color: var(--placeholder-color);
    font-weight: bold;
    font-size: 0.9em;
}

.plan-save-btn:hover {
    background-color: var(--light) !important;
    color: var(--placeholder-color) !important;
    font-weight: bold;
}
</style>
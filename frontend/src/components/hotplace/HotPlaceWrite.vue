<template>
    <b-container class="m-3">
    <BeforeSite></BeforeSite>
        <b-row>
            <b-col></b-col>
            <b-col cols="6">
                <p class="text-center font-weight-bold" id="hot-place-text">핫 플레이스</p>
            </b-col>
            <b-col></b-col>
        </b-row>
    
        <div>
            <b-input-group>
                <b-form-select v-model="params.category_group_code" :options="options" @change="handleCategoryChange"></b-form-select>
                <b-form-input class="w-75" v-model="params.query" placeholder="카테고리를 먼저 설정해주세요" >
                </b-form-input>
                <b-input-group-append>
                    <b-button id="plan-search-input-btn" @click="searchConfirm" >
                        <b-icon-search></b-icon-search>
                    </b-button>
                </b-input-group-append>
            </b-input-group>
        </div>
    
    
        <b-row class="p-3" id="uploadButtonTemplate">
            <b-col cols="6" id="file-upload">
                파일 업로드
            </b-col>
            <b-col class="text-right" @click="confirmCategory" >
                 <input accept="image/*" type="file" ref="fileInput" @change="handleFileInputChange" disabled>
            </b-col>
        </b-row>
      
    
        <b-row class="pt-2">
            <b-col cols="6">
                <div class="mt-2">
                    <div id="map" style="width:100%; height:30em;"></div>
                </div>
            </b-col>
            <b-col cols="6">
                <b-form-textarea id="hot-place-content" style="width:100%; height:30em;" placeholder="내용을 입력해주세요" v-model="content">
                </b-form-textarea>
            </b-col>
        </b-row>
    
        <b-row class="p-3">
            <b-col id="file-upload">
                태그
            </b-col>
        </b-row>
        <b-row>
            <b-col cols="3" class="tag p-1 text-center" v-for="tag in tags" :key="tag.id" >
                <b-button @click="selectTag">
                    {{ tag.tagName }}
                </b-button>
            </b-col>
        </b-row>
        <b-row class="pt-3">
            <b-col>
                <b-button style="width: 100%;" @click="submitHotPlace">핫플 등록</b-button>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import http from '@/assets/js/community/http';
import { searchMap } from '@/assets/js/plan/plan';
import { searchMapByCategory } from '@/assets/js/hotplace/http';
import exifr from 'exifr';
import BeforeSite from '../common/BeforeSite.vue';
import { mapState } from "vuex";
export default {
    computed: {
        ...mapState("userStore", ["isLogin", "userId"]),
    },
    data() {
        return {
            hotplacename: "",
            content: "",
            isClose: true,
            searchItem: {
                place_name: "",
            },
            searchList: [],
            map: null,
            markers: [],
            infowindows: [],
            params: {
                query: null,
                category_group_code: null,
                radius: 100,
                x: null,
                y: null,
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
            selectedFile: [],
            imageUrl: null,
            tags: [
                { id: 1, tagName: "분위기 좋은", isSelect: false },
                { id: 2, tagName: "음식이 맛있는", isSelect: false },
                { id: 3, tagName: "가성비 있는", isSelect: false },
                { id: 4, tagName: "친절한 서비스", isSelect: false },
                { id: 5, tagName: "테라스/야외석", isSelect: false },
                { id: 6, tagName: "디자인이 멋진", isSelect: false },
                { id: 7, tagName: "저렴한 가격", isSelect: false },
                { id: 8, tagName: "유명한 메뉴", isSelect: false },
            ],
            
        };
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
        }
        else {
            kakao.maps.load(() => {
                this.initMap();
            });
        }
    },
    methods: {
        handleCategoryChange() {
            console.log(this.params.category_group_code);
            this.$refs.fileInput.disabled = true;
            if (this.params.category_group_code == null) {
                alert("카테고리를 선택해주세요");
                return;
            }
            this.$refs.fileInput.disabled = false;
        },
     
        submitHotPlace() {
            if (this.searchItem.place_name == "") {
                alert("핫플을 선택해주세요");
                return;
            }
            let hotPlace = {
                x: this.searchItem.x,
                y: this.searchItem.y,
                hotPlaceName: this.searchItem.place_name,
                hotPlaceId: this.searchItem.id,
                placeUrl: this.searchItem.place_url,
                roadAddressName: this.searchItem.road_address_name,
                addressName: this.searchItem.address_name,
            };
            let article = {
                hotPlaceName: this.searchItem.place_name,
                // TO DO : User ID 연동 해야함.
                userId: this.userId,
                content: this.content,
                hotPlaceId: this.searchItem.id,
            };
            let tagList = [];
            for (let i = 0; i < this.tags.length; i++) {
                if (this.tags[i].isSelect) {
                    tagList.push(this.tags[i].tagName);
                }
            }
            http.get(`/hotplace/${this.searchItem.id}`).then((res) => {
                console.log(res);
                if (res.data.response == null) {
                    http.post("/hotplace", hotPlace).then((response) => {
                        console.log(response);
                        http.post("/hotplace/article", article).then((response) => {
                            let articleId = response.data.response;
                            http.put(`/hotplace/${this.searchItem.id}/tag`, tagList).then((response) => {
                                console.log(response);
                                console.log("tag List", tagList);
                                // Response에서 article 의 id 를 반환함
                                if (this.imageUrl && this.imageUrl.length > 0) {
                                    const formData = new FormData();
                                    for (let i = 0; i < this.selectedFile.length; i++) {
                                        formData.append(`files`, this.selectedFile[i]);
                                        console.log(this.selectedFile[i]);
                                    }
                                    formData.append("files", this.selectedFile);
                                    console.log(this.selectedFile);
                                    console.log(formData);
                                    http.post(`/hotplace/article/${articleId}/flleUpload`, formData, {
                                        headers: {
                                            "Content-Type": "multipart/form-data",
                                        },
                                    }).then((response) => {
                                        alert("핫플을 정상적으로 등록하였습니다.");
                                        console.log(response);
                                        this.$router.push("/hotplace/list");
                                    }).catch((error) => {
                                        alert(error.response.data.error.message)
                                        console.log(error);
                                        this.$router.push("/hotplace/list");
                                    });
                                }
                                else {
                                    alert("핫플을 정상적으로 등록하였습니다.");
                                    this.$router.push("/hotplace/list");
                                }
                            }).catch((error) => {
                                alert(error.response.data.error.message)
                                console.log(error);
                                this.$router.push("/hotplace/list");
                            });
                        }).catch((error) => {
                            alert(error.response.data.error.message)
                            console.log(error);
                            this.$router.push("/hotplace/list");
                        });
                    }).catch((error) => {
                        alert(error.response.data.error.message)
                        console.log(error);
                        this.$router.push("/hotplace/list");
                    });
                }
                else {
                    http.post("/hotplace/article", article).then((response) => {
                        let articleId = response.data.response;
                        http.put(`/hotplace/${this.searchItem.id}/tag`, tagList).then(() => {
                            // Response에서 article 의 id 를 반환함
                            if (this.imageUrl && this.imageUrl.length > 0) {
                                const formData = new FormData();
                                for (let i = 0; i < this.selectedFile.length; i++) {
                                    formData.append(`files`, this.selectedFile[i]);
                                }
                                formData.append("files", this.selectedFile);
                                http.post(`/hotplace/article/${articleId}/flleUpload`, formData, {
                                    headers: {
                                        "Content-Type": "multipart/form-data",
                                    },
                                }).then(() => {
                                    alert("핫플을 정상적으로 등록하였습니다.");
                                    this.$router.push("/hotplace/list");
                                }).catch((error) => {
                                    alert("핫플 등록 실패");
                                    console.log(error);
                                    this.$router.push("/hotplace/list");
                                });
                            }
                            else {
                                alert("핫플을 정상적으로 등록하였습니다.");
                                this.$router.push("/hotplace/list");
                            }
                        }).catch((error) => {
                            alert("핫플 등록 실패");
                            console.log(error);
                            this.$router.push("/hotplace/list");
                        });
                    }).catch((error) => {
                        alert("핫플 등록 실패");
                        console.log(error);
                        this.$router.push("/hotplace/list");
                    });
                }
            }).catch((error) => {
                alert("핫플 등록 실패");
                console.log(error);
                this.$router.push("/hotplace/list");
            });
        },
        selectTag(e) {
            const tag = e.target.innerText;
            for (let i = 0; i < this.tags.length; i++) {
                if (this.tags[i].tagName == tag) {
                    this.tags[i].isSelect = !this.tags[i].isSelect;
                    if (this.tags[i].isSelect) {
                        e.target.style.backgroundColor = "#4461F2";
                    }
                    else {
                        e.target.style.backgroundColor = "#6c757d";
                    }
                }
            }
        },
        handleFileInputChange(event) {
            if (this.params.category_group_code == null) {
                alert("핫플의 카테고리를 선택해주세요");
                return;
            }
            if (this.selectedFile == null) {
                alert("사진을 등록해주세요");
                return;
            }
            this.selectedFile = event.target.files;
            this.imageUrl = URL.createObjectURL(this.selectedFile[0]);
            exifr.gps(this.selectedFile[0]).then((gps) => {
                if (gps) {
                    const { latitude, longitude } = gps;
                    // Perform the necessary tasks with the extracted location information
                    this.searchNearbyPlaces(latitude, longitude);
                }
                else {
                    alert("사진에 위치 정보가 없습니다. 검색하여 등록해주세요");
                    return;
                }
            }).catch((error) => {
                console.error("Error extracting location:", error);
            });
        },
        confirmCategory() {
            if (this.params.category_group_code == null) {
                alert("핫플의 카테고리를 먼저 선택해주세요");
                return;
            }
        },
        closeAllInfowindow() {
            for (let infowindow of this.infowindows) {
                infowindow.close();
            }
        },
        removeAllMarker() {
            for (let marker of this.markers) {
                marker.setMap(null);
            }
            this.markers = [];
        },
        initMap() {
            var mapContainer = document.getElementById("map"), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(37.56842, 126.9706),
                level: 5,
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
        async searchNearbyPlaces(latitude, longitude) {
            this.params.x = longitude;
            this.params.y = latitude;
            await searchMapByCategory(this.params, ({ data }) => {
                if (data.documents.length > 0) {
                    this.removeAllMarker();
                    this.closeAllInfowindow();
                    this.infowindows = [];
                    let bounds = new kakao.maps.LatLngBounds();
                    this.searchList = data.documents;
                    for (let searchItem of this.searchList) {
                        let latlng = new kakao.maps.LatLng(searchItem.y, searchItem.x);
                        let marker = new kakao.maps.Marker({
                            map: this.map,
                            position: latlng,
                            title: searchItem["place_name"],
                        });
                        let infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
                        kakao.maps.event.addListener(marker, "click", async () => {
                            if (this.isClose) {
                                this.closeAllInfowindow();
                                let content = `
<div style="width:20em; padding:6px;">
                    <span style="font-size: 1em; font-weight: bold;">${searchItem["place_name"]}</span>
                    <span style="font-size: 0.5em;">${searchItem["category_group_name"]}</span><br>
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
                                this.hotplacename = searchItem.place_name;
                                this.isClose = false;
                            }
                            else {
                                infowindow.close();
                                this.isClose = true;
                            }
                        });
                        this.infowindows.push(infowindow);
                        this.markers.push(marker);
                        bounds.extend(new kakao.maps.LatLng(searchItem.y, searchItem.x));
                    }
                    this.map.setBounds(bounds);
                }
                else {
                    alert("검색 결과가 없습니다!");
                }
            });
        },
        async searchConfirm() {
            await searchMap(this.params, ({ data }) => {
                if (data.documents.length > 0) {
                    this.removeAllMarker();
                    this.closeAllInfowindow();
                    this.infowindows = [];
                    let bounds = new kakao.maps.LatLngBounds();
                    this.searchList = data.documents;
                    for (let searchItem of this.searchList) {
                        let latlng = new kakao.maps.LatLng(searchItem.y, searchItem.x);
                        let marker = new kakao.maps.Marker({
                            map: this.map,
                            position: latlng,
                            title: searchItem["place_name"],
                        });
                        let infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
                        kakao.maps.event.addListener(marker, "click", async () => {
                            if (this.isClose) {
                                this.closeAllInfowindow();
                                let content = `
<div style="width:20em; padding:6px;">
                    <span style="font-size: 1em; font-weight: bold;">${searchItem["place_name"]}</span>
                    <span style="font-size: 0.5em;">${searchItem["category_group_name"]}</span><br>
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
                                this.hotplacename = searchItem.place_name;
                                this.isClose = false;
                            }
                            else {
                                infowindow.close();
                                this.isClose = true;
                            }
                        });
                        this.infowindows.push(infowindow);
                        this.markers.push(marker);
                        bounds.extend(new kakao.maps.LatLng(searchItem.y, searchItem.x));
                    }
                    this.map.setBounds(bounds);
                }
                else {
                    alert("검색 결과가 없습니다!");
                }
            }, (error) => {
                console.log(error);
            });
        },
    },
    components: { BeforeSite }
};
</script>

<style scoped>
@import "@/assets/css/global.css";
#hot-place-text {
    color: var(--primary-color);
    font-family: "IBM-Plex-Sans-KR-regular";
}

#hot-place-content {
    width: 100%;
    border: 1px solid var(--light-gray-color);
    border-radius: 1rem;
}

#file-upload {
    border: none;
    border-bottom: 1px solid var(--light-gray-color);
    font-size: 1rem;
    font-weight: bold;
}


</style>


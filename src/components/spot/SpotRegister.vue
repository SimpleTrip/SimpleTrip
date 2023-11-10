<script setup>
import { ref, onMounted } from "vue"
onMounted(() => {
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    // 지도를 클릭한 위치에 표출할 마커입니다
    var marker = new kakao.maps.Marker({
        // 지도 중심좌표에 마커를 생성합니다 
        position: map.getCenter()
    });
    // 지도에 마커를 표시합니다
    marker.setMap(map);

    // 지도에 클릭 이벤트를 등록합니다
    // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
    kakao.maps.event.addListener(map, 'click', function (mouseEvent) {

        // 클릭한 위도, 경도 정보를 가져옵니다 
        var latlng = mouseEvent.latLng;

        // 마커 위치를 클릭한 위치로 옮깁니다
        marker.setPosition(latlng);

        var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
        message += '경도는 ' + latlng.getLng() + ' 입니다';

        var resultDiv = document.getElementById('clickLatlng');
        resultDiv.innerHTML = message;

    });
})
const post = ref({
    image: "",
    placeName: "",
    date: "",
    type: "",
    content: ""
})
</script>

<template>
    <h2 class="text-center mb-5">Register Spot</h2>
    <div class="d-flex justify-content-evenly">
        <div id="map" style="width:50%;min-height:500px;"></div>

        <div class="justify-content-center">

            <div class="d-flex justify-content-center gap-2">
                <div class="d-flex flex-column align-items-center container">
                    <div class="col-12 mb-2 mt-2">
                        <h5>Image</h5>
                        <div class="input-group input-group-outline">
                            <label for="formFile" class="form-label"></label>
                            <input class="form-control form-control-lg" type="file" id="formFile">
                        </div>
                    </div>
                </div>
            </div>
            <div class="d-flex justify-content-center gap-2">
                <div class="d-flex flex-column align-items-center container">
                    <div class="col-12 mb-2 mt-2">
                        <h5>Place Name</h5>
                        <div class="input-group input-group-outline"><label class="form-label"></label>
                            <input type="text" id="text" class="form-control form-control-md" v-model="post.placeName" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="d-flex justify-content-center gap-2">
                <div class="d-flex flex-column align-items-center container">
                    <div class="col-12 mb-2 mt-2">
                        <h5>Date</h5>
                        <div class="input-group input-group-outline"><label class="form-label"></label>
                            <input type="text" id="text" class="form-control form-control-md" v-model="post.date" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="d-flex justify-content-center gap-2">
                <div class="d-flex flex-column align-items-center container">
                    <div class="col-12 mb-2 mt-2">
                        <h5>Type</h5>
                        <div class="input-group input-group-outline"><label class="form-label"></label><input type="text"
                                id="text" class="form-control form-control-md" v-model="post.type" /></div>
                    </div>
                </div>
            </div>
            <div class="d-flex justify-content-center gap-2">
                <div class="d-flex flex-column align-items-center container">
                    <div class="col-12 mb-2 mt-2">
                        <h5>Content</h5>
                        <div class="input-group input-group-outline">
                            <label class="form-label"></label>
                            <textarea class="text-area form-contol form-control-md" rows="5" cols="37" style="width: 100%"
                                v-model="post.content"></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="d-flex justify-content-center gap-2 mt-2">
                <router-link :to="{ name: 'boardWrite' }" class="btn btn-success btn-md">Register</router-link>
            </div>
        </div>
    </div>
</template>

<style scoped>
.text-area {
    background: none;
    border: 1px solid #d2d6da;
    border-radius: 0.375rem;
    border-top-left-radius: 0.375rem !important;
    border-bottom-left-radius: 0.375rem !important;
    padding: 0.625rem 0.75rem !important;
    line-height: 1.3 !important;
}
</style>
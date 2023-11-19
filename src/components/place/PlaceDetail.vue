<script setup>
import { ref, onMounted } from "vue"
import { useRoute } from 'vue-router';
import { getPlace } from '@/api/place.js';
const route = useRoute();

const placeId = ref(route.params.placeId)
const testPlace = ref({})

const setPlace = async function () {

    await getPlace(placeId.value, ({ data }) => {
        testPlace.value = data;

        console.log(testPlace.value)

        if (testPlace.value != "") {
            if (window.kakao && window.kakao.maps) {
                // 
            } else {
                const script = document.createElement("script");
                script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
                    }&libraries=services,clusterer`;
                /* global kakao */
                document.head.appendChild(script);
            }

            let mapContainer = document.getElementById('map'), // 지도를 표시할 div 
                mapOption = {
                    center: new kakao.maps.LatLng(testPlace.value.placeLat, testPlace.value.placeLng), // 지도의 중심좌표
                    // center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                    level: 3 // 지도의 확대 레벨
                };

            let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

            // 지도를 클릭한 위치에 표출할 마커입니다
            let marker = new kakao.maps.Marker({
                // 지도 중심좌표에 마커를 생성합니다 
                position: map.getCenter()
            });
            // 지도에 마커를 표시합니다
            marker.setMap(map);

            let overlay = null;

            // 커스텀 오버레이에 표시할 컨텐츠 입니다
            // 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
            // 별도의 이벤트 메소드를 제공하지 않습니다 
            let content = '<div class="wrap">' +
                '    <div class="info">' +
                '        <div id="place-name" class="title">' +
                `            ${testPlace.value.placeName}` +
                '        </div>' +
                '        <div class="body">' +
                '            <div class="img">' +
                `                <img id="thumb-nail" src="${testPlace.value.placeImgUrl}" width="73" height="70">` +
                '           </div>' +
                '            <div class="desc">' +
                `                <div id="place-addr" class="ellipsis">${testPlace.value.placeAddr}</div>` +
                `                <div id="place-type" class="jibun ellipsis" style="color:#4caf50">${testPlace.value.placeType}</div>` +
                '            </div>' +
                '        </div>' +
                '    </div>' +
                '</div>';

            // 마커 위에 커스텀오버레이를 표시합니다
            // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
            overlay = new kakao.maps.CustomOverlay({
                content: content,
                map: map,
                position: marker.getPosition()
            });

            // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
            kakao.maps.event.addListener(marker, 'click', function () {
                overlay.setMap(map);
            });
        }
    })
}

const setMap = async () => {
    await setPlace()
}

onMounted(() => setMap()
)
</script>

<template>
    <div class="container">
        <h2 class="text-center mb-5">장소 정보</h2>
        <div v-if="testPlace.value != ''" class="d-flex justify-content-evenly">
            <div id="map" style="width:50%;min-height:500px;"></div>

            <div class="justify-content-center">
                <div class="d-flex justify-content-center gap-2">
                    <div class="d-flex flex-column align-items-center container">
                        <div class="col-12 mb-2 mt-2">
                            <h5>장소명</h5>
                            <div class="input-group input-group-outline"><label class="form-label"></label>
                                <input readonly type="text" id="text" class="form-control form-control-md"
                                    v-model="testPlace.placeName" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-center gap-2">
                    <div class="d-flex flex-column align-items-center container">
                        <div class="col-12 mb-2 mt-2">
                            <h5>주소</h5>
                            <div class="input-group input-group-outline"><label class="form-label"></label>
                                <input readonly type="text" id="text2" class="form-control form-control-md"
                                    v-model="testPlace.placeAddr" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-center gap-2">
                    <div class="d-flex flex-column align-items-center container">
                        <div class="col-12 mb-2 mt-2">
                            <h5>날짜</h5>
                            <div class="input-group input-group-outline">
                                <label class="form-label"></label>
                                <input readonly v-model="testPlace.placeDate" type="date" id="date"
                                    class="form-control form-control-md">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-center gap-2">
                    <div class="d-flex flex-column align-items-center container">
                        <div class="col-12 mb-2 mt-2">
                            <h5>유형</h5>
                            <div class="input-group input-group-outline">
                                <label class="form-label"></label>
                                <select disabled v-model="testPlace.placeType" name="type" id="type"
                                    class="form-control form-control-md">
                                    <option value="관광지">관광지</option>
                                    <option value="문화시설">문화시설</option>
                                    <option value="행사/공연/축제">행사/공연/축제</option>
                                    <option value="여행코스">여행코스</option>
                                    <option value="레포츠">레포츠</option>
                                    <option value="숙박">숙박</option>
                                    <option value="쇼핑">쇼핑</option>
                                    <option value="음식점">음식점</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-center gap-2">
                    <div class="d-flex flex-column align-items-center container">
                        <div class="col-12 mb-2 mt-2">
                            <h5>내용</h5>
                            <div class="input-group input-group-outline">
                                <label class="form-label"></label>
                                <textarea readonly class="text-area form-contol form-control-md" rows="5" cols="37"
                                    style="width: 100%" v-model="testPlace.placeContent"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
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

<!-- Kakao Map -->
<style>
.wrap {
    position: absolute;
    left: 0;
    bottom: 40px;
    width: 288px;
    height: 132px;
    margin-left: -144px;
    text-align: left;
    overflow: hidden;
    font-size: 12px;
    font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
    line-height: 1.5;
}

.wrap * {
    padding: 0;
    margin: 0;
}

.wrap .info {
    width: 286px;
    height: 120px;
    border-radius: 5px;
    border-bottom: 2px solid #ccc;
    border-right: 1px solid #ccc;
    overflow: hidden;
    background: #fff;
}

.wrap .info:nth-child(1) {
    border: 0;
    box-shadow: 0px 1px 2px #888;
}

.info .title {
    padding: 5px 0 0 10px;
    height: 30px;
    background: #eee;
    border-bottom: 1px solid #ddd;
    font-size: 18px;
    font-weight: bold;
}

.info .close {
    position: absolute;
    top: 10px;
    right: 10px;
    color: #888;
    width: 17px;
    height: 17px;
    background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');
}

.info .close:hover {
    cursor: pointer;
}

.info .body {
    position: relative;
    overflow: hidden;
}

.info .desc {
    position: relative;
    margin: 13px 0 0 90px;
    height: 75px;
}

.desc .ellipsis {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.desc .jibun {
    font-size: 11px;
    color: #888;
    margin-top: -2px;
}

.info .img {
    position: absolute;
    top: 6px;
    left: 5px;
    width: 73px;
    height: 71px;
    border: 1px solid #ddd;
    color: #888;
    overflow: hidden;
}

.info:after {
    content: '';
    position: absolute;
    margin-left: -12px;
    left: 50%;
    bottom: 0;
    width: 22px;
    height: 12px;
    background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')
}

.info .link {
    color: #5085BB;
}
</style>
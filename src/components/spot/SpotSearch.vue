<script setup>
import { ref, computed, onMounted, toRaw } from "vue"

import { getSido, getGugun, getMap } from '@/api/Map.js'

const cities = ref([]);
const regions = ref([]);
const contentTypes = ref([
    { code: "12", name: "관광지" },
    { code: "14", name: "문화시설" },
    { code: "15", name: "행사/공연/축제" },
    { code: "25", name: "여행코스" },
    { code: "28", name: "레포츠" },
    { code: "32", name: "숙박" },
    { code: "38", name: "쇼핑" },
    { code: "39", name: "음식점" },
]);

const cityName = computed(() => {
    const selected = cities.value.find(city => city.code === cityCode.value)
    return selected.name;
})
const cityCode = ref("");
const regionCode = ref("");
const contentTypeCode = ref("");

onMounted(() => {
    getSido(({ data }) => {
        let sidoList = data.response.body.items.item;
        sidoList.forEach((city) => cities.value.push(city));
    })

    initMap();
});

let spots = null;

let cityFunc = async function () {
    await getGugun(cityCode.value, ({ data }) => {
        let gugunList = data.response.body.items.item;
        regions.value = [];
        gugunList.forEach((region) => regions.value.push(region));
    })
    await getMap(cityName.value, cityCode.value, regionCode.value, contentTypeCode.value,
        ({ data }) => {
            spots = data.response.body.items.item;
        }
    )
    if (spots) setMap(spots);
};

let regionFunc = async function () {
    await getMap(cityName.value, cityCode.value, regionCode.value, contentTypeCode.value,
        ({ data }) => {
            spots = data.response.body.items.item;
        }
    )
    if (spots) setMap(spots);
};

let contentFunc = async function () {
    await getMap(cityName.value, cityCode.value, regionCode.value, contentTypeCode.value,
        ({ data }) => {
            spots = data.response.body.items.item;
        }
    )
    if (spots) setMap(spots);
};

let map = ref({});
let markerPostions = ref([]);
let markers = ref([]);

let displayMarkers = function (list) {
    if (markers.value.length > 0) {
        markers.value.forEach((marker) => marker.setMap(null));
    }

    let positions = markerPostions.value.map(
        (position) => new kakao.maps.LatLng(position.lat, position.lng)
    )

    if (positions.length > 0) {
        markers.value = positions.map(
            (position) =>
                new kakao.maps.Marker({
                    map: toRaw(map.value),
                    position,
                })
        )
    }

    for (let i = 0; i < list.length; i++) {
        let spot = list[i]
        let lat = spot.mapy
        let lng = spot.mapx

        let content = 
        '<div class="wrap">' + 
        '    <div class="info">' + 
        '        <div class="title">' + 
        `            ${spot.title}` +
        '        </div>' + 
        '        <div class="body">' + 
        '            <div class="img">' +
        `                <img src="${spot.firstimage}" width="73" height="70">` +
        '           </div>' + 
        '            <div class="desc">' + 
        `                <div class="ellipsis">${spot.addr1}</div>` +  
        '            </div>' + 
        '        </div>' + 
        '    </div>' +    
        '</div>';

        let customOverlay = new kakao.maps.CustomOverlay({
            map: map.value,
            clickable: true,
            content: content,
            position: new kakao.maps.LatLng(lat, lng),
            zIndex: 3
        })

        customOverlay.setVisible(false)

        // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
        kakao.maps.event.addListener(markers.value[i], 'click', function () {
            let visible = customOverlay.getVisible()
            if (visible) customOverlay.setVisible(false)
            else customOverlay.setVisible(true)
        })
    }
}

let initMap = function () {
    let mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(33.452278, 126.567803), // 지도의 중심좌표
            level: 5 // 지도의 확대 레벨
        };

    map.value = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
    let mapTypeControl = new kakao.maps.MapTypeControl();

    // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
    // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
    map.value.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

    // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
    let zoomControl = new kakao.maps.ZoomControl();
    map.value.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
}

let setMap = function (list) {
    // 지도
    var mapContainer = document.getElementById('map'); // 지도를 표시할 div

    var mapOption = null;
    if (list) {
        mapOption = {
            center: new kakao.maps.LatLng(list[0].mapy, list[0].mapx), // 지도의 중심좌표
            level: 7 // 지도의 확대 레벨
        };
    }

    else {
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 7 // 지도의 확대 레벨
        };
    }

    map.value = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
    var mapTypeControl = new kakao.maps.MapTypeControl();

    // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
    // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
    map.value.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

    // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
    var zoomControl = new kakao.maps.ZoomControl()
    map.value.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT)

    markerPostions.value = [];
    list.forEach((spot) => markerPostions.value.push({
        lat: spot.mapy,
        lng: spot.mapx
    }))

    displayMarkers(list)
}
</script>

<template>
    <h2 class="text-center mb-5">Search Spot</h2>
    <div class="d-flex flex-column justify-content-center align-items-center">
        <div class="input-group mb-3">
            <div class="d-flex justify-content-center align-items-center container">
                <div class="d-flex flex-column align-items-center container col-2">
                    <label for="city">City</label>
                    <select class="select-option form-select text-center" id="city" v-model="cityCode" @change="cityFunc">
                        <option v-for="city in cities" :key="city.code" :value="city.code">
                            {{ city.name }}
                        </option>
                    </select>
                </div>
                <div class="d-flex flex-column align-items-center container  col-2">
                    <label for="region">Region</label>
                    <select class="select-option form-select text-center" id="region" v-model="regionCode"
                        @change="regionFunc">
                        <option v-for="region in regions" :key="region.code" :value="region.code">
                            {{ region.name }}
                        </option>
                    </select>
                </div>
                <div class="d-flex flex-column align-items-center container  col-2">
                    <label for="type">Type</label>
                    <select class="select-option form-select text-center" id="type" v-model="contentTypeCode"
                        @change="contentFunc">
                        <option v-for="contentType in contentTypes" :key="contentType.code" :value="contentType.code">
                            {{ contentType.name }}
                        </option>
                    </select>
                </div>
            </div>
        </div>
        <div class="container">
            <div id="map" style="width:100%;height:550px;"></div>
        </div>
    </div>
</template>

<style scoped>
.select-option {
    background: none;
    border: 1px solid #d2d6da;
    border-radius: 0.375rem;
    border-top-left-radius: 0.375rem !important;
    border-bottom-left-radius: 0.375rem !important;
    padding: 0.625rem 0.75rem !important;
    line-height: 1.3 !important;
}
</style>
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
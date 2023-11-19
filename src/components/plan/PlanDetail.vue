<script setup>
import { onMounted, ref, toRaw } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import PlanItemCard from '@/components/plan/item/PlanItemCard.vue';
import PlaceDetail from '@/components/plan/item/PlaceDetail.vue';
import MaterialInput from '@/components/material/MaterialInput.vue';
import { getPlan, deletePlanAndItem } from '@/api/plan.js';

const router = useRouter();
const route = useRoute();

const planNo = ref(route.query.planNo);
const planItemList = ref([]);
const plan = ref({});

const isShowDetail = ref(false);
const selectIndex = ref(-1);

let map;
let infowindow;

onMounted(async () => {
  await getPlan(planNo.value, ({ data }) => {
    plan.value = data.plan;
    planItemList.value = data.planItem;
  });

  let mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
      center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
      level: 3, // 지도의 확대 레벨
    };
  map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
  let mapTypeControl = new kakao.maps.MapTypeControl();
  map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
  let zoomControl = new kakao.maps.ZoomControl();
  map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
  infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
  displayMarkers(planItemList.value);
});

// 마커 보기
const displayMarkers = (places) => {
  var bounds = new kakao.maps.LatLngBounds();
  for (var i = 0; i < places.length; i++) {
    var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
      marker = addMarker(placePosition, i);
    bounds.extend(placePosition);
    (function (marker, title) {
      kakao.maps.event.addListener(marker, 'mouseover', function () {
        displayInfowindow(marker, title);
      });

      kakao.maps.event.addListener(marker, 'click', function () {
        map.panTo(marker.getPosition());
      });
      kakao.maps.event.addListener(marker, 'mouseover', function () {
        displayInfowindow(marker, title);
      });

      kakao.maps.event.addListener(marker, 'mouseout', function () {
        infowindow.close();
      });
    })(marker, places[i].placeName);
  }
  // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
  map.setBounds(bounds);
};

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
const addMarker = (position, idx, title) => {
  var marker = new kakao.maps.Marker({
    position: position, // 마커의 위치
  });
  marker.setMap(map); // 지도 위에 마커를 표출합니다
  return marker;
};

// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title) {
  var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

  infowindow.setContent(content);
  infowindow.open(map, marker);
}

const clickList = () => {
  router.replace({ name: 'planList' });
};

const clickCard = (index, placeName) => {
  const placePosition = new kakao.maps.LatLng(planItemList.value[index].y, planItemList.value[index].x);
  const marker = addMarker(placePosition, index);
  displayInfowindow(marker, placeName);
  map.panTo(marker.getPosition());
};

const clickDelete = () => {
  deletePlanAndItem(planNo.value, ({ date }) => {});
  alert('계획이 삭제되었습니다.', router.replace({ name: 'planList' }));
};
</script>

<template>
  <div class="container-fluid">
    <div class="row align-items-center justify-content-center">
      <div class="col-lg-6">
        <div id="map" style="height: 550px; border-radius: 10px"></div>
      </div>
      <div class="col-lg-4">
        <div class="row text-left">
          <div class="col container">
            <p class="mt-4" style="text-align: center; font-size: 24px; font-weight: bold; margin-bottom: 6px">{{ plan.planUserId }}님의 여행 계획</p>
            <div class="input-group input-group-outline" style="flex-direction: column; margin-bottom: 14px">
              <label for="planTitle" class="d-block">계획명:</label>
              <input type="text" id="planTitle" class="write-form form-control" :value="plan.planTitle" readonly />
            </div>
            <div class="row" style="margin-bottom: 14px">
              <div class="col-6">
                <div class="input-group input-group-outline">
                  <label for="startDate" class="d-block">출발일:</label>
                  <input id="startDate" type="date" class="write-form form-control" :value="plan.startDate" readonly />
                </div>
              </div>
              <div class="col-6">
                <div class="input-group input-group-outline">
                  <label for="endDate" class="d-block">도착일:</label>
                  <input id="endDate" type="date" class="write-form form-control" :value="plan.endDate" readonly />
                </div>
              </div>
            </div>
            <div class="input-group input-group-outline" style="margin-bottom: 14px">
              <label for="planContent" class="d-block">계획 세부 사항:</label>
              <textarea id="planContent" class="write-form form-control" rows="10" maxlength="500" :value="plan.planContent" readonly></textarea>
            </div>
            <div class="row justify-content-evenly">
              <button type="button" class="btn btn-outline-success" style="margin: 10px; width: 40%" @click="clickList">여행계획목록</button>
              <button type="button" class="btn btn-danger" style="margin: 10px; width: 40%" @click="clickDelete">삭제</button>
            </div>
          </div>
        </div>
      </div>

      <div class="row justify-content-center">
        <div style="text-align: center; width: 100%; margin: 20px; font-weight: bold; margin-top: 40px">
          <span style="font-size: 20px">{{ plan.planUserId }}님의 여행 코스</span>
        </div>
        <div class="search-list-container col-lg-10">
          <div style="max-height: 700px; overflow-y: scroll; padding: 4px">
            <PlanItemCard v-for="(place, index) in planItemList" :key="place.id" :place="place" :idx="index" @click="clickCard(index, place.placeName)" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.search-list-container {
  height: 100%;
  border: 1px lightgray solid;
  border-radius: 10px;
}

.searchBtn {
  display: flex;
  font-size: 16px;
  justify-content: center;
  align-items: center;
  padding: 10px;
  background-color: #4caf50;
  color: white;
  border-top-right-radius: 6px;
  border-bottom-right-radius: 6px;
}

.searchBtn:hover {
  background-color: #40a027;
}

.centered-text {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 2;
  width: 70%;
  min-height: 50%;
  background-color: white;
  border: 1px solid gray;
  border-radius: 10px;
}

.write-form {
  width: 100%;
  border: 1px solid lightgray !important;
  border-radius: 6px;
  box-shadow: 0px 1px 3px 0px lightgray;
}
</style>

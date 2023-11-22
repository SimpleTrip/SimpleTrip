<script setup>
import { onMounted, ref, toRaw } from 'vue';
import { useRouter } from 'vue-router';
import PlaceCard from '@/components/plan/item/PlaceCard.vue';
import PlacePlusCard from '@/components/plan/item/PlacePlusCard.vue';
import PlaceDetail from '@/components/plan/item/PlaceDetail.vue';
import MaterialInput from '@/components/material/MaterialInput.vue';
import { setPlan } from '@/api/plan.js';
import { useCookies } from 'vue3-cookies';
import { refresh } from '@/util/tokenUtil';
import { useUserStore } from '@/stores/user';
import { storeToRefs } from 'pinia';

const userStore = useUserStore();
const { isLogin, userInfo } = storeToRefs(userStore);
const { cookies } = useCookies();

const noAuth = () => {
  cookies.remove('refreshToken', '/', 'localhost');
  cookies.remove('accessToken', '/', 'localhost');
  isLogin.value = false;
  userInfo.value = {};
  router.replace({ name: 'login' });
};

const inputWord = ref('');
const searchList = ref([]);
const markers = ref([]);

const plusList = ref([]);

const isShowDetail = ref(false);
const selectIndex = ref(-1);

const plan = ref({
  planTitle: '',
  planContent: '',
  startDate: '',
  endDate: '',
  planUserId: userInfo.value.userId,
});

const router = useRouter();

let page;
let map;
let ps;
let infowindow;

onMounted(() => {
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
});

const clickSearch = () => {
  searchList.value = [];
  ps = new kakao.maps.services.Places();
  removeMarker();
  ps.keywordSearch(inputWord.value, placesSearchCB);
};

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
const placesSearchCB = (data, status, pagination) => {
  if (status === kakao.maps.services.Status.OK) {
    const test = [...toRaw(searchList.value), ...data];
    searchList.value = test;
    displayMarkers(searchList.value);
    page = pagination;
  } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
    alert('검색 결과가 존재하지 않습니다.');
    return;
  } else if (status === kakao.maps.services.Status.ERROR) {
    alert('검색 결과 중 오류가 발생했습니다.');
    return;
  }
};

// 마커 보기
const displayMarkers = (places) => {
  var bounds = new kakao.maps.LatLngBounds();
  removeMarker();

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
    })(marker, places[i].place_name);
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
  markers.value.push(marker); // 배열에 생성된 마커를 추가합니다
  return marker;
};

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
const removeMarker = () => {
  for (var i = 0; i < markers.value.length; i++) {
    markers.value[i].setMap(null);
  }
  markers.value = [];
};

// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title) {
  var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

  infowindow.setContent(content);
  infowindow.open(map, marker);
}

const clickNext = () => {
  if (page.hasNextPage) page.nextPage();
  else alert('마지막입니다.');
};

const clickCard = (index, place_name) => {
  displayInfowindow(markers.value[index], place_name);
  map.panTo(markers.value[index].getPosition());
};

const clickPlus = (index) => {
  const placeToAdd = searchList.value[index];
  placeToAdd.memo = '';

  // 중복 체크
  if (!plusList.value.some((place) => place.id === placeToAdd.id)) {
    plusList.value.push(placeToAdd);
  } else {
    alert('이미 추가한 장소입니다.');
  }
};

const clickMinus = (index) => {
  plusList.value.splice(index, 1);
};

const showDetail = (index) => {
  isShowDetail.value = true;
  selectIndex.value = index;
};

const closeDetail = () => {
  isShowDetail.value = false;
  selectIndex.value = -1;
};

const writeDetail = (inputMemo) => {
  plusList.value[selectIndex.value].memo = inputMemo;
  closeDetail();
};

const clickRegist = () => {
  if (plusList.value.length == 0) {
    alert('선택한 여행지가 없습니다.');
    return;
  }
  if (plan.value.planTitle == '') {
    alert('계획명을 작성해주세요');
    return;
  } else if (plan.value.planContent == '') {
    alert('계획 세부 사항을 작성해주세요');
    return;
  } else if (plan.value.startDate == '' || plan.value.endDate == '') {
    alert('날짜를 정확히 기입해주세요');
    return;
  }
  setPlan(
    { planItem: plusList.value, plan: plan.value },
    (success) => {
      alert('계획이 등록되었습니다.');
      router.replace({ name: 'planList' });
    },
    async (fail) => {
      if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
        const refreshData = await refresh();
        if (refreshData != null) {
          if (refreshData.dataHeader.successCode == 0) {
            userInfo.value = refreshData.dataBody;
            await setPlan(
              { planItem: plusList.value, plan: plan.value },
              (success) => {
                alert('계획이 등록되었습니다.');
                router.replace({ name: 'planList' });
              },
              (fail) => {
                alert(fail.dataHeader.resultMessage);
                router.go(0);
              }
            );
          } else {
            noAuth();
            alert(fail.dataHeader.resultMessage);
          }
        }
      } else {
        alert(fail.dataHeader.resultMessage);
        router.go(0);
      }
    }
  );
};

const clickList = () => {
  router.replace({ name: 'planList' });
};
</script>

<template>
  <div class="container-fluid">
    <div class="row align-items-center justify-content-center">
      <div class="col-lg-8">
        <div id="map" style="height: 550px; border-radius: 10px"></div>
      </div>
      <div class="col-lg-4 row">
        <div class="col-6" style="padding: 4px">
          <div class="search-list-container">
            <div style="text-align: center; width: 100%; padding: 10px">
              <span style="font-size: 20px">여행지 검색</span>
            </div>
            <div class="input-group input-group-outline" style="padding: 6px">
              <input placeholder="관광지 검색어 입력" type="text" id="text" class="form-control form-control-md" v-model="inputWord" @keypress.enter="clickSearch" />
              <i class="searchBtn fas fa-search" @click="clickSearch"></i>
            </div>
            <div style="height: 450px; overflow-y: scroll; padding: 4px">
              <PlaceCard v-for="(place, index) in searchList" :key="place.id" :place="place" :idx="index" @click="clickCard(index, place.place_name)" @click-plus="clickPlus" />
              <a v-if="searchList.length != 0" @click="clickNext" class="btn btn-secondary btn-md" style="width: 100%; margin: 0px">더보기</a>
            </div>
          </div>
        </div>
        <div class="col-6" style="padding: 4px">
          <div class="search-list-container">
            <div style="text-align: center; width: 100%; padding: 10px">
              <span style="font-size: 20px">나의 여행 코스</span>
            </div>
            <div style="height: 502px; overflow-y: scroll; padding: 4px">
              <PlacePlusCard v-for="(place, index) in plusList" :key="place.id" :place="place" :idx="index" @click-minus="clickMinus" @click-detail="showDetail" />
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row text-left mt-4">
      <div class="col">
        <div class="input-group input-group-outline" style="flex-direction: column; margin-bottom: 14px">
          <label for="planTitle" class="d-block">계획명:</label>
          <input type="text" id="planTitle" class="write-form form-control" v-model="plan.planTitle" />
        </div>
        <div class="row" style="margin-bottom: 14px">
          <div class="col-6">
            <div class="input-group input-group-outline">
              <label for="startDate" class="d-block">출발일:</label>
              <input id="startDate" type="date" class="write-form form-control" v-model="plan.startDate" />
            </div>
          </div>
          <div class="col-6">
            <div class="input-group input-group-outline">
              <label for="endDate" class="d-block">도착일:</label>
              <input id="endDate" type="date" class="write-form form-control" v-model="plan.endDate" />
            </div>
          </div>
        </div>
        <div class="input-group input-group-outline" style="margin-bottom: 14px">
          <label for="planContent" class="d-block">계획 세부 사항:</label>
          <textarea id="planContent" class="write-form form-control" rows="10" maxlength="500" v-model="plan.planContent"></textarea>
        </div>
        <div class="row justify-content-evenly">
          <button type="button" class="btn btn-success" style="margin: 10px; width: 45%" @click="clickRegist">여행계획등록</button>
          <button type="button" class="btn btn-outline-success" style="margin: 10px; width: 45%" @click="clickList">여행계획목록</button>
        </div>
      </div>
    </div>

    <div v-if="isShowDetail" class="centered-text">
      <PlaceDetail :place="plusList[selectIndex]" :idx="selectIndex" @close-detail="closeDetail" @write-detail="writeDetail" />
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
}

.write-form:focus {
  box-shadow: 0px 1px 3px 0px #66bb6a;
}
</style>

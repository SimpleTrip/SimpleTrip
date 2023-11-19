<script setup>
import { ref, onMounted } from "vue"
import { v4 as UUID } from 'uuid'
import { registPlace } from '@/api/place.js';
import AWS from 'aws-sdk'


const {VITE_BUCKET_NAME, VITE_BUCKET_REGION, VITE_IDENTITY_POOL_ID, VITE_IMAGE_URL} = import.meta.env

const uploadedFile = ref({})

const registHandler = function () {
    // Infos Without Image
    console.log(`Vue -> Spring Boot -> DB`)
    console.log(place.value)

    //
    const response = confirm('등록하시겠습니까?');
    if (response) {
        registPlace(place.value)
        alert('등록 완료');
        // router.push({ name: 'articleList' });
    } else {
        alert('등록 실패');
    }

    // Image
    console.log(`Vue -> AWS S3 : ${place.value.imageName}`)
    uploadFile()
}

const changeHandler = function () {
    let placeName = document.getElementById("place-name")
    if(placeName) placeName.innerHTML = place.value.placeName
    let placeAddr = document.getElementById("place-addr")
    if(placeAddr) placeAddr.innerHTML = place.value.placeAddr
    let placeType = document.getElementById("place-type")
    if(placeType) placeType.innerHTML = place.value.placeType
}

const getFileExtension = function (fileName) {
    const len = fileName.length;
    const lastDot = fileName.lastIndexOf('.')
    const extension = fileName.substring(lastDot, len).toLowerCase()
    return extension
}

const setPlaceImage = function () {
    const file = document.getElementById("place-image").files[0]
    const uuid = UUID(file.name)
    place.value.placeImgUrl = VITE_IMAGE_URL + uuid + getFileExtension(file.name)
    place.value.uuid = uuid + getFileExtension(file.name)
    uploadedFile.value = file
    console.log(uploadedFile.value)
    setThumbNail(file)
}

const setThumbNail = function (file) {
    let reader = new FileReader();
    reader.onload = function (e) {
        let thumbnail = document.getElementById("thumb-nail")
        if(thumbnail) thumbnail.setAttribute("src", e.target.result)
    }
    reader.readAsDataURL(file)
}

const uploadFile = function () {

  // Set the Region 
  AWS.config.update({
    region: VITE_BUCKET_REGION,
    credentials: new AWS.CognitoIdentityCredentials({
      IdentityPoolId: VITE_IDENTITY_POOL_ID
    })
  });

  // Create S3 service object
  const s3 = new AWS.S3({
    apiVersion: '2006-03-01',
    params: {
      Bucket: VITE_BUCKET_NAME
    }
  });


  s3.upload({
    Key: place.value.uuid,
    Body: uploadedFile.value,
    ACL: 'public-read'
  }, function (err, data) {
    console.log(uploadedFile.value.uuid)
    if (err) {
      console.log(err)
      return alert('There was an error uploading your photo: ', err.message)
    }
    alert('Successfully uploaded photo')
    console.log(data)
  }
  )
}

onMounted(() => {

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
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

    let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    // 지도를 클릭한 위치에 표출할 마커입니다
    let marker = new kakao.maps.Marker({
        // 지도 중심좌표에 마커를 생성합니다 
        // position: map.getCenter()
    });
    // 지도에 마커를 표시합니다
    marker.setMap(map);

    let overlay = null;

    // 지도에 클릭 이벤트를 등록합니다
    // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
    kakao.maps.event.addListener(map, 'click', function (mouseEvent) {

        // 클릭한 위도, 경도 정보를 가져옵니다 
        let latlng = mouseEvent.latLng;

        // 마커 위치를 클릭한 위치로 옮깁니다
        marker.setPosition(latlng);

        place.value.placeLng = latlng.La
        place.value.placeLat = latlng.Ma

        if (overlay) overlay.setMap(null);

        // 커스텀 오버레이에 표시할 컨텐츠 입니다
        // 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
        // 별도의 이벤트 메소드를 제공하지 않습니다 
        let content = '<div class="wrap">' +
            '    <div class="info">' +
            '        <div id="place-name" class="title">' +
            `            ${place.value.placeName}` +
            '        </div>' +
            '        <div class="body">' +
            '            <div class="img">' +
            `                <img id="thumb-nail" src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png" width="73" height="70">` +
            '           </div>' +
            '            <div class="desc">' +
            `                <div id="place-addr" class="ellipsis">${place.value.placeAddr}</div>` +
            `                <div id="place-type" class="jibun ellipsis" style="color:#4caf50">${place.value.placeType}</div>` +
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
    });
})

// 
const place = ref({
    placeName: '',
    placeAddr: '',
    placeType: '',
    placeContent: '',
    placeDate: '',
    placeImgUrl: '',
    // 
    placeLat: '',
    placeLng: ''
})
</script>

<template>
    <div class="container">
        <h2 class="text-center mb-5">장소 등록</h2>
        <div class="d-flex justify-content-evenly">
            <div id="map" style="width:50%;min-height:500px;"></div>

            <div class="justify-content-center">
                <div class="d-flex justify-content-center gap-2">
                    <div class="d-flex flex-column align-items-center container">
                        <div class="col-12 mb-2 mt-2">
                            <h5>장소명</h5>
                            <div class="input-group input-group-outline"><label class="form-label"></label>
                                <input @change="changeHandler" type="text" id="text" class="form-control form-control-md"
                                    v-model="place.placeName" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-center gap-2">
                    <div class="d-flex flex-column align-items-center container">
                        <div class="col-12 mb-2 mt-2">
                            <h5>주소</h5>
                            <div class="input-group input-group-outline"><label class="form-label"></label>
                                <input @change="changeHandler" type="text" id="text" class="form-control form-control-md"
                                    v-model="place.placeAddr" />
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
                                <input v-model="place.placeDate" type="date" id="date" class="form-control form-control-md">
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
                                <select @change="changeHandler" v-model="place.placeType" name="type" id="type"
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
                                <textarea class="text-area form-contol form-control-md" rows="5" cols="37"
                                    style="width: 100%" v-model="place.placeContent"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-center gap-2">
                    <div class="d-flex flex-column align-items-center container">
                        <div class="col-12 mb-2 mt-2">
                            <h5>사진</h5>
                            <div class="input-group input-group-outline">
                                <label for="place-image" class="form-label"></label>
                                <input @change="setPlaceImage" class="form-control form-control-lg" type="file"
                                    id="place-image">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-center gap-2 mt-2">
                    <button class="btn btn-success btn-md" @click="registHandler">저장</button>
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
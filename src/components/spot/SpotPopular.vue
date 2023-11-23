<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia'
import { useCookies } from 'vue3-cookies';
import { refresh } from '@/util/tokenUtil';
import { useUserStore } from '@/stores/user'
import { getPopularSpotList } from '@/api/spot.js';

import { viewUser } from '@/api/user.js';


const { cookies } = useCookies()
const userStore = useUserStore()
const { isLogin, userInfo } = storeToRefs(userStore)

const router = useRouter();

const popularSpotList = ref([]);

const user = ref({});

const resetAuth = () => {
    cookies.remove('refreshToken', '/', 'localhost');
    cookies.remove('accessToken', '/', 'localhost');
    isLogin.value = false;
    userInfo.value = {};
    router.replace({ name: 'login' });
};

onMounted(async () => {

    await viewUser(
        userInfo.value.userId,
        (success) => {
            user.value = success.dataBody;
        },
        async (fail) => {
            console.log(fail)
            if (fail.dataHeader == null) {
                //
            }
            else if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
                const refreshData = await refresh();
                if (refreshData != null) {
                    if (refreshData.dataHeader.successCode == 0) {
                        userInfo.value = refreshData.dataBody;
                        await viewUser(
                            userInfo.value.userId,
                            (success) => {
                                user.value = success.dataBody;
                            },
                            (fail) => {
                                alert(fail.dataHeader.resultMessage);
                                router.go(-1);
                            }
                        );
                    } else {
                        resetAuth();
                        alert(fail.dataHeader.resultMessage);
                    }
                }
            } else {
                alert(fail.dataHeader.resultMessage);
                router.go(-1);
            }
        }
    );

    // let userInfo = {};
    // // userInfo.userSex = user.value.userSex;
    // // userInfo.userAge = user.value.userAge;

    getPopularSpotList(
        user.value,
        // success
        (success) => {
            for (const popularSpot of success.dataBody) {
                popularSpotList.value.push(popularSpot);
            }
        },
        async (fail) => {
            if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
                const refreshData = await refresh();
                if (refreshData != null) {
                    if (refreshData.dataHeader.successCode == 0) {
                        // access만 만료 됐었어서 refresh로 새롭게 가져옴
                        userInfo.value = refreshData.dataBody;
                        // 새로운 토큰으로 재시도
                        await getPopularSpotList(
                            userInfo,
                            (success) => {
                                // refresh 후 등록 성공
                                for (const popularSpot of success.dataBody) {
                                    popularSpotList.value.push(popularSpot);
                                }
                            },
                            (fail) => {
                                // 새로운 토큰으로 했는데 서버에서 에러남
                                // 로그인 관련 문제 아님
                                alert(fail.dataHeader.resultMessage);
                            }
                        );
                    } else {
                        // 로그인 만료(access/refresh 둘 다)
                        // 쿠키에 아무것도 없음
                        resetAuth();
                        alert(fail.dataHeader.resultMessage);
                        router.push({ name: 'main' })
                    }
                }
            } else {
                // UNAUTHORIZED 이외의 오류
                alert(fail.dataHeader.resultMessage);
            }
        }
    )
});
</script>

<template>
    <div class="container">
        <h2 class="text-center mb-5">추천 여행지</h2>
        <div class="row">
            <div v-for="popularSpot in popularSpotList" :key="popularSpot.spotId" class="col-md-6 mx-auto mt-4">
                <div class="card mt-5">
                    <div class="card-header p-0 mx-3 mt-n4 position-relative z-index-2">
                        <a class="d-block blur-shadow-image">
                            <img :src="popularSpot.spotImgUrl" alt="img-blur-shadow" class="img-fluid border-radius-lg"
                                loading="lazy" />
                        </a>
                    </div>
                    <div class="card-body pt-3">
                        <h5>{{ popularSpot.spotName }}</h5>
                        <p>{{ popularSpot.spotAddr }}</p>
                        <button class="btn btn-sm mb-0 btn-success"><a class="text-white"
                                :href="'https://search.daum.net/search?q=' + popularSpot.spotName"
                                target="_blank">검색</a></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>

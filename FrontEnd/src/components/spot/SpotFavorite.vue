<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia'
import { useCookies } from 'vue3-cookies';
import { refresh } from '@/util/tokenUtil';
import { useUserStore } from '@/stores/user'
import { getFavoriteList } from '@/api/favorite.js'

const userStore = useUserStore()
const { isLogin, userInfo } = storeToRefs(userStore)

const favoriteList = ref([])

const { cookies } = useCookies()

const router = useRouter();

const resetAuth = () => {
    cookies.remove('refreshToken', '/', 'localhost');
    cookies.remove('accessToken', '/', 'localhost');
    isLogin.value = false;
    userInfo.value = {};
    router.replace({ name: 'login' });
};

onMounted(() => {
    getFavoriteList(
        userInfo.value.userId,
        // success
        (success) => {
            for (const favorite of success.dataBody) {
                favoriteList.value.push(favorite)
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
                        await getFavoriteList(
                            userInfo.value.userId,
                            (success) => {
                                // refresh 후 등록 성공
                                for (const favorite of success.dataBody) {
                                    favoriteList.value.push(favorite)
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
                    }
                }
            } else {
                // UNAUTHORIZED 이외의 오류
                alert(fail.dataHeader.resultMessage);
            }
        }
    )
})
</script>

<template>
    <h2 class="text-center mb-5">여행지 즐겨찾기</h2>
    <h5 v-if="!isLogin" class="text-danger text-center mb-5">로그인 후 이용바랍니다.</h5>
    <div v-else>
        <div v-for="favorite in favoriteList" :key=favorite.favoriteId class="col-7 mx-auto mt-4">
            <div class="card mt-5">
                <div class="card-header p-0 mx-3 mt-n4 position-relative z-index-1">
                    <a class="d-block blur-shadow-image">
                        <img v-if="favorite.spotImgUrl != ''" :src="favorite.spotImgUrl" class="img-fluid border-radius-md"
                            alt="Image Placeholder" loading="lazy">
                    </a>
                </div>
                <div class="card-body p-5">
                    <span class="text-gradient text-uppercase text-xs font-weight-bold text-success">Hub</span>
                    <a class="card-title mt-3 h5 d-block text-darker">{{ favorite.spotName }}</a>
                    <p class="card-description mb-4" style="max-width:100%">{{ favorite.spotAddr }}</p>
                    <div class="name"><span>{{ userInfo.userId }}</span></div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
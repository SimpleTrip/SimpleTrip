<script setup>
import { ref, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/user'
import { getFavoriteList } from '@/api/favorite.js'

const userStore = useUserStore()
const { isLogin, userInfo } = storeToRefs(userStore)

const favoriteList = ref([])

onMounted(() => {
    console.log(userInfo.value.userId)
    getFavoriteList(userInfo.value.userId, ({ data }) => {
        console.log('data', data)
        for (const favorite of data) {
            favoriteList.value.push(favorite)
        }
    })
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
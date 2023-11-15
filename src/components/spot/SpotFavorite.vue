<script setup>
import { ref, onMounted } from 'vue'
import { getFavoriteList } from '@/api/favorite.js'

const user = ref({
    userId: 'ssafy'
})
const favoriteList = ref([])

onMounted(() => {
    getFavoriteList(user.value.userId, ({ data }) => {
        for (const favorite of data) {
            console.log(favorite)
            favoriteList.value.push(favorite)
        }
    })
})
</script>

<template>
    <h2 class="text-center mb-5">여행지 즐겨찾기</h2>
    <div class="col-7 mx-auto mt-4">
        <div class="card mt-5">
            <div class="card-header p-0 mx-3 mt-n4 position-relative z-index-1"><a href="javascript:;"
                    class="d-block blur-shadow-image"><img src="https://bit.ly/3EZlfcW" class="img-fluid border-radius-md"
                        alt="Image Placeholder" loading="lazy"></a></div>
            <div class="card-body">
                <span class="text-gradient text-uppercase text-xs font-weight-bold text-success">Hub</span>
                <a href="javascript:;" class="card-title mt-3 h5 d-block text-darker">Shared Coworking</a>
                <p class="card-description mb-4" style="max-width:100%">Use border utilities to quickly style the border and
                    border-radius of an element. Great for images, buttons.</p>
                <div class="name"><span>{{ user.userId }}</span></div>
            </div>
        </div>
    </div>
    <div v-for="favorite in favoriteList" :key=favorite.favoriteId class="col-7 mx-auto mt-4">
        <div class="card mt-5">
            <div class="card-header p-0 mx-3 mt-n4 position-relative z-index-1"><a href="javascript:;"
                    class="d-block blur-shadow-image"><img src="https://bit.ly/3EZlfcW" class="img-fluid border-radius-md"
                        alt="Image Placeholder" loading="lazy"></a></div>
            <div class="card-body">
                <span class="text-gradient text-uppercase text-xs font-weight-bold text-success">Hub</span>
                <a href="javascript:;" class="card-title mt-3 h5 d-block text-darker">Shared Coworking</a>
                <p class="card-description mb-4" style="max-width:100%">{{ favorite. favoriteId }}</p>
                <div class="name"><span>{{ user.userId }}</span></div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
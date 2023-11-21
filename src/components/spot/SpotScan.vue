<script setup>
import { ref, onMounted } from "vue"
import SpotSearch from "./SpotSearch.vue";
import SpotFavorite from "./SpotFavorite.vue";

// nav-pill
import setNavPills from "@/assets/js/nav-pills.js";

const isSearch = ref(true)
const isFavorite = ref(false)

function switchSearch() {
    isSearch.value = true
    isFavorite.value = false
}
function switchFavorite() {
    isSearch.value = false
    isFavorite.value = true
}

onMounted(() => {
    setNavPills();
});
</script>

<template>
    <div>
        <section class="py-5">
            <div class="container">
                <div class="row">
                    <div class="col-lg-4 mx-auto">
                        <div class="nav-wrapper position-relative end-0">
                            <ul class="nav nav-pills nav-fill p-1" role="tablist">
                                <li class="nav-item">
                                    <a @click="switchSearch" class="nav-link mb-0 px-0 py-1 fw-bold"
                                        :class="{ active: isSearch }" data-bs-toggle="tab" href="#profile-tabs-simple"
                                        role="tab" aria-controls="profile" :aria-selected="isSearch">여행지 검색</a>
                                </li>
                                <li class="nav-item">
                                    <a @click="switchFavorite" class="nav-link mb-0 px-0 py-1 fw-bold"
                                        :class="{ active: isFavorite }" data-bs-toggle="tab" href="#dashboard-tabs-simple"
                                        role="tab" aria-controls="dashboard" :aria-selected="isFavorite">
                                        즐겨찾기
                                    </a>
                                </li>
                                
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <div v-if="isSearch">
            <SpotSearch></SpotSearch>
        </div>
        <div v-if="isFavorite">
            <SpotFavorite></SpotFavorite>
        </div>
    </div>
</template>

<style scoped></style>
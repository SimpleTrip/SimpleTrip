<script setup></script>

<template>
  <h2 class="text-center">Write QnA</h2>

  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>Author</h5>
        <div class="input-group input-group-outline"><label class="form-label"></label><input type="text" id="text" class="form-control form-control-md" /></div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>Title</h5>
        <div class="input-group input-group-outline"><label class="form-label"></label><input type="text" id="text" class="form-control form-control-md" /></div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>Content</h5>
        <div class="input-group input-group-outline">
          <label class="form-label"></label>
          <textarea class="text-area" rows="5" cols="37" style="width: 100%"></textarea>
        </div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center gap-2">
    <router-link :to="{ name: 'QnAWrite' }" class="btn btn-success btn-md">Write</router-link>
    <button class="btn btn-outline-success btn-md ms-2 me-2">Clear</button>
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

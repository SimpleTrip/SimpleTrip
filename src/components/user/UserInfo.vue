<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import setMaterialInput from '@/assets/js/material-input';
import { viewUser, withdrawUser, updateUserInfo, changePass } from '@/api/user.js';
import { useCookies } from 'vue3-cookies';
import { refresh } from '@/util/tokenUtil';
import { useUserStore } from '@/stores/user';
import { storeToRefs } from 'pinia';

const router = useRouter();
const userStore = useUserStore();
const { isLogin, userInfo } = storeToRefs(userStore);
const { cookies } = useCookies();
const user = ref({});

const modifyPass = ref({
  userId: userInfo.value.userId,
  beforePass: '',
  afterPass: '',
});

const modifyInput = ref({
  userId: userInfo.value.userId,
  userPw: '',
  userSex: '',
  userAge: '',
});

const isModifyPass = ref(false);
const isModify = ref(false);

const noAuth = () => {
  cookies.remove('refreshToken', '/', 'localhost');
  cookies.remove('accessToken', '/', 'localhost');
  isLogin.value = false;
  userInfo.value = {};
  router.replace({ name: 'login' });
};

onMounted(() => {
  getUser();
  setMaterialInput();
});

const getUser = () => {
  viewUser(
    userInfo.value.userId,
    (success) => {
      user.value = success.dataBody;
    },
    async (fail) => {
      if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
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
            noAuth();
            alert(fail.dataHeader.resultMessage);
          }
        }
      } else {
        alert(fail.dataHeader.resultMessage);
        router.go(-1);
      }
    }
  );
};

const deleteUser = () => {
  withdrawUser(
    userInfo.value.userId,
    (success) => {
      noAuth();
      alert('회원탈퇴가 완료되었습니다.');
    },
    async (fail) => {
      if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
        const refreshData = await refresh();
        if (refreshData != null) {
          if (refreshData.dataHeader.successCode == 0) {
            userInfo.value = refreshData.dataBody;
            await withdrawUser(
              userInfo.value.userId,
              (success) => {
                noAuth();
                alert('회원탈퇴가 완료되었습니다.');
              },
              (fail) => {
                alert(fail.dataHeader.resultMessage);
                router.go(0);
              }
            );
          } else {
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

const updateUserSet = () => {
  if (modifyInput.value.userId == '' || modifyInput.value.userPw == '' || modifyInput.value.userAge == '' || modifyInput.value.userSex == '') {
    alert('모든 정보를 기입 후 수정이 가능합니다.');
  } else {
    updateUserInfo(
      modifyInput.value,
      (success) => {
        alert('회원정보가 수정되었습니다.');
        router.go(0);
      },
      async (fail) => {
        if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
          const refreshData = await refresh();
          if (refreshData != null) {
            if (refreshData.dataHeader.successCode == 0) {
              userInfo.value = refreshData.dataBody;
              await updateUserInfo(
                modifyInput.value,
                (success) => {
                  alert('회원정보가 수정되었습니다.');
                  router.go(0);
                },
                (fail) => {
                  alert(fail.dataHeader.resultMessage);
                  router.go(-1);
                }
              );
            } else {
              noAuth();
              alert(fail.dataHeader.resultMessage);
            }
          }
        } else {
          alert(fail.dataHeader.resultMessage);
          router.go(-1);
        }
      }
    );
  }
};

const clickChangePass = () => {
  if (modifyPass.value.userId == '' || modifyPass.value.beforePass == '' || modifyPass.value.afterPass == '') {
    alert('페스워드를 기입 후 수정이 가능합니다.');
  } else {
    changePass(
      modifyPass.value,
      (success) => {
        alert('비밀번호가 수정되었습니다.');
        router.go(0);
      },
      async (fail) => {
        if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
          const refreshData = await refresh();
          if (refreshData != null) {
            if (refreshData.dataHeader.successCode == 0) {
              userInfo.value = refreshData.dataBody;
              await changePass(
                modifyPass.value,
                (success) => {
                  alert('비밀번호가 수정되었습니다.');
                  router.go(0);
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
  }
};
</script>

<template>
  <div class="my-auto w-100">
    <div class="row my-2">
      <div class="col-lg-4 col-md-8 col-12 mx-auto">
        <div class="card z-index-0 fadeIn3 fadeInBottom">
          <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
            <div class="bg-gradient-success shadow-success border-radius-lg py-3 pe-1">
              <h4 class="text-white font-weight-bolder text-center mt-2 mb-0">회원 정보</h4>
            </div>
          </div>
          <div class="card-body">
            <div class="text-center">
              <div class="input-group input-group-static my-4 is-filled">
                <label class="form-label">아이디</label>
                <span class="form-control" style="text-align: left">{{ user.userId }}</span>
              </div>
              <template v-if="!isModifyPass && !isModify">
                <div class="input-group input-group-static my-4 is-filled">
                  <label class="form-label">성별</label>
                  <span class="form-control" style="text-align: left">{{ user.userSex }}</span>
                </div>
                <div class="input-group input-group-static my-4 is-filled">
                  <label class="form-label">나이</label>
                  <span class="form-control" style="text-align: left">{{ user.userAge }}</span>
                </div>
              </template>

              <template v-if="isModifyPass">
                <div class="input-group input-group-static my-4 is-filled">
                  <label class="form-label">현재 비밀번호</label>
                  <input type="password" class="form-control" v-model="modifyPass.beforePass" />
                </div>
                <div class="input-group input-group-static my-4 is-filled">
                  <label class="form-label">변경 비밀번호</label>
                  <input type="password" class="form-control" v-model="modifyPass.afterPass" />
                </div>
              </template>

              <template v-if="isModify">
                <div class="input-group input-group-static my-4 is-filled">
                  <label class="form-label">성별</label>
                  <select class="select-option form-select text-lightgray select-sex" id="sex" style="border-width: 0px; border-bottom-width: 1px; border-radius: 0px" v-model="modifyInput.userSex">
                    <option value="" selected disabled>성별을 선택해주세요</option>
                    <option value="M">남자</option>
                    <option value="F">여자</option>
                  </select>
                </div>
                <div class="input-group input-group-static my-4 is-filled">
                  <label class="form-label">나이</label>
                  <input type="number" min="10" class="form-control" v-model="modifyInput.userAge" />
                </div>
                <div class="input-group input-group-static my-4 is-filled">
                  <label class="form-label">현재 비밀번호</label>
                  <input type="password" class="form-control" v-model="modifyInput.userPw" />
                </div>
              </template>

              <div class="d-flex justify-content-between" style="padding-top: 20px; padding-bottom: 10px">
                <template v-if="!isModifyPass && !isModify">
                  <a @click="isModifyPass = true" class="btn btn-outline-success btn-md" style="width: 100%; margin: 4px">비밀번호 변경</a>
                  <a @click="isModify = true" class="btn btn-outline-info btn-md" style="width: 100%; margin: 4px">개인정보 수정</a>
                  <a @click="deleteUser" class="btn btn-outline-danger btn-md" style="width: 100%; margin: 4px">회원 탈퇴</a>
                </template>
                <template v-if="isModifyPass">
                  <a
                    @click="
                      isModifyPass = false;
                      modifyPass = {
                        userId: user.userId,
                      };
                    "
                    class="btn btn-outline-secondary btn-md"
                    style="width: 100%; margin: 4px"
                    >취소</a
                  >
                  <a @click="clickChangePass" class="btn btn-outline-success btn-md" style="width: 100%; margin: 4px">수정</a>
                </template>
                <template v-if="isModify">
                  <a
                    @click="
                      isModify = false;
                      modifyInput = {
                        userId: user.userId,
                      };
                    "
                    class="btn btn-outline-secondary btn-md"
                    style="width: 100%; margin: 4px"
                    >취소</a
                  >
                  <a @click="updateUserSet" class="btn btn-outline-success btn-md" style="width: 100%; margin: 4px">수정</a>
                </template>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.select-sex {
  border-width: 0px;
  border-bottom-width: 1px;
  border-radius: 0px;
}

.select-sex:hover {
  border-width: 0px;
  border-bottom-width: 1px;
  border-radius: 0px;
}
</style>

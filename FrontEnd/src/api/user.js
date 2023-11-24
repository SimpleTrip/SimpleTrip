import { useCookies } from 'vue3-cookies';
import { localAxios } from '../util/axiosUtil';

const local = localAxios();
const url = '/users';
const { cookies } = useCookies();

const loginUser = async (params, success, fail) => {
  await local
    .post(`${url}/login`, JSON.stringify(params))
    .then((data) => success(data.data))
    .catch((data) => fail(data.response.data));
};

const joinUser = async (params, success, fail) => {
  await local
    .post(`${url}`, JSON.stringify(params))
    .then((data) => success(data.data))
    .catch((data) => fail(data.response.data));
};

const viewUser = async (userId, success, fail) => {
  const accessToken = cookies.get('accessToken');
  await local
    .get(`${url}/${userId}`, {
      headers: {
        Authorization: accessToken && `Bearer ${accessToken}`,
      },
    })
    .then((data) => success(data.data))
    .catch((data) => fail(data.response.data));
};

const withdrawUser = async (userId, success, fail) => {
  const accessToken = cookies.get('accessToken');
  await local
    .put(`${url}/delete`, JSON.stringify({ userId: userId }), {
      headers: {
        Authorization: accessToken && `Bearer ${accessToken}`,
      },
    })
    .then((data) => success(data.data))
    .catch((data) => fail(data.response.data));
};

const updateUserInfo = async (params, success, fail) => {
  const accessToken = cookies.get('accessToken');
  await local
    .put(`${url}/modifyInfo`, JSON.stringify(params), {
      headers: {
        Authorization: accessToken && `Bearer ${accessToken}`,
      },
    })
    .then((data) => success(data.data))
    .catch((data) => fail(data.response.data));
};

const changePass = async (params, success, fail) => {
  const accessToken = cookies.get('accessToken');
  await local
    .put(`${url}/changePass`, JSON.stringify(params), {
      headers: {
        Authorization: accessToken && `Bearer ${accessToken}`,
      },
    })
    .then((data) => success(data.data))
    .catch((data) => fail(data.response.data));
};

const getMyPass = async (params, success, fail) => {
  await local
    .post(`${url}/findpass`, JSON.stringify(params))
    .then((data) => success(data.data))
    .catch((data) => fail(data.response.data));
};

const sendEmail = async (params, success, fail) => {
  await local
    .get(`${url}/sendEmail`, { params: { email: params } })
    .then((data) => success(data.data))
    .catch(async (data) => await fail(data.response.data));
};

const checkEmail = async (params, success, fail) => {
  await local
    .get(`${url}/checkEmail`, { params: params })
    .then((data) => success(data.data))
    .catch((data) => fail(data.response.data));
};

const logout = async (success, fail) => {
  const accessToken = cookies.get('accessToken');
  await local
    .get(`${url}/logout`, {
      headers: {
        Authorization: accessToken && `Bearer ${accessToken}`,
      },
    })
    .then((data) => success(data.data))
    .catch((data) => fail(data.response.data));
};

export { loginUser, logout, joinUser, viewUser, withdrawUser, updateUserInfo, changePass, getMyPass, sendEmail, checkEmail };

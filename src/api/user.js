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

export { loginUser, logout };

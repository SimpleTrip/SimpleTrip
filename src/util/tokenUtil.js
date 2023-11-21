import { useCookies } from 'vue3-cookies';
import { localAxios } from './axiosUtil';

const local = localAxios();
const url = '/users';
const { cookies } = useCookies();

const refresh = async () => {
  let refreshData = null;
  await local
    .post(`${url}/refresh`, JSON.stringify({ accessToken: cookies.get('accessToken'), refreshToken: cookies.get('refreshToken') }))
    .then((data) => {
      refreshData = data.data;
    })
    .catch((data) => {
      refreshData = data.response.data;
    });
  return refreshData;
};

export { refresh };

import { useCookies } from 'vue3-cookies';
import { localAxios } from './axiosUtil';

const local = localAxios();
const url = '/users';
const { cookies } = useCookies();

const refresh = async (failData) => {
  let refreshData = null;
  if (failData.dataHeader.resultCode == 'UNAUTHORIZED' && failData.dataHeader.successCode == 1) {
    const accessToken = cookies.get('accessToken');
    const refreshToken = cookies.get('refreshToken');
    if (accessToken != null && refreshToken != null) {
      await local
        .post(`${url}/refresh`, JSON.stringify({ accessToken: cookies.get('accessToken'), refreshToken: cookies.get('refreshToken') }))
        .then((data) => {
          refreshData = data.data;
        })
        .catch((data) => {
          console.log(data);
          refreshData = data.response.data;
        });
    }
  }
  return refreshData;
};

export { refresh };

import { localAxios } from '../util/axiosUtil';
import { useCookies } from 'vue3-cookies';

const local = localAxios();
const url = '/plan';
const { cookies } = useCookies();

const getPlanlist = (params, success, fail) => {
  local
    .get(`${url}/planList`, { params: params })
    .then((data) => {
      success(data.data);
    })
    .catch((data) => {
      fail(data.response.data);
    });
};

const setPlan = (params, success, fail) => {
  const accessToken = cookies.get('accessToken');
  local
    .post(`${url}/planRegist`, JSON.stringify(params), {
      headers: {
        Authorization: accessToken && `Bearer ${accessToken}`,
      },
    })
    .then((data) => {
      success(data.data);
    })
    .catch((data) => {
      fail(data.response.data);
    });
};

const getPlan = async (planNo, success, fail) => {
  const accessToken = cookies.get('accessToken');
  await local
    .get(`${url}/${planNo}`, {
      headers: {
        Authorization: accessToken && `Bearer ${accessToken}`,
      },
    })
    .then((data) => {
      success(data.data);
    })
    .catch((data) => {
      fail(data.response.data);
    });
};

const deletePlanAndItem = (planNo, planUserId, success, fail) => {
  const accessToken = cookies.get('accessToken');
  local
    .delete(`${url}/${planNo}`, {
      data: {
        planUserId: planUserId,
      },
      headers: {
        Authorization: accessToken && `Bearer ${accessToken}`,
      },
    })
    .then((data) => {
      success(data.data);
    })
    .catch((data) => {
      fail(data.response.data);
    });
};

export { getPlanlist, setPlan, getPlan, deletePlanAndItem };

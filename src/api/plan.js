import { localAxios } from '../util/axiosUtil';

const local = localAxios();

const url = '/plan';

const getPlanlist = (params, success, fail) => {
  local.get(`${url}/planList`, { params: params }).then(success).catch(fail);
};

const setPlan = (params, success, fail) => {
  local.post(`${url}/planRegist`, JSON.stringify(params)).then(success).catch(fail);
};

const getPlan = async (planNo, success, fail) => {
  await local.get(`${url}/${planNo}`).then(success).catch(fail);
};

const deletePlanAndItem = (planNo, success, fail) => {
  local.delete(`${url}/${planNo}`).then(success).catch(fail);
};

export { getPlanlist, setPlan, getPlan, deletePlanAndItem };

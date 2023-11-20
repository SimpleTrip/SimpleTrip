import { localAxios } from '../util/axiosUtil';

const local = localAxios();
const url = '/users';

const loginUser = async (params, success, fail) => {
  await local.post(`${url}/login`, JSON.stringify(params)).then(success).catch(fail);
};

export { loginUser };

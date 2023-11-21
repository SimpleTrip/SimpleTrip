import { localAxios } from '../util/axiosUtil';

const local = localAxios();
const url = '/users';

const loginUser = async (params, success, fail) => {
  await local
    .post(`${url}/login`, JSON.stringify(params))
    .then((data) => success(data.data))
    .catch((data) => fail(data.response.data));
};

const logout = async (success, fail) => {
  await local
    .get(`${url}/logout`)
    .then((data) => success(data.data))
    .catch((data) => fail(data.response.data));
};

export { loginUser, logout };

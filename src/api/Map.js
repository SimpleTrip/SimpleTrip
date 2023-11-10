import { publicDataAxios } from '../util/axiosUtil';
const { VITE_SECRET_KEY } = import.meta.env;

const publicAxios = publicDataAxios(); // axios instance
const url = '/areaCode1';
const query = {
  MobileOS: 'ETC',
  MobileApp: 'SimpleTrip',
  _type: 'json',
  serviceKey: VITE_SECRET_KEY,
};

function getSido(success, fail) {
  publicAxios.get(`${url}`, { params: query }).then(success).catch(fail);
}
export { getSido };

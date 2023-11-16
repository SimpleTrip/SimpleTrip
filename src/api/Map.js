import { publicDataAxios } from '../util/axiosUtil';
const { VITE_SECRET_KEY } = import.meta.env;

const publicAxios = publicDataAxios(); // axios instance

const areaUrl = '/areaCode1';
const mapUrl = '/searchKeyword1';

const baseQuery = {
  MobileOS: 'ETC',
  MobileApp: 'SimpleTrip',
  _type: 'json',
  serviceKey: VITE_SECRET_KEY,
};

async function getSido(success, fail) {
  const sidoQuery = Object.assign({}, baseQuery);
  await publicAxios.get(`${areaUrl}`, { params: sidoQuery }).then(success).catch(fail);
}

async function getGugun(areaCode, success, fail) {
  const gugunQuery = Object.assign({}, baseQuery);
  gugunQuery.areaCode = areaCode;
  await publicAxios.get(`${areaUrl}`, { params: gugunQuery }).then(success).catch(fail);
}

async function getMap(areaName, areaCode, sigunguCode, contentTypeId, success, fail) {
  const mapQuery = Object.assign({}, baseQuery);
  mapQuery.keyword = areaName;
  mapQuery.areaCode = areaCode;
  mapQuery.sigunguCode = sigunguCode;
  mapQuery.contentTypeId = contentTypeId;
  await publicAxios.get(`${mapUrl}`, { params: mapQuery }).then(success).catch(fail);
}

export { getSido, getGugun, getMap };

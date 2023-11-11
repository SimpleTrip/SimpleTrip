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

function getSido(success, fail) {
  const sidoQuery = baseQuery;
  publicAxios.get(`${areaUrl}`, { params: sidoQuery }).then(success).catch(fail);
}

function getGugun(areaCode, success, fail) {
  const gugunQuery = baseQuery;
  gugunQuery.areaCode = areaCode;
  publicAxios.get(`${areaUrl}`, { params: gugunQuery }).then(success).catch(fail);
}

function getMap(areaName, areaCode, sigunguCode, contentTypeId, success, fail) {
  const mapQuery = baseQuery;
  mapQuery.keyword = areaName;
  mapQuery.areaCode = areaCode;
  mapQuery.sigunguCode = sigunguCode;
  mapQuery.contentTypeId = contentTypeId;
  publicAxios.get(`${mapUrl}`, { params: mapQuery }).then(success).catch(fail);
}

export { getSido, getGugun, getMap };

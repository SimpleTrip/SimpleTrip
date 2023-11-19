import { localAxios } from '../util/axiosUtil';

const placeAxios = localAxios();

const domain = '/places';

function registPlace(place, success, fail) {
    placeAxios.post(`${domain}`, place).then(success).catch(fail);
}

function getPlace(placeId, success, fail) {
    placeAxios.get(`${domain}/${placeId}`).then(success).catch(fail);
}

function getPlaceList(listInfo, success, fail) {
    placeAxios.get(`${domain}`, {params: listInfo}).then(success).catch(fail);
}

export { registPlace, getPlace, getPlaceList }

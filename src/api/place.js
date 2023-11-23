import { localAxios } from "../util/axiosUtil";
import { useCookies } from "vue3-cookies";

const placeAxios = localAxios();

const domain = "/places";

const { cookies } = useCookies();

function registPlace(place, success, fail) {
  placeAxios.post(`${domain}`, place).then(success).catch(fail);
}

function getPlace(placeId, success, fail) {
  const accessToken = cookies.get("accessToken");
  placeAxios
    .get(`${domain}/${placeId}`, {
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
}

function getPlaceList(listInfo, success, fail) {
  placeAxios.get(`${domain}`, { params: listInfo }).then(success).catch(fail);
}

export { registPlace, getPlace, getPlaceList };

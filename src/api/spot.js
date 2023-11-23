import { localAxios } from '../util/axiosUtil';
import { useCookies } from "vue3-cookies";

const spotAxios = localAxios();

const domain = '/spots';

const { cookies } = useCookies();

function addSpot(userId, spot, success, fail) {
    const accessToken = cookies.get("accessToken");
    spotAxios
    .post(`${domain}/${userId}`, spot, {
        headers: {
            Authorization: accessToken && `Bearer ${accessToken}`,
        }
    })
    .then((data) => {
        success(data.data);
      })
      .catch((data) => {
        fail(data.response.data);
    });
}

function getPopularSpotList(user, success, fail) {
    const accessToken = cookies.get("accessToken");
    spotAxios
    .get(`${domain}`, {
        headers: {
            Authorization: accessToken && `Bearer ${accessToken}`,
        },
        params: {
            userSex: user.userSex,
            userAge: user.userAge
        }
    })
    .then((data) => {
        success(data.data);
    })
      .catch((data) => {
        fail(data.response.data);
    });
}

export { addSpot, getPopularSpotList }
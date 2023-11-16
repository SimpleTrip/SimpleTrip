import { localAxios } from '../util/axiosUtil';

const spotAxios = localAxios();

const domain = '/spots';

function addSpot(spot, sucess, fail) {
    spotAxios.post(`${domain}`, spot).then(sucess).catch(fail);
}

function getPopularSpotList(user, sucess, fail) {
    spotAxios.get(`${domain}`, {
        params: {
            userSex: user.userSex,
            userAge: user.userAge
        }
    }).then(sucess).catch(fail);
}

export { addSpot, getPopularSpotList }
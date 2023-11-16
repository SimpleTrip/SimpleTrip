import { localAxios } from '../util/axiosUtil';

const favoriteAxios = localAxios();

const domain = '/favorites';

function addFavorite(favorite, sucess, fail) {
    favoriteAxios.post(`${domain}`, favorite).then(sucess).catch(fail);
}

function getFavoriteList(userId, sucess, fail) {
    favoriteAxios.get(`${domain}/${userId}`).then(sucess).catch(fail);
}

function deleteFavorite(favorite, sucess, fail) {
    favoriteAxios.delete(`${domain}`, { 
        data: {
            userId: favorite.userId,
            spotId: favorite.spotId
        }
    })
    .then(sucess)
    .catch(fail);
}

export { addFavorite, getFavoriteList, deleteFavorite }

import { localAxios } from "../util/axiosUtil";
import { useCookies } from "vue3-cookies";

const favoriteAxios = localAxios();

const domain = "/favorites";

const { cookies } = useCookies();

function addFavorite(favorite, success, fail) {
  const accessToken = cookies.get("accessToken");
  favoriteAxios
    .post(`${domain}`, favorite, {
      headers: {
        Authorization: accessToken && `Bearer ${accessToken}`,
      },
    })
    .then((data) => {
      success(data.data);
    })
    .catch((error) => {
      const responseData = error.response ? error.response.data : undefined;
      fail(responseData);
    });
}

function getFavoriteList(userId, success, fail) {
  const accessToken = cookies.get("accessToken");
  favoriteAxios
  .get(`${domain}/${userId}`, {
    headers: {
      Authorization: accessToken && `Bearer ${accessToken}`,
    },
  })
  .then((data) => {
    success(data.data);
  })
  .catch((data) => {
    console.log(data)
    fail(data.response.data);
  });
}

function deleteFavorite(favorite, success, fail) {
  const accessToken = cookies.get("accessToken");
  favoriteAxios
    .delete(`${domain}`, {
      headers: {
        Authorization: accessToken && `Bearer ${accessToken}`,
      },
      data: {
        userId: favorite.userId,
        spotId: favorite.spotId,
      },
    })
    .then((data) => {
      success(data.data);
    })
    .catch((data) => {
      fail(data.response.data);
    });
}

export { addFavorite, getFavoriteList, deleteFavorite };

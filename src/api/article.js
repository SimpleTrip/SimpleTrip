import { localAxios } from "../util/axiosUtil";
import { useCookies } from "vue3-cookies";

const articleAxios = localAxios();
const domain = "/articles";
const { cookies } = useCookies();

function writeArticle(article, success, fail) {
  const accessToken = cookies.get("accessToken");
  articleAxios
    .post(`${domain}`, article, {
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

function getArticle(articleId, success, fail) {
  articleAxios
    .get(`${domain}/${articleId}`)
    .then((data) => {
      success(data.data);
    })
    .catch((data) => {
      fail(data.response.data);
    });
}

function getArticleList(params, success, fail) {
  articleAxios.get(`${domain}`, { params: params }).then(success).catch(fail);
}

function modifyArticle(articleId, article, success, fail) {
  const accessToken = cookies.get("accessToken");
  articleAxios
    .put(`${domain}/${articleId}`, article, {
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

function deleteArticle(articleId, articleUserId, success, fail) {
  const accessToken = cookies.get("accessToken");
  articleAxios
  .delete(`${domain}/${articleId}`, {
    data: {
      articleUserId: articleUserId
    },
    headers: {
      Authorization: accessToken && `Bearer ${accessToken}`,
    }
  })
  .then((data) => {
    console.log("success", data);
    success(data.data);
  })
  .catch((data) => {
    console.log(articleId, articleUserId)
    console.log("fail", data);
    fail(data.response.data);
  });
}

export {
  writeArticle,
  getArticle,
  getArticleList,
  modifyArticle,
  deleteArticle,
};

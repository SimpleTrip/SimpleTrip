import { localAxios } from '../util/axiosUtil';

const articleAxios = localAxios();

const domain = '/articles';

function writeArticle(article, success, fail) {
  articleAxios.post(`${domain}`, article).then(success).catch(fail);
}

function getArticle(articleId, success, fail) {
  articleAxios.get(`${domain}/${articleId}`).then(success).catch(fail);
}

function getArticleList(params, success, fail) {
  articleAxios.get(`${domain}`, { params: params }).then(success).catch(fail);
}

function modifyArticle(articleId, article, success, fail) {
  articleAxios.put(`${domain}/${articleId}`, article).then(success).catch(fail);
}

function deleteArticle(articleId, success, fail) {
  articleAxios.delete(`${domain}/${articleId}`).then(success).catch(fail);
}

export { writeArticle, getArticle, getArticleList, modifyArticle, deleteArticle };

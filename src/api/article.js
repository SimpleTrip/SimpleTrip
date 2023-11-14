import { localAxios } from '../util/axiosUtil';

const articleAxios = localAxios();

const domain = '/articles';

function writeArticle(article, sucess, fail) {
    articleAxios.post(`${domain}`, article).then(sucess).catch(fail);
}

function getArticle(articleId, sucess, fail) {
    articleAxios.get(`${domain}/${articleId}`).then(sucess).catch(fail);
}

function getArticleList (sucess, fail) {
    articleAxios.get(`${domain}`).then(sucess).catch(fail);
}

function modifyArticle (articleId, article, sucess, fail) {
    articleAxios.put(`${domain}/${articleId}`, article).then(sucess).catch(fail);
}

function deleteArticle (articleId, sucess, fail) {
    articleAxios.delete(`${domain}/${articleId}`).then(sucess).catch(fail);
}

export { writeArticle, getArticle, getArticleList, modifyArticle, deleteArticle }
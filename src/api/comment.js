import { localAxios } from '../util/axiosUtil';

const articleAxios = localAxios();

const domain = '/comment';

const getCommentList = async (articleId, sucess, fail) => {
  await articleAxios.get(`${domain}/list/${articleId}`).then(sucess).catch(fail);
};

const writeComment = async (comment, sucess, fail) => {
  await articleAxios.post(`${domain}/write`, comment).then(sucess).catch(fail);
};

const rewriteComment = async (comment, sucess, fail) => {
  await articleAxios.put(`${domain}/rewrite`, comment).then(sucess).catch(fail);
};

const deleteComment = async (commentNo, sucess, fail) => {
  await articleAxios.delete(`${domain}/${commentNo}`).then(sucess).catch(fail);
};

export { writeComment, getCommentList, rewriteComment, deleteComment };

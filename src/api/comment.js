import { localAxios } from '../util/axiosUtil';
import { useCookies } from "vue3-cookies";

const articleAxios = localAxios();

const domain = '/comment';

const { cookies } = useCookies();

const getCommentList = async (articleId, success, fail) => {
  await articleAxios.get(`${domain}/list/${articleId}`).then(success).catch(fail);
};

const writeComment = async (comment, success, fail) => {
  const accessToken = cookies.get("accessToken");
  await articleAxios
  .post(`${domain}/write`, comment, {
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
};

const rewriteComment = async (comment, success, fail) => {
  await articleAxios
  .put(`${domain}/rewrite`, comment)
  .then((data) => {
    success(data.data)
  })
  .catch((data) => {
    fail(data.response.data)
  });
};

const deleteComment = async (commentNo, commentUserId, success, fail) => {
  const accessToken = cookies.get("accessToken");
  await articleAxios
  .delete(`${domain}/${commentNo}`, {
    data: {
      commentUserId: commentUserId
    },
    headers: {
      Authorization: accessToken && `Bearer ${accessToken}`,
    }
  })
  .then((data) => {
    success(data.data)
  })
  .catch((data) => {
    fail(data.response.data)
  });
};

export { writeComment, getCommentList, rewriteComment, deleteComment };

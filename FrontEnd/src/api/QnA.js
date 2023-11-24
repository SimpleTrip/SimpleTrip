import { localAxios } from '../util/axiosUtil';
import { useCookies } from 'vue3-cookies';

const local = localAxios();
const url = '/qna';
const { cookies } = useCookies();

const list = async (params, success, fail) => {
  await local
    .get(`${url}/qnaList`, { params: params })
    .then((data) => {
      success(data.data);
    })
    .catch((data) => {
      fail(data.response.data);
    });
};

const getQnA = (qnaNo, success, fail) => {
  const accessToken = cookies.get('accessToken');
  local
    .get(`${url}/question/${qnaNo}`, {
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

const writeQnA = (params, success, fail) => {
  const accessToken = cookies.get('accessToken');
  local
    .post(`${url}/question`, JSON.stringify(params), {
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

const answerQnA = (params, success, fail) => {
  const accessToken = cookies.get('accessToken');
  local
    .put(`${url}/answer`, JSON.stringify(params), {
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

const deleteQnA = (qnaNo, questionUserId, success, fail) => {
  const accessToken = cookies.get('accessToken');
  local
    .delete(`${url}/question/${qnaNo}`, {
      data: {
        questionUserId: questionUserId,
      },
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

export { list, getQnA, writeQnA, answerQnA, deleteQnA };

import { localAxios } from '../util/axiosUtil';

const local = localAxios();

const url = '/qna';

const list = (params, success, fail) => {
  local.get(`${url}/qnaList`, { params: params }).then(success).catch(fail);
};

const getQnA = (qnaNo, success, fail) => {
  local.get(`${url}/question/${qnaNo}`).then(success).catch(fail);
};

const writeQnA = (params, success, fail) => {
  local.post(`${url}/question`, JSON.stringify(params)).then(success).catch(fail);
};

const answerQnA = (params, success, fail) => {
  local.put(`${url}/answer`, JSON.stringify(params)).then(success).catch(fail);
};

const deleteQnA = (qnaNo, success, fail) => {
  local.delete(`${url}/question/${qnaNo}`).then(success).catch(fail);
};

export { list, getQnA, writeQnA, answerQnA, deleteQnA };

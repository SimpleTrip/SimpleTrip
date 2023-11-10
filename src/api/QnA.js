import { localAxios } from '../util/axiosUtil';

const local = localAxios();

const url = '/qna';

function list(success, fail) {
  local.get(`${url}/qnaList`).then(success).catch(fail);
}

function getQnA(qnaNo, success, fail) {
  local.get(`${url}/question/${qnaNo}`).then(success).catch(fail);
}

function writeQnA(params, success, fail) {
  local.post(`${url}/question`, JSON.stringify(params)).then(success).catch(fail);
}

function answerQnA(params, success, fail) {
  local.put(`${url}/answer`, JSON.stringify(params)).then(success).catch(fail);
}

function deleteQnA(qnaNo, success, fail) {
  local.delete(`${url}/question/${qnaNo}`).then(success).catch(fail);
}

export { list, getQnA, writeQnA, answerQnA, deleteQnA };

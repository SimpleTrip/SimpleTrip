import { localAxios } from '../util/axiosUtil';

const local = localAxios(); // axios instance

const url = '/qna';

function list(success, fail) {
  local.get(`${url}/qnaList`).then(success).catch(fail);
}

export { list };

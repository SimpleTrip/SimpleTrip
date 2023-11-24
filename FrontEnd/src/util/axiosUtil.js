const { VITE_LOCAL_URL, VITE_MAP_URL } = import.meta.env;
import axios from 'axios';

axios.defaults.withCredentials = true;

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    withCredentials: true,
    baseURL: '/local',
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  return instance;
}

// public data vue api axios instance
function publicDataAxios() {
  const instance = axios.create({
    withCredentials: true,
    baseURL: '/openApiMap',
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  return instance;
}

export { localAxios, publicDataAxios };

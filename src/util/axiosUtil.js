const { VITE_LOCAL_URL, VITE_MAP_URL } = import.meta.env;
import axios from 'axios';

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
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
    baseURL: VITE_MAP_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  return instance;
}

export { localAxios, publicDataAxios };

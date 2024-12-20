import axios from "axios";

const { VITE_VUE_API_URL, VITE_ELECTRIC_CHARGING_STATION_URL } = import.meta.env;

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });

  instance.interceptors.request.use(
    function (config) {
      const token = localStorage.getItem("token");
      if (config.onStart) config.onStart(); // 콜백 함수로 로딩 시작
      if (token) {
        config.headers.Authorization = `${token}`;
      }
      return config;
    },
    function (error) {
      if (error.config?.onFinish) error.config.onFinish(); // 콜백 함수로 로딩 종료
      return Promise.reject(error);
    },
  );

  instance.interceptors.response.use(
    function (response) {
      if (response.config.onFinish) response.config.onFinish(); // 콜백 함수로 로딩 종료
      return response;
    },
    function (error) {
      if (error.config?.onFinish) error.config.onFinish(); // 콜백 함수로 로딩 종료
      return Promise.reject(error);
    }
  );

  return instance;
}

export { localAxios };

import axios from "axios";

const token = localStorage.getItem("idToken");

const instance = axios.create({
  baseURL: "https://k6a306.p.ssafy.io/api/",
  headers: {
    "Content-Type": "multipart/form-data",
    "Access-Control-Allow-Credentials": true,
  },
});

instance.interceptors.request.use(
  function (config) {
    config.headers.Authorization = token ? "Bearer" + token.replace(`"`, " ").replace(`"`, " ") : null;

    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);

export default instance;

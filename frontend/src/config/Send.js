import axios from "axios";

const token = localStorage.getItem("idToken");

const instance = axios.create({
  baseURL: "https://k6a306.p.ssafy.io/api/",
  headers: {
    "Content-Type": "application/json",
    Authorization: token ? "Bearer" + token.replace(`"`, " ").replace(`"`, " ") : null,
  },
});

instance.interceptors.request.use(
  function (config) {
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);

export default instance;

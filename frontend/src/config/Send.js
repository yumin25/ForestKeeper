import axios from "axios";

const instance = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
    Authorization: localStorage.getItem("idToken") ? "Bearer" + localStorage.getItem("idToken").replace(`"`, " ").replace(`"`, " ") : null,
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

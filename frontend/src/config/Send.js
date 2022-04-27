import axios from "axios";

let Token = localStorage.getItem("idToken").replace(`"`, " ").replace(`"`, " ");

const instance = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
    Authorization: "Bearer" + Token,
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
